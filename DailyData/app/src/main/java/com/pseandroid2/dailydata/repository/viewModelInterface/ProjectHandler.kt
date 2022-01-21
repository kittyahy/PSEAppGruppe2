package com.pseandroid2.dailydata.repository.viewModelInterface


import com.pseandroid2.dailydata.model.database.AppDataBase
import com.pseandroid2.dailydata.repository.viewModelInterface.communicationClasses.Project
import com.pseandroid2.dailydata.repository.viewModelInterface.communicationClasses.adapters.flows.ProjectFlow
import com.pseandroid2.dailydata.repository.viewModelInterface.communicationClasses.adapters.flows.ProjectPreviewFlow
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow

@InternalCoroutinesApi
class ProjectHandler (val projectPreviewFlow: ProjectPreviewFlow, private val appDataBase: AppDataBase){
    fun getProjectByID(id: Long) : Flow<Project> {
        return ProjectFlow(appDataBase, id.toInt()).getProjectFlow()
    }
}