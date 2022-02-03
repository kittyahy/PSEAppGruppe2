package com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.adapters.flows

import com.pseandroid2.dailydata.model.database.AppDataBase
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.GraphTemplate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class GraphTemplateFlow(private val db: AppDataBase) {
    fun getTemplate(): Flow<List<GraphTemplate>> {
        return GraphTemplateFlowProvider(db).provideFlow.distinctUntilChanged()
            .map { templates ->
                val list = mutableListOf<GraphTemplate>()
                for (template in templates) {
                    list.add(
                        GraphTemplate(
                            template.name,
                            template.getWallpaper(),
                            template.id,
                        )
                    )
                }
                list.toList()
            }
    }
}
