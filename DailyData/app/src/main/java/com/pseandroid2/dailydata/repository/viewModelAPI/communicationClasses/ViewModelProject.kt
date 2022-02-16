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
    override var graphs: MutableList<Graph<*, *>> = ArrayList(),
    override var users: MutableList<User> = mutableListOf(),
    override var admin: User,
    val repositoryViewModelAPI: RepositoryViewModelAPI
) : Project {

    constructor(
        id: Int = -1,
        onlineId: Long = -1,
        name: String = "",
        desc: String = "",
        path: String = "",
        color: Int = -1,
        notifications: MutableList<Notification> = mutableListOf(),
        isOnline: Boolean = false,
        table: Table = ArrayListTable(),
        graphs: MutableList<Graph<*, *>> = ArrayList(),
        users: MutableList<User> = mutableListOf(),
        admin: User,
        repositoryViewModelAPI: RepositoryViewModelAPI
    ) : this(
        SimpleSkeleton(id, onlineId, name, desc, path, color, notifications),
        isOnline,
        table,
        graphs,
        users,
        admin,
        repositoryViewModelAPI
    )

    private val mutableIllegalOperation: Map<Operation, MutableSharedFlow<Boolean>>
    override val isIllegalOperation: Map<Operation, Flow<Boolean>>

    private val executeQueue = repositoryViewModelAPI.projectHandler.executeQueue

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

    suspend fun addGraph(graph: Graph<*, *>) {
        mutableIllegalOperation[Operation.ADD_GRAPH]!!.emit(false)
        @Suppress("DEPRECATION")
        executeQueue.add(AddGraph(id, graph, repositoryViewModelAPI))
    }

    suspend fun addRow(row: Row) {
        table.addRow(row)
    }

    suspend fun deleteRow(row: Int) {
        table.deleteRow(row)
    }

    suspend fun addColumn(specs: ColumnData, default: Any) {
        table.addColumn(specs, default)
    }

    suspend fun deleteColumn(column: Int) {
        table.deleteColumn(column)
    }

    suspend fun addUIElements(col: Int, uiElementList: List<UIElement>) {
        for (uiElement in uiElementList) {
            table.addUIElement(col, uiElement)
        }
    }

    suspend fun deleteUIElement(col: Int, id: Int) {
        table.removeUIElement(col, id)
    }

    suspend fun delete() {
        TODO("deleteProj")
    }

    suspend fun addUser(user: User) {
        if (user !in users && users.size < Project.MAXIMUM_PROJECT_USERS) {
            mutableIllegalOperation[Operation.ADD_USER]!!.emit(false)
            @Suppress("DEPRECATION")
            executeQueue.add(AddUser(id, user, repositoryViewModelAPI))
        } else {
            throw IllegalOperationException(
                "Could not add the User ${Gson().toJson(user)} to " +
                        "project with id $id. Perhaps they are already a member of that project " +
                        "or the maximum number of members has been reached for that project"
            )
        }
    }

    suspend fun removeUser(user: User) {
        if (user in users && users.size > 1) {
            TODO("deleteMember")
        } else {
            //TODO this error Message doesn't make any sense whatsoever considering what the if statement checks against
            throw IllegalOperationException("This command is only usable by project admins and you are no project admin.")
        }
    }

    suspend fun leaveOnlineProject() {
        TODO("leaveOnlineProject")
    }

    suspend fun setAdmin(user: User) {
        TODO("setAdmin")
    }

    override suspend fun setColor(color: Int) {
        TODO("changeWallpaper")
    }

    suspend fun changeNotification(notification: Notification) {
        TODO("setNotification")
    }

    suspend fun deleteNotification(notification: Notification) {
        mutableIllegalOperation[Operation.DELETE_NOTIFICATION]!!.emit(false)
        TODO("Not yet implemented after refactoring")
    }

    suspend fun addNotification(notification: Notification) {
        mutableIllegalOperation[Operation.ADD_NOTIFICATION]!!.emit(false)
        @Suppress("DEPRECATION")
        executeQueue.add(AddNotification(id, notification, repositoryViewModelAPI))
    }

    override suspend fun setName(name: String) {
        mutableIllegalOperation[Operation.SET_PROJECT_NAME]!!.emit(false)
        @Suppress("DEPRECATION")
        executeQueue.add(SetTitle(this, name, repositoryViewModelAPI))
    }

    override suspend fun setDesc(desc: String) {
        mutableIllegalOperation[Operation.SET_PROJECT_DESC]!!.emit(false)
        @Suppress("DEPRECATION")
        executeQueue.add(SetDescription(this, desc, repositoryViewModelAPI))
    }

    override suspend fun setPath(path: String) {
        TODO("Not yet implemented")
    }

    override fun createTransformationFromString(transformationString: String): Project.DataTransformation<out Any> {
        TODO("Not yet implemented")
    }

    suspend fun publish() {
        mutableIllegalOperation[Operation.PUBLISH_PROJECT]!!.emit(false)
        @Suppress("DEPRECATION")
        executeQueue.add(PublishProject(this, repositoryViewModelAPI))
    }
}