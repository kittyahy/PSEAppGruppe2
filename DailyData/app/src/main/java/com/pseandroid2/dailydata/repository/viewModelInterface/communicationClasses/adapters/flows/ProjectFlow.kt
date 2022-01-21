package com.pseandroid2.dailydata.repository.viewModelInterface.communicationClasses.adapters.flows

import com.pseandroid2.dailydata.model.GraphType
import com.pseandroid2.dailydata.model.database.AppDataBase
import com.pseandroid2.dailydata.repository.viewModelInterface.communicationClasses.Button
import com.pseandroid2.dailydata.repository.viewModelInterface.communicationClasses.Column
import com.pseandroid2.dailydata.repository.viewModelInterface.communicationClasses.Graph
import com.pseandroid2.dailydata.repository.viewModelInterface.communicationClasses.LineChart
import com.pseandroid2.dailydata.repository.viewModelInterface.communicationClasses.Member
import com.pseandroid2.dailydata.repository.viewModelInterface.communicationClasses.Notification
import com.pseandroid2.dailydata.repository.viewModelInterface.communicationClasses.Project
import com.pseandroid2.dailydata.repository.viewModelInterface.communicationClasses.Row
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
) {
    private val sharedFlow = MutableSharedFlow<Project>()
    private val graphFlow = appDataBase.graphDAO().getGraphEntityForProjects(projectId)
    private val notificationFlow = appDataBase.notificationsDAO().getNotificationEntities(projectId)
    private val projectDataFlow = appDataBase.projectDataDAO().getProjectData(projectId)
    private val settingsFlow: Flow<Map<String, String>> = TODO()
    private val tableContentFlow = appDataBase.tableContentDAO().getRowsById(projectId)
    private val uIElementFlow = appDataBase.uiElementDAO().getUIElements(projectId)

    init {
        GlobalScope.launch {
            adapt()
        }
    }

    fun getProjectPreviewFlow(): Flow<Project> {
        return sharedFlow
    }

    @InternalCoroutinesApi
    suspend fun adapt() {
        val emptyProject = Project(
            projectId.toLong(),
            false,
            false,
            "title missing",
            "description missing",
            "wallpaper missing",
            ArrayList<Column>(),
            ArrayList<Row>(),
            ArrayList<Button>(),
            ArrayList<Notification>(),
            ArrayList<Graph>(),
            ArrayList<Member>()
        )
        val emptyFlow = MutableSharedFlow<Project>()
        emptyFlow.emit(emptyProject)
        emptyFlow.combine(graphFlow) { project, graphList ->
            project.graphs = graphList
        }
        sharedFlow.emit(listProjectPreview)
    }
    private fun modelGraphListToVMGraphList(graphEntityList: List<com.pseandroid2.dailydata.model.Graph>): List<Graph> {
        val vMGraphList = ArrayList<Graph>()
        for (graphEntity: com.pseandroid2.dailydata.model.Graph in graphEntityList) {
            if (graphEntity.getType() == GraphType.LINE_CHART) {
                val graph = LineChart(graphEntity.id.toLong(), )
            }

        }
    }
}
