package com.pseandroid2.dailydata.repository.viewModelInterface.communicationClasses.adapters.flows

import com.pseandroid2.dailydata.model.GraphType
import com.pseandroid2.dailydata.model.database.AppDataBase
import com.pseandroid2.dailydata.model.database.entities.ProjectData
import com.pseandroid2.dailydata.repository.viewModelInterface.communicationClasses.Button
import com.pseandroid2.dailydata.repository.viewModelInterface.communicationClasses.Graph
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
    private val buttonFlow : Flow<List<Button>> = ButtonFlow(appDataBase.uiElementDAO().getUIElements(projectId)).getFlow()
    private val graphFlow : Flow<List<Graph>> = GraphFlow(appDataBase.graphDAO().getGraphEntityForProjects(projectId)).getFlow()
    private val memberFlow : Flow<List<Member>> = MemberFlow(TODO()).getFlow()
    private val notificationFlow : Flow<List<Notification>> = NotificationFlow(appDataBase.notificationsDAO().getNotificationEntities(projectId)).getFlow()
    private val projectDataFlow : Flow<ProjectData?> = appDataBase.projectDataDAO().getProjectData(projectId)
    private val settingsFlow: Flow<Map<String, String>> = TODO()
    private val rowFlow : Flow<List<Row>> = RowFlow(appDataBase.tableContentDAO().getRowsById(projectId)).getFlow()

    init {
        GlobalScope.launch {
            adapt()
        }
    }

    fun getProjectFlow(): Flow<Project> {
        return sharedFlow
    }

    @InternalCoroutinesApi
    suspend fun adapt() {
        val emptyProject = Project()
        val emptyFlow = MutableSharedFlow<Project>()
        emptyFlow.emit(emptyProject)
        emptyFlow.combine(buttonFlow) { project, graphList ->
            TODO()//project.graphs = graphList
        }
        sharedFlow//TODO .emit(listProjectPreview)
    }
}
