package com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.adapters.flows


import com.pseandroid2.dailydata.model.database.entities.ProjectData
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.ProjectPreview
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow

@InternalCoroutinesApi
class ProjectPreviewFlow(flow: Flow<List<ProjectData>>) : FlowAdapter<ProjectData, ProjectPreview> (flow){
    override fun provide(i: ProjectData): ProjectPreview {
        return ProjectPreview(i)
    }


}