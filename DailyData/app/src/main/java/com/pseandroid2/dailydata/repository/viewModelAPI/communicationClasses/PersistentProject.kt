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


    private val executeQueue = r.projectHandler.executeQueue

    override val graphs: List<Graph<*, *>>
        get() = project.graphs

    override val users: List<User>
        get() = project.users

    override val table: Table = PersistentTable(project.table, r, scope, id)

    override val notifications: List<Notification>
        get() = project.notifications


    @Deprecated("Internal function, should not be used outside the RepositoryViewModelAPI")
    @Suppress("DEPRECATION")
    override val mutableIllegalOperation: MutableMap<String, MutableSharedFlow<Boolean>> =
        mutableMapOf()

    init {
        for (operation in ProjectOperation.values()) {
            @Suppress("DEPRECATION")
            mutableIllegalOperation[operation.id] = MutableSharedFlow(1)
        }
        addFlows(table)
    }

    override var id: Int
        get() = project.id
        set(value) {
            throw IllegalOperationException("Re-Setting the id of a project is not permitted")
        }

    override val name: String
        get() = project.name

    override suspend fun setName(name: String) {
        s(SetTitle(id, name, r), ProjectOperation.SET_PROJECT_NAME.id)
    }

    override val desc: String
        get() = project.desc

    override suspend fun setDesc(desc: String) {
        s(SetDescription(id, desc, r), ProjectOperation.SET_PROJECT_DESC.id)
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
        s(AddGraph(id, graph, r), ProjectOperation.ADD_GRAPH.id)
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
        s(AddNotification(id, notification, r), ProjectOperation.ADD_NOTIFICATION.id)
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
        s(TODO("Not yet implemented after refactoring"), ProjectOperation.DELETE_NOTIFICATION.id)
    }

    override suspend fun addUser(user: User) {
        if (user !in users && users.size < Project.MAXIMUM_PROJECT_USERS) {
            s(AddUser(id, user, r), ProjectOperation.ADD_USER.id)
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
        s(PublishProject(id, r), ProjectOperation.PUBLISH_PROJECT.id)
    }

    override suspend fun setAdmin(admin: User) {
        s(TODO("Set Admin Command"), ProjectOperation.SET_ADMIN.id)
    }

    override val isOnline: Boolean
        get() = TODO("Not yet implemented")

    override suspend fun unsubscribe() {
        TODO("Not yet implemented")
    }

    override suspend fun delete() {
        TODO("deleteProj")
    }

    private suspend fun s(projectCommand: ProjectCommand, vararg operationIDs: String) {
        for (id in operationIDs) {
            @Suppress("DEPRECATION")
            mutableIllegalOperation[id]!!.emit(false)
        }
        executeQueue.add(projectCommand)
    }
}