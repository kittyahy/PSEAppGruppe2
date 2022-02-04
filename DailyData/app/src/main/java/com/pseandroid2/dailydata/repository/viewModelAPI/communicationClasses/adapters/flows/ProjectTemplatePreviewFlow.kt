package com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.adapters.flows

import com.pseandroid2.dailydata.model.database.AppDataBase
import com.pseandroid2.dailydata.repository.commandCenter.ExecuteQueue
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.ProjectTemplatePreview
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ProjectTemplatePreviewFlow(
    private val provider: ProjectTemplateDataFlowProvider,
    private val eq: ExecuteQueue
) {
    fun getTemplatePreviews(): Flow<List<ProjectTemplatePreview>> =
        provider.provideFlow.distinctUntilChanged()
            .map { templateDataPairs ->
                val previews = mutableListOf<ProjectTemplatePreview>()
                for (pair in templateDataPairs) {
                    val preview = ProjectTemplatePreview(pair.first, pair.second)
                    @Suppress("Deprecation")
                    preview.executeQueue = eq
                    previews.add(preview)
                }
                previews.toList()
            }
}