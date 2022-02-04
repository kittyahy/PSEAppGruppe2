package com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.adapters.flows

import com.pseandroid2.dailydata.model.database.AppDataBase
import com.pseandroid2.dailydata.repository.commandCenter.ExecuteQueue
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.GraphTemplate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class GraphTemplateFlow(
    private val db: AppDataBase,
    private val eq: ExecuteQueue,
    private val templateId: Int
) {
    fun getTemplates(): Flow<List<GraphTemplate>> {
        return GraphTemplateFlowProvider(templateId, db).provideFlow.distinctUntilChanged()
            .map { templates ->
                val list = mutableListOf<GraphTemplate>()
                for (template in templates) {
                    val addTemplate = GraphTemplate(
                        template.name,
                        template.getWallpaper(),
                        template.id,
                    )
                    //TODO figure out if GraphTemplates should have an executeQueue?
                    //addTemplate.executeQueue = eq
                    list.add(addTemplate)
                }
                list.toList()
            }
    }
}
