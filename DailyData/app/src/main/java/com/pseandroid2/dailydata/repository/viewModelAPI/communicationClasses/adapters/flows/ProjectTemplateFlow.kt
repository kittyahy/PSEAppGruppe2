package com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.adapters.flows

import com.pseandroid2.dailydata.model.database.entities.ProjectTemplateData
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.ProjectTemplate
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow


//TODO("Robin changes")
@InternalCoroutinesApi
class ProjectTemplateFlow(flow: Flow<List<ProjectTemplateData>>) : FlowAdapter<ProjectTemplateData, ProjectTemplate> (flow){
    override fun provide(i: ProjectTemplateData): ProjectTemplate {
        TODO("ich hab keine ahung von dem kram")
    }

}