package com.pseandroid2.dailydata.repository.viewModelInterface.communicationClasses.adapters.flows


import com.pseandroid2.dailydata.model.database.entities.ProjectData
import com.pseandroid2.dailydata.repository.viewModelInterface.communicationClasses.ProjectPreview
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@InternalCoroutinesApi
class ProjectPreviewFlow(flow: Flow<List<ProjectData>>) : FlowAdapter<ProjectData, ProjectPreview> (flow){
    override fun provide(i: ProjectData): ProjectPreview {
        return ProjectPreview(i)
    }


}