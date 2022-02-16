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
import com.pseandroid2.dailydata.model.project.ProjectSkeleton
import com.pseandroid2.dailydata.model.project.SimpleSkeleton
import com.pseandroid2.dailydata.model.table.ArrayListTable
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
import com.pseandroid2.dailydata.repository.commandCenter.commands.PublishProject
import com.pseandroid2.dailydata.repository.commandCenter.commands.SetDescription
import com.pseandroid2.dailydata.repository.commandCenter.commands.SetTitle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class ViewModelProject(
    @Deprecated("Access via skeleton is deprecated")
    @Suppress("Deprecation")
    override val skeleton: ProjectSkeleton = SimpleSkeleton(),
    override var isOnline: Boolean = false,
    override var table: Table = ArrayListTable(),
    private var mutableGraphs: MutableList<Graph<*, *>> = mutableListOf(),
    private var mutableUsers: MutableList<User> = mutableListOf(),
    override var admin: User,
    val repo: RepositoryViewModelAPI
) : Project {

    private val mutableIllegalOperation: Map<Operation, MutableSharedFlow<Boolean>>
    override val isIllegalOperation: Map<Operation, Flow<Boolean>>

    private val executeQueue = repo.projectHandler.executeQueue

    override val graphs: List<Graph<*, *>>
        get() = mutableGraphs

    override val users: List<User>
        get() = mutableUsers

    init {
        val operations = mutableMapOf<Operation, MutableSharedFlow<Boolean>>()
        for (operation in Operation.values()) {
            if (operation.type == Operation.OperationType.PROJECT) {
                operations[operation] = MutableSharedFlow(1)
            }
        }
        mutableIllegalOperation = operations.toMap()
        val immutableOperations = mutableMapOf<Operation, Flow<Boolean>>()
        for (entry in mutableIllegalOperation) {
            immutableOperations[entry.key] = entry.value.asSharedFlow()
        }
        for (operation in Operation.values()) {
            if (operation.type == Operation.OperationType.TABLE) {
                immutableOperations[operation] = table.isIllegalOperation[operation]!!
            }
        }
        isIllegalOperation = immutableOperations.toMap()
    }

    override suspend fun setName(name: String) {
        mutableIllegalOperation[Operation.SET_PROJECT_NAME]!!.emit(false)
        executeQueue.add(SetTitle(this, name, repo))
    }

    override suspend fun setDesc(desc: String) {
        mutableIllegalOperation[Operation.SET_PROJECT_DESC]!!.emit(false)
        executeQueue.add(SetDescription(this, desc, repo))
    }

    override suspend fun setPath(path: String) {
        TODO("Not yet implemented")
    }

    override suspend fun setColor(color: Int) {
        TODO("changeWallpaper")
    }

    override suspend fun addGraph(graph: Graph<*, *>) {
        mutableIllegalOperation[Operation.ADD_GRAPH]!!.emit(false)
        executeQueue.add(AddGraph(id, graph, repo))
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

    suspend fun deleteRow(row: Int) {
        table.deleteRow(row)
    }

    override suspend fun addColumn(specs: ColumnData, default: Any) {
        table.addColumn(specs, default)
    }

    override suspend fun deleteColumn(column: Int) {
        table.deleteColumn(column)
    }

    override suspend fun addUIElements(col: Int, uiElement: UIElement) {
        table.addUIElement(col, uiElement)
    }

    override suspend fun deleteUIElement(col: Int, id: Int) {
        table.removeUIElement(col, id)
    }

    override suspend fun addNotification(notification: Notification) {
        mutableIllegalOperation[Operation.ADD_NOTIFICATION]!!.emit(false)
        executeQueue.add(AddNotification(id, notification, repo))
    }

    override suspend fun changeNotification(id: Int, notification: Notification) {
        TODO("setNotification")
    }

    override suspend fun removeNotification(id: Int) {
        mutableIllegalOperation[Operation.DELETE_NOTIFICATION]!!.emit(false)
        TODO("Not yet implemented after refactoring")
    }

    override suspend fun addUser(user: User) {
        if (user !in users && users.size < Project.MAXIMUM_PROJECT_USERS) {
            mutableIllegalOperation[Operation.ADD_USER]!!.emit(false)
            executeQueue.add(AddUser(id, user, repo))
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

    override suspend fun publish() {
        mutableIllegalOperation[Operation.PUBLISH_PROJECT]!!.emit(false)
        executeQueue.add(PublishProject(this, repo))
    }

    override suspend fun setAdmin(admin: User) {
        mutableIllegalOperation[Operation.SET_ADMIN]!!.emit(false)
        executeQueue.add(TODO("Set Admin Command"))
    }

    override suspend fun unlink() {
        TODO("Not yet implemented")
    }

    override suspend fun delete() {
        TODO("deleteProj")
    }
}