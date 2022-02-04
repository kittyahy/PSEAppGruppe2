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

package com.pseandroid2.dailydata.repository.viewModelAPI


import com.pseandroid2.dailydata.model.database.AppDataBase
import com.pseandroid2.dailydata.remoteDataSource.RemoteDataSourceAPI
import com.pseandroid2.dailydata.repository.commandCenter.ExecuteQueue
import com.pseandroid2.dailydata.repository.commandCenter.commands.CreateProject
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Button
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Column
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Graph
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Notification
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Project
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.adapters.flows.GraphFlow
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.adapters.flows.GraphTemplateFlow
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.adapters.flows.ProjectFlow
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.adapters.flows.ProjectPreviewFlow
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.adapters.flows.ProjectTemplateFlow
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.adapters.flows.ProjectTemplatePreviewFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.first

@InternalCoroutinesApi
class ProjectHandler(
    private val appDataBase: AppDataBase,
    private val rds: RemoteDataSourceAPI,
    private val executeQueue: ExecuteQueue
) {
    fun getProjectPreviews() = ProjectPreviewFlow(appDataBase, executeQueue).getPreviews()

    fun getProjectByID(id: Int) = ProjectFlow(appDataBase, rds, executeQueue, id).getProject()

    fun getGraphs(projectId: Int) = GraphFlow(appDataBase, executeQueue, projectId).getGraphs()

    fun getGraphTemplates(projectTemplateId: Int) =
        GraphTemplateFlow(appDataBase, executeQueue, projectTemplateId).getTemplates()

    fun getProjectTemplatePreviews() =
        ProjectTemplatePreviewFlow(appDataBase, executeQueue).getTemplatePreviews()

    fun getProjectTemplate(id: Int) =
        ProjectTemplateFlow(appDataBase, executeQueue, id).getProjectTemplate()


    private suspend fun newProjectAsync(
        name: String,
        description: String,
        wallpaper: Int,
        table: List<Column>,
        buttons: List<Button>,
        notification: List<Notification>,
        graphs: List<Graph>
    ) = coroutineScope {
        async(Dispatchers.IO) {
            val idFlow = MutableSharedFlow<Int>()
            val createProject = CreateProject(
                idFlow,
                "User1",
                name,
                description,
                wallpaper,
                table,
                buttons,
                notification,
                graphs
            )
            executeQueue.add(createProject)
            return@async idFlow.first()
        }
    }

    suspend fun newProjectAsync(project: Project) = coroutineScope {
        async(Dispatchers.IO) {
            return@async newProjectAsync(
                project.title,
                project.description,
                project.wallpaper,
                project.table,
                project.buttons,
                project.notifications,
                project.graphs
            )
        }
    }

    fun joinOnlineProject(onlineID: Long): Int {
        return TODO("joinOnlineProject")
    }

    fun getProjectTemplateByID() {
        TODO("getProjectTemplateByID")
    }

    //TODO("Robin changes")
    fun deleteProjectTemplate(id: Int) {

    }

    //TODO("Robin changes")
    fun deleteGraphTemplate(id: Int) {

    }
}