package com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.adapters.flows

import com.pseandroid2.dailydata.model.database.AppDataBase
import com.pseandroid2.dailydata.model.database.entities.ProjectData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class ProjectPreviewFlowProvider(private val db: AppDataBase) : FlowProvider<List<ProjectData>>() {
    override suspend fun initialize() = coroutineScope {
        launch(Dispatchers.IO) {
            db.projectDataDAO().getAllProjectData().distinctUntilChanged().collect { projectData ->
                mutableFlow.emit(projectData)
            }
        }
        Unit
    }
}