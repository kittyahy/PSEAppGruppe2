package com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.adapters.flows

import com.pseandroid2.dailydata.model.database.AppDataBase
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.GraphTemplate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class GraphTemplateFlow(private val templateId: Int, private val db: AppDataBase) {
    fun getTemplate(): Flow<GraphTemplate?> {
        return GraphTemplateFlowProvider(templateId, db).provideFlow.distinctUntilChanged()
            .map { template ->
                if (template == null) {
                    null
                } else {
                    GraphTemplate(
                        template.name,
                        template.getWallpaper(),
                        template.id,
                    )
                }
            }
    }
}