package com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.adapters.flows

import com.pseandroid2.dailydata.model.database.AppDataBase
import com.pseandroid2.dailydata.model.database.entities.ProjectTemplateData
import com.pseandroid2.dailydata.repository.commandCenter.ExecuteQueue
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.ProjectTemplate
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProjectTemplateFlow(
    private val db: AppDataBase,
    private val eq: ExecuteQueue,
    private val templateId: Int
) {

    fun getProjectTemplate(): Flow<ProjectTemplate> {
        return ProjectTemplateFlowProvider(templateId, db).provideFlow.map { template ->
            val ret = if (template != null) {
                ProjectTemplate(template)
            } else {
                ProjectTemplate()
            }
            ret.executeQueue = eq
            return@map ret
        }
    }

}