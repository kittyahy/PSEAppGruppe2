package com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.adapters.flows

import com.pseandroid2.dailydata.model.database.AppDataBase
import com.pseandroid2.dailydata.model.database.entities.ProjectTemplateData
import com.pseandroid2.dailydata.repository.commandCenter.ExecuteQueue
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.ProjectTemplate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ProjectTemplateFlow(
    private val db: AppDataBase,
    private val eq: ExecuteQueue,
    private val provider: ProjectTemplateFlowProvider
) {

    fun getProjectTemplate(): Flow<ProjectTemplate> =
        provider.provideFlow.distinctUntilChanged().map { template ->
            val ret = if (template != null) {
                ProjectTemplate(template)
            } else {
                ProjectTemplate()
            }
            @Suppress("Deprecation")
            ret.executeQueue = eq
            return@map ret
        }
}
