package com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.adapters.flows

import com.pseandroid2.dailydata.model.database.AppDataBase
import com.pseandroid2.dailydata.repository.commandCenter.ExecuteQueue
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.ProjectTemplatePreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class ProjectTemplatePreviewFlow(private val db: AppDataBase, private val eq: ExecuteQueue) {
    fun getTemplatePreviews(): Flow<List<ProjectTemplatePreview>> {
        return ProjectTemplateDataFlowProvider(db).provideFlow.distinctUntilChanged()
            .map { templateDataPairs ->
                val previews = mutableListOf<ProjectTemplatePreview>()
                for (pair in templateDataPairs) {
                    val preview = ProjectTemplatePreview(pair.first, pair.second)
                    preview.executeQueue = eq
                    previews.add(preview)
                }
                previews.toList()
            }
    }
}