/*

    DailyData is an android app to easily create diagrams from data one has collected
    Copyright (C) 2022  Antonia Heiming, Anton Kadelbach, Arne Kuchenbecker, Merlin Opp, Robin Amman

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.

*/

package com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses

import com.google.gson.Gson
import com.pseandroid2.dailydata.model.graph.Graph
import com.pseandroid2.dailydata.model.notifications.Notification
import com.pseandroid2.dailydata.model.project.Project
import com.pseandroid2.dailydata.model.table.ColumnData
import com.pseandroid2.dailydata.model.table.Row
import com.pseandroid2.dailydata.model.table.Table
import com.pseandroid2.dailydata.model.uielements.UIElement
import com.pseandroid2.dailydata.model.users.User
import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.repository.commandCenter.commands.AddGraph
import com.pseandroid2.dailydata.repository.commandCenter.commands.AddNotification
import com.pseandroid2.dailydata.repository.commandCenter.commands.AddUser
import com.pseandroid2.dailydata.repository.commandCenter.commands.IllegalOperationException
import com.pseandroid2.dailydata.repository.commandCenter.commands.ProjectCommand
import com.pseandroid2.dailydata.repository.commandCenter.commands.PublishProject
import com.pseandroid2.dailydata.repository.commandCenter.commands.SetDescription
import com.pseandroid2.dailydata.repository.commandCenter.commands.SetTitle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class PersistentProject(
    private val project: Project,
    val r: RepositoryViewModelAPI,
    scope: CoroutineScope
) : Project {

    private val mutableIllegalOperation: Map<ProjectOperation, MutableSharedFlow<Boolean>>
    override val isIllegalOperation: Map<ProjectOperation, Flow<Boolean>>

    private val executeQueue = r.projectHandler.executeQueue

    override val graphs: List<Graph<*, *>>
        get() = project.graphs

    override val users: List<User>
        get() = project.users

    override val table: Table = PersistentTable(project.table, r, scope, id)

    override val notifications: List<Notification>
        get() = project.notifications

    init {
        val operations = mutableMapOf<ProjectOperation, MutableSharedFlow<Boolean>>()
        for (operation in ProjectOperation.values()) {
            if (operation.type == ProjectOperation.OperationType.PROJECT) {
                operations[operation] = MutableSharedFlow(1)
                scope.launch(Dispatchers.IO) {
                    operations[operation]!!.emit(operation.isIllegalByData(this@PersistentProject))
                }
            }
        }
        mutableIllegalOperation = operations.toMap()
        val immutableOperations = mutableMapOf<ProjectOperation, Flow<Boolean>>()
        for (entry in mutableIllegalOperation) {
            immutableOperations[entry.key] = entry.value.asSharedFlow()
        }
        for (operation in ProjectOperation.values()) {
            if (operation.type == ProjectOperation.OperationType.TABLE) {
                immutableOperations[operation] = table.isIllegalOperation[operation]!!
            }
        }
        isIllegalOperation = immutableOperations.toMap()
    }

    override var id: Int
        get() = project.id
        set(value) {
            throw IllegalOperationException("Re-Setting the id of a project is not permitted")
        }

    override val name: String
        get() = project.name

    override suspend fun setName(name: String) {
        mutableIllegalOperation[ProjectOperation.SET_PROJECT_NAME]!!.emit(false)
        executeQueue.add(SetTitle(id, name, r))
    }

    override val desc: String
        get() = project.desc

    override suspend fun setDesc(desc: String) {
        mutableIllegalOperation[ProjectOperation.SET_PROJECT_DESC]!!.emit(false)
        executeQueue.add(SetDescription(id, desc, r))
    }

    override val path: String
        get() = project.path

    override suspend fun setPath(path: String) {
        TODO("Not yet implemented")
    }

    override val color: Int
        get() = project.color

    override suspend fun setColor(color: Int) {
        TODO("changeWallpaper")
    }

    override suspend fun addGraph(graph: Graph<*, *>) {
        mutableIllegalOperation[ProjectOperation.ADD_GRAPH]!!.emit(false)
        executeQueue.add(AddGraph(id, graph, r))
    }

    override suspend fun addGraphs(graphsToAdd: Collection<Graph<*, *>>) {
        for (graph in graphsToAdd) {
            addGraph(graph)
        }
    }

    override suspend fun removeGraph(id: Int) {
        TODO("Not yet implemented")
    }

    override fun createTransformationFromString(transformationString: String): Project.DataTransformation<out Any> {
        TODO("Not yet implemented")
    }

    suspend fun addRow(row: Row) {
        table.addRow(row)
    }

    suspend fun deleteRow(row: Row) {
        table.deleteRow(row)
    }

    override suspend fun addColumn(specs: ColumnData, default: Any) {
        table.addColumn(specs, default)
    }

    override suspend fun deleteColumn(column: Int) {
        table.deleteColumn(column)
    }

    override suspend fun addUIElement(col: Int, uiElement: UIElement) {
        table.addUIElement(col, uiElement)
    }

    override suspend fun deleteUIElement(col: Int, id: Int) {
        table.removeUIElement(col, id)
    }

    override val admin: User
        get() = TODO("Not yet implemented")

    override suspend fun addNotification(notification: Notification) {
        mutableIllegalOperation[ProjectOperation.ADD_NOTIFICATION]!!.emit(false)
        executeQueue.add(AddNotification(id, notification, r))
    }

    override suspend fun addNotifications(notifications: Collection<Notification>) {
        for (notification in notifications) {
            addNotification(notification)
        }
    }

    override suspend fun changeNotification(id: Int, notification: Notification) {
        TODO("setNotification")
    }

    override suspend fun removeNotification(id: Int) {
        mutableIllegalOperation[ProjectOperation.DELETE_NOTIFICATION]!!.emit(false)
        TODO("Not yet implemented after refactoring")
    }

    override suspend fun addUser(user: User) {
        if (user !in users && users.size < Project.MAXIMUM_PROJECT_USERS) {
            mutableIllegalOperation[ProjectOperation.ADD_USER]!!.emit(false)
            executeQueue.add(AddUser(id, user, r))
        } else {
            throw IllegalOperationException(
                "Could not add the User ${Gson().toJson(user)} to " +
                        "project with id $id. Perhaps they are already a member of that project " +
                        "or the maximum number of members has been reached for that project"
            )
        }
    }

    override suspend fun addUsers(usersToAdd: Collection<User>) {
        TODO("Not yet implemented")
    }

    override suspend fun removeUser(user: User) {
        if (user in users && users.size > 1) {
            TODO("deleteMember")
        } else {
            //TODO this error Message doesn't make any sense whatsoever considering what the if statement checks against
            throw IllegalOperationException("This command is only usable by project admins and you are no project admin.")
        }
    }

    override val onlineId: Long
        get() = project.onlineId

    override suspend fun publish() {
        mutableIllegalOperation[ProjectOperation.PUBLISH_PROJECT]!!.emit(false)
        executeQueue.add(PublishProject(id, r))
    }

    override suspend fun setAdmin(admin: User) {
        mutableIllegalOperation[ProjectOperation.SET_ADMIN]!!.emit(false)
        executeQueue.add(TODO("Set Admin Command"))
    }

    override val isOnline: Boolean
        get() = TODO("Not yet implemented")

    override suspend fun unsubscribe() {
        TODO("Not yet implemented")
    }

    override suspend fun delete() {
        TODO("deleteProj")
    }

    private suspend fun s(projectCommand: ProjectCommand) {
        r.projectHandler.executeQueue.add(projectCommand)
    }
}