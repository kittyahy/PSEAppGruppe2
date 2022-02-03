package com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.adapters.flows

import com.pseandroid2.dailydata.model.database.AppDataBase
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.ProjectTemplatePreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class ProjectTemplatePreviewFlow(private val db: AppDataBase) {
    fun getTemplatePreviews(): Flow<List<ProjectTemplatePreview>> {
        return ProjectTemplateDataFlowProvider(db).provideFlow.distinctUntilChanged()
            .map { templateDataPairs ->
                val previews = mutableListOf<ProjectTemplatePreview>()
                for (pair in templateDataPairs) {
                    previews.add(ProjectTemplatePreview(pair.first, pair.second))
                }
                previews.toList()
            }
    }
}