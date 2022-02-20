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
import com.pseandroid2.dailydata.model.users.SimpleUser
import com.pseandroid2.dailydata.model.users.User
import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.repository.commandCenter.commands.AddGraph
import com.pseandroid2.dailydata.repository.commandCenter.commands.AddNotification
import com.pseandroid2.dailydata.repository.commandCenter.commands.AddUser
import com.pseandroid2.dailydata.repository.commandCenter.commands.DeleteGraph
import com.pseandroid2.dailydata.repository.commandCenter.commands.DeleteNotification
import com.pseandroid2.dailydata.repository.commandCenter.commands.DeleteProject
import com.pseandroid2.dailydata.repository.commandCenter.commands.DeleteUser
import com.pseandroid2.dailydata.repository.commandCenter.commands.IllegalOperationException
import com.pseandroid2.dailydata.repository.commandCenter.commands.LeaveOnlineProject
import com.pseandroid2.dailydata.repository.commandCenter.commands.ProjectCommand
import com.pseandroid2.dailydata.repository.commandCenter.commands.PublishProject
import com.pseandroid2.dailydata.repository.commandCenter.commands.ResetAdmin
import com.pseandroid2.dailydata.repository.commandCenter.commands.SetDescription
import com.pseandroid2.dailydata.repository.commandCenter.commands.SetTitle
import com.pseandroid2.dailydata.repository.commandCenter.commands.SetWallpaper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class PersistentProject(
    private val project: Project,
    val repositoryViewModelAPI: RepositoryViewModelAPI,
    scope: CoroutineScope
) : Project {


    private val executeQueue = repositoryViewModelAPI.projectHandler.executeQueue

    override val graphs: List<Graph<*, *>>
        get() = project.graphs

    override val users: List<User>
        get() = project.users

    override val table: Table = PersistentTable(project.table, repositoryViewModelAPI, scope, id)

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
        scope.launch {
            admin = SimpleUser(
                @Suppress("DEPRECATION")
                repositoryViewModelAPI.remoteDataSourceAPI.getProjectAdmin(
                    repositoryViewModelAPI.appDataBase.projectDataDAO().getOnlineId(id)
                ),
                "ADMIN"
            )
        }

    }

    override var id: Int
        get() = project.id
        set(value) {
            throw IllegalOperationException("Re-Setting the id of a project is not permitted")
        }

    override val name: String
        get() = project.name

    override suspend fun setName(name: String) {
        saveToDB(SetTitle(id, name, repositoryViewModelAPI), ProjectOperation.SET_PROJECT_NAME.id)
    }

    override val desc: String
        get() = project.desc

    override suspend fun setDesc(desc: String) {
        saveToDB(SetDescription(id, desc, repositoryViewModelAPI), ProjectOperation.SET_PROJECT_DESC.id)
    }

    override val path: String
        get() = project.path

    override suspend fun setPath(path: String) {
        TODO("Not yet implemented")
    }

    override val color: Int
        get() = project.color

    override suspend fun setColor(color: Int) {
        saveToDB(SetWallpaper(id, color, repositoryViewModelAPI), ProjectOperation.SET_COLOR.id)
    }

    override suspend fun addGraph(graph: Graph<*, *>) {
        saveToDB(AddGraph(id, graph, repositoryViewModelAPI), ProjectOperation.ADD_GRAPH.id)
    }

    override suspend fun addGraphs(graphsToAdd: Collection<Graph<*, *>>) {
        for (graph in graphsToAdd) {
            addGraph(graph)
        }
    }

    override suspend fun removeGraph(id: Int) {
        saveToDB(DeleteGraph(this.id, id, repositoryViewModelAPI), ProjectOperation.DELETE_GRAPH.id)
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

    override lateinit var admin: User

    override suspend fun addNotification(notification: Notification) {
        saveToDB(AddNotification(id, notification, repositoryViewModelAPI), ProjectOperation.ADD_NOTIFICATION.id)
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
        saveToDB(DeleteNotification(this.id, id, repositoryViewModelAPI), ProjectOperation.DELETE_NOTIFICATION.id)
    }

    override suspend fun addUser(user: User) {
        if (user !in users && users.size < Project.MAXIMUM_PROJECT_USERS) {
            saveToDB(AddUser(id, user, repositoryViewModelAPI), ProjectOperation.ADD_USER.id)
        } else {
            throw IllegalOperationException(
                "Could not add the User ${Gson().toJson(user)} to " +
                        "project with id $id. Perhaps they are already a member of that project " +
                        "or the maximum number of members has been reached for that project"
            )
        }
    }

    override suspend fun addUsers(usersToAdd: Collection<User>) {
        for (user in usersToAdd) {
            saveToDB(AddUser(id, user, repositoryViewModelAPI), ProjectOperation.ADD_USER.id)
        }
    }

    override suspend fun removeUser(user: User) {
        @Suppress("DEPRECATION")
        if (user.id == admin.id) {
            saveToDB(ResetAdmin(id, repositoryViewModelAPI), ProjectOperation.SET_ADMIN.id)
            if (user in users && users.size > 1) {
                saveToDB(DeleteUser(id, user, repositoryViewModelAPI), ProjectOperation.DELETE_USER.id)
            } else {
                throw IllegalOperationException("This project does not contain this user.")
            }
        }

    }

    override val onlineId: Long
        get() = project.onlineId

    override suspend fun publish() {
        saveToDB(PublishProject(id, repositoryViewModelAPI), ProjectOperation.PUBLISH_PROJECT.id)
    }

    override suspend fun resetAdmin() {
        saveToDB(ResetAdmin(id, repositoryViewModelAPI), ProjectOperation.SET_ADMIN.id)
    }

    override val isOnline: Boolean
        get() = onlineId != (-1).toLong()

    override suspend fun unsubscribe() {
        saveToDB(LeaveOnlineProject(id, repositoryViewModelAPI), ProjectOperation.LEAVE_PROJECT.id)
    }

    override suspend fun delete() {
        saveToDB(DeleteProject(id, repositoryViewModelAPI), ProjectOperation.DELETE.id)
    }

    private suspend fun saveToDB(projectCommand: ProjectCommand, vararg operationIDs: String) {
        for (id in operationIDs) {
            @Suppress("DEPRECATION")
            mutableIllegalOperation[id]!!.emit(false)
        }
        executeQueue.add(projectCommand)
    }
}