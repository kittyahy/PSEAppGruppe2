package com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.adapters.flows

import com.pseandroid2.dailydata.model.database.AppDataBase
import com.pseandroid2.dailydata.model.database.entities.ProjectTemplateData
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.ProjectTemplate
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProjectTemplateFlow(private val templateId: Int, private val db: AppDataBase) {

    fun getProjectTemplate(): Flow<ProjectTemplate> {
        return ProjectTemplateFlowProvider(templateId, db).provideFlow.map {
            ProjectTemplate(it)
        }
    }

}