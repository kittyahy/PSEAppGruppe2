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

import com.pseandroid2.dailydata.model.database.AppDataBase
import com.pseandroid2.dailydata.model.database.entities.ProjectData
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Button
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Graph
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Member
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Notification
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Project
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Row
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

@InternalCoroutinesApi
class ProjectFlow(
    private val appDataBase: AppDataBase,
    private val projectId: Int
) : FlowAdapter<Void, Project>(MutableSharedFlow()) {
    private lateinit var sharedFlow: Flow<Project>
    private val buttonFlow: Flow<List<Button>> = ButtonFlow(
        appDataBase.uiElementDAO().getUIElements(projectId)
    ).getFlow() //Todo Arne fragen passt so
    private val graphFlow: Flow<List<Graph>> =
        GraphFlow(TODO("GraphFlow")).getFlow() //appDataBase.graphDAO().getGraphDataForProject(projectId)
    private val memberFlow: Flow<List<Member>> = MemberFlow(TODO("MemberFlow")).getFlow()
    private val notificationFlow: Flow<List<Notification>> =
        NotificationFlow(appDataBase.notificationsDAO().getNotifications(projectId)).getFlow()
    private val projectDataFlow: Flow<ProjectData?> =
        appDataBase.projectDataDAO().getProjectData(projectId)
    private val settingsFlow: Flow<Map<String, String>> = TODO("settingsFlow")
    private val rowFlow: Flow<List<Row>> =
        RowFlow(appDataBase.tableContentDAO().getRowsById(projectId)).getFlow()

    init {
        GlobalScope.launch {
            adapt()
        }
    }

    fun getProjectFlow(): Flow<Project> {
        return sharedFlow
    }

    @InternalCoroutinesApi
    override suspend fun adapt() {
        val emptyProject = Project()
        val emptyFlow = MutableSharedFlow<Project>()
        emptyFlow.emit(emptyProject)
        sharedFlow = emptyFlow.combine(buttonFlow) { project, buttonList ->
            project.buttons = buttonList
            project
        }.combine(graphFlow) { project, graphList ->
            project.graphs = graphList
            project
        }.combine(memberFlow) { project, memberList ->
            project.members = memberList
            project
        }.combine(notificationFlow) { project, notificationList ->
            project.notifications = notificationList
            project
        }.combine(projectDataFlow) { project, projectData ->
            projectData!! //Todo überprüfen, ob man diese Assertion hier so verwenden darf
            project.update(projectData)
            project
        }.combine(settingsFlow) { project, settingsMap ->
            //Todo project.update(settingsMap)
            project
        }.combine(rowFlow) { project, rowList ->
            project.data = rowList
            project
        }
    }

    override fun provide(i: Void): Project {
        //unreachable code, because it is never used by overwritten adapt fun
        return Project()
    }
}
