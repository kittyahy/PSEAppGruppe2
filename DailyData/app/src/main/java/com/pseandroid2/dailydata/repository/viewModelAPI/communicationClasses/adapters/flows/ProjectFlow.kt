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

package com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.adapters.flows

import com.google.gson.Gson
import com.pseandroid2.dailydata.model.database.AppDataBase
import com.pseandroid2.dailydata.model.notifications.TimeNotification
import com.pseandroid2.dailydata.model.table.ArrayListLayout
import com.pseandroid2.dailydata.remoteDataSource.RemoteDataSourceAPI
import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.repository.commandCenter.ExecuteQueue
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Button
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Graph
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Member
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Notification
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Project
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Row
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.toColumnList
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.toViewGraph
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@InternalCoroutinesApi
class ProjectFlow(
    private val repositoryViewModelAPI: RepositoryViewModelAPI,
    private val provider: ProjectFlowProvider
) {
    @Suppress("DEPRECATION")
    private val db: AppDataBase = repositoryViewModelAPI.appDataBase

    @Suppress("DEPRECATION")
    private val rds: RemoteDataSourceAPI = repositoryViewModelAPI.remoteDataSourceAPI
    private val eq: ExecuteQueue = repositoryViewModelAPI.projectHandler.executeQueue
    fun getProject(): Flow<Project> = provider.provideFlow.distinctUntilChanged().map { project ->
        val ret = if (project == null) {
            Project(repositoryViewModelAPI = repositoryViewModelAPI)
        } else {
            val rows = mutableListOf<Row>()
            for (row in project.table) {
                rows.add(Row(row))
            }
            val buttons = mutableListOf<Button>()
            for (col in project.table.getLayout()) {
                for (uiElement in col.uiElements) {
                    buttons.add(Button(uiElement, col.id))
                }
            }
            val notifications = mutableListOf<Notification>()
            for (notif in project.notifications) {
                if (notif is TimeNotification) {
                    notifications.add(Notification(notif))
                }
            }
            val graphs = mutableListOf<Graph>()
            for (graph in project.graphs) {
                graphs.add(
                    graph.toViewGraph(
                        Gson().fromJson(
                            db.projectDataDAO().getCurrentLayout(provider.projId),
                            ArrayListLayout::class.java
                        )
                    )
                )
            }
            val members = mutableListOf<Member>()
            for (user in project.users) {
                members.add(Member(user))
            }
            Project(
                project.id,
                project.isOnline,
                rds.getUserID() == project.admin.getId(),
                project.name,
                project.desc,
                project.color,
                project.table.getLayout().toColumnList(),
                rows.toList(),
                buttons.toList(),
                notifications.toList(),
                graphs.toList(),
                members.toList(),
                repositoryViewModelAPI
            )
        }
        @Suppress("Deprecation")
        ret.executeQueue = eq
        return@map ret
    }
}

