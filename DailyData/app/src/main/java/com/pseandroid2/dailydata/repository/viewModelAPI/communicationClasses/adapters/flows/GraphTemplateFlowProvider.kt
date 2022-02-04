package com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.adapters.flows

import com.pseandroid2.dailydata.model.database.AppDataBase
import com.pseandroid2.dailydata.model.database.daos.GraphCDManager.Companion.TEMPLATE_SETTINGS_PROJ_ID
import com.pseandroid2.dailydata.model.graph.GraphTemplate
import com.pseandroid2.dailydata.model.graph.GraphType
import com.pseandroid2.dailydata.model.graph.SimpleGraphTemplate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.distinctUntilChanged


class GraphTemplateFlowProvider(private val templateId: Int, private val db: AppDataBase) :
    FlowProvider<List<GraphTemplate>>() {
    private var templates = mutableListOf<GraphTemplate>()
    override suspend fun initialize() = coroutineScope {
        //Observe GraphTemplateData
        launch(Dispatchers.IO) {
            db.templateDAO().getGraphTemplatesForProjectTemplate(templateId).distinctUntilChanged()
                .collect { templateData ->
                    for (data in templateData) {
                        templates.add(SimpleGraphTemplate(data))
                    }
                    mutableFlow.emit(templates)
                }
        }
        Unit
    }
}


