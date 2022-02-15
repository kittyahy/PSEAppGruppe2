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
import com.pseandroid2.dailydata.model.project.Project
import com.pseandroid2.dailydata.remoteDataSource.RemoteDataSourceAPI
import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.repository.commandCenter.ExecuteQueue
import com.pseandroid2.dailydata.repository.commandCenter.PublishQueue
import com.pseandroid2.dailydata.repository.commandCenter.commands.CreateProject
import com.pseandroid2.dailydata.repository.commandCenter.commands.JoinOnlineProject
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Button
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Column
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Graph
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Notification
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.ViewModelProject
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.adapters.flows.GraphTemplateFlow
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.adapters.flows.GraphTemplateFlowProvider
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.adapters.flows.ProjectFlow
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.adapters.flows.ProjectFlowProvider
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.adapters.flows.ProjectPreviewFlow
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.adapters.flows.ProjectPreviewFlowProvider
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.adapters.flows.ProjectTemplateDataFlowProvider
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.adapters.flows.ProjectTemplateFlow
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.adapters.flows.ProjectTemplateFlowProvider
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.adapters.flows.ProjectTemplatePreviewFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ProjectHandler(
    private val repositoryViewModelAPI: RepositoryViewModelAPI
) {
    val executeQueue: ExecuteQueue =
        ExecuteQueue(repositoryViewModelAPI, PublishQueue(repositoryViewModelAPI))

    var previewsInitialized = false

    private val appDataBase: AppDataBase = repositoryViewModelAPI.appDataBase
    private val rds: RemoteDataSourceAPI = repositoryViewModelAPI.remoteDataSourceAPI

    private lateinit var projectPreviewProvider: ProjectPreviewFlowProvider
    private lateinit var graphTemplateProvider: GraphTemplateFlowProvider
    private lateinit var projectTemplateDataProvider: ProjectTemplateDataFlowProvider
    private val projectFlows = mutableMapOf<Int, ProjectFlowProvider>()
    private val templateFlows = mutableMapOf<Int, ProjectTemplateFlowProvider>()

    suspend fun initializePreviews() = coroutineScope {
        launch {
            projectPreviewProvider = ProjectPreviewFlowProvider(appDataBase)
            projectPreviewProvider.initialize()
        }
        launch {
            graphTemplateProvider = GraphTemplateFlowProvider(appDataBase)
            graphTemplateProvider.initialize()
        }
        launch {
            projectTemplateDataProvider = ProjectTemplateDataFlowProvider(appDataBase)
            projectTemplateDataProvider.initialize()
        }
        previewsInitialized = true
    }

    suspend fun initializeProjectProvider(id: Int) = coroutineScope {
        launch {
            projectFlows[id] = ProjectFlowProvider(id, appDataBase)
            projectFlows[id]!!.initialize()
        }
    }

    suspend fun initializeProjectTemplateProvider(id: Int) = coroutineScope {
        launch {
            templateFlows[id] = ProjectTemplateFlowProvider(id, appDataBase)
            templateFlows[id]!!.initialize()
        }
    }

    fun getProjectPreviews() =
        ProjectPreviewFlow(projectPreviewProvider, appDataBase, executeQueue).getPreviews()

    fun getProjectByID(id: Int) =
        ProjectFlow(repositoryViewModelAPI, projectFlows[id]!!).getProject()

    fun getGraphTemplates() =
        GraphTemplateFlow(appDataBase, executeQueue, graphTemplateProvider).getTemplates()

    fun getProjectTemplatePreviews() =
        ProjectTemplatePreviewFlow(projectTemplateDataProvider, executeQueue).getTemplatePreviews()

    fun getProjectTemplate(id: Int) =
        ProjectTemplateFlow(appDataBase, executeQueue, templateFlows[id]!!).getProjectTemplate()


    suspend fun newProjectAsync(project: Project) = coroutineScope {
        async(Dispatchers.IO) {
            val idFlow = MutableSharedFlow<Int>()
            val createProject = CreateProject(project, idFlow, repositoryViewModelAPI)
            executeQueue.add(createProject)
            return@async idFlow.first()
        }
    }

    /**
     * If false, it would be imprudent to use the corresponding "manipulation" fun.
     * Thus it should be used to block input options from being used if false.
     * e.g. If manipulationIsPossible.first() is false,
     *      users should not be able to call manipulation().
     */
    fun joinOnlineProjectIsPossible(): Flow<Boolean> {
        val flow = MutableSharedFlow<Boolean>(1)
        runBlocking {
            flow.emit(JoinOnlineProject.isPossible())
        }
        return flow
    }


    suspend fun joinOnlineProject(onlineID: Long): Flow<Int> {
        val idFlow = MutableSharedFlow<Int>()
        executeQueue.add(JoinOnlineProject(onlineID, idFlow, repositoryViewModelAPI))
        return idFlow

    }
}