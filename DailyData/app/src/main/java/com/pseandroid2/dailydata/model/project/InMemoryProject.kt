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

package com.pseandroid2.dailydata.model.project

import com.pseandroid2.dailydata.model.graph.Graph
import com.pseandroid2.dailydata.model.notifications.Notification
import com.pseandroid2.dailydata.model.table.ArrayListTable
import com.pseandroid2.dailydata.model.table.ColumnData
import com.pseandroid2.dailydata.model.table.Table
import com.pseandroid2.dailydata.model.uielements.UIElement
import com.pseandroid2.dailydata.model.users.NullUser
import com.pseandroid2.dailydata.model.users.User
import com.pseandroid2.dailydata.repository.commandCenter.commands.IllegalOperationException
import com.pseandroid2.dailydata.repository.viewModelAPI.ProjectHandler
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.ProjectOperation
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.MutableSharedFlow

class InMemoryProject
constructor(
    private val skeleton: ProjectSkeleton,
    initialTable: Table,
    initialAdmin: User,
    override var isOnline: Boolean,
    private val mutableUsers: MutableList<User>,
    private val mutableGraphs: MutableList<Graph<*, *>>
) : Project {

    constructor(
        id: Int = -1,
        onlineId: Long = -1,
        name: String = "",
        desc: String = "",
        color: Int = 0,
        path: String = "",
        notifications: MutableList<Notification> = mutableListOf(),
        isOnline: Boolean = false,
        table: Table = ArrayListTable(),
        mutableGraphs: MutableList<Graph<*, *>> = mutableListOf(),
        mutableUsers: MutableList<User> = mutableListOf(),
        admin: User = NullUser()
    ) : this(
        SimpleSkeleton(id, onlineId, name, desc, path, color, notifications),
        table,
        admin,
        isOnline,
        mutableUsers,
        mutableGraphs,
    )

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

    private var mutableTable: Table = initialTable
    override val table: Table
        get() = mutableTable

    private var mutableAdmin: User = initialAdmin
    override val admin: User
        get() = mutableAdmin

    override val graphs: List<Graph<*, *>>
        get() = mutableGraphs

    override val users: List<User>
        get() = mutableUsers

    override var id: Int
        get() = skeleton.id
        set(value) {
            skeleton.id = value
        }

    override val name: String
        get() = skeleton.name

    override suspend fun setName(name: String) {
        skeleton.name = name
    }

    override val desc: String
        get() = skeleton.desc

    override suspend fun setDesc(desc: String) {
        skeleton.desc = desc
    }

    override val path: String
        get() = skeleton.path

    override suspend fun setPath(path: String) {
        skeleton.path = path
    }

    override val color: Int
        get() = skeleton.color

    override suspend fun setColor(color: Int) {
        skeleton.color = color
    }

    override suspend fun addGraph(graph: Graph<*, *>) {
        for (existingGraph in mutableGraphs) {
            if (existingGraph.id == graph.id) {
                return
            }
        }
        mutableGraphs.add(graph)
    }

    override suspend fun addGraphs(graphsToAdd: Collection<Graph<*, *>>) {
        for (graph in graphsToAdd) {
            addGraph(graph)
        }
    }

    override suspend fun removeGraph(id: Int) {
        mutableGraphs.removeIf { graph ->
            graph.id == id
        }
    }

    override val notifications: List<Notification>
        get() = skeleton.notifications

    override suspend fun addNotification(notification: Notification) {
        for (notif in skeleton.notifications) {
            if (notif.id == notification.id) {
                return
            }
        }
        skeleton.notifications.add(notification)
    }

    override suspend fun addNotifications(notifications: Collection<Notification>) {
        for (notif in notifications) {
            addNotification(notif)
        }
    }

    override suspend fun removeNotification(id: Int) {
        skeleton.notifications.removeIf { notif ->
            notif.id == id
        }
    }

    override suspend fun changeNotification(id: Int, notification: Notification) {
        removeNotification(id)
        notification.id = id
        addNotification(notification)
    }

    override suspend fun addColumn(specs: ColumnData, default: Any) {
        table.addColumn(specs, default)
    }

    override suspend fun deleteColumn(column: Int) {
        table.deleteColumn(column)
    }

    override suspend fun addUIElement(col: Int, uiElement: UIElement) {
        table.layout.addUIElement(col, uiElement)
    }

    override suspend fun deleteUIElement(col: Int, id: Int) {
        table.layout.removeUIElement(col, id)
    }

    override suspend fun resetAdmin() {
        throw IllegalOperationException("Local Project only. Create an actual project in order to publish it")
    }

    override val onlineId: Long
        get() = skeleton.onlineId

    override suspend fun publish() {
        throw IllegalOperationException("Local Project only. Create an actual project in order to publish it")
    }

    override suspend fun unsubscribe() {
        throw IllegalOperationException("Local Project only. Create an actual project in order to unlink it")
    }

    override suspend fun addUser(user: User) {
        for (existingUser in mutableUsers) {
            if (existingUser.id == user.id) {
                return
            }
        }
        mutableUsers.add(user)
    }

    override suspend fun addUsers(usersToAdd: Collection<User>) {
        for (user in usersToAdd) {
            addUser(user)
        }
    }

    override suspend fun removeUser(user: User) {
        mutableUsers.remove(user)
    }

    override suspend fun delete() {
        throw IllegalOperationException("Local Project only. Has not been saved to the database and thus can't be removed from it either")
    }

    override fun createTransformationFromString(transformationString: String): Project.DataTransformation<out Any> {
        TODO("Not yet implemented")
    }

    fun setTable(table: Table) {
        mutableTable = table
    }

    suspend fun writeToDBAsync(projectHandler: ProjectHandler): Deferred<Int> {
        return projectHandler.newProjectAsync(this)
    }
}