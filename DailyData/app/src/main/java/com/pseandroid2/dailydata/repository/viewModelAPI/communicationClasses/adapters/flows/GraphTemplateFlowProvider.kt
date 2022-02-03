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
    FlowProvider<GraphTemplate?>() {
    private var template: GraphTemplate = SimpleGraphTemplate(type = GraphType.PIE_CHART)
    override suspend fun initialize() = coroutineScope {
        //Observe GraphTemplateData
        launch(Dispatchers.IO) {
            db.templateDAO().getGraphTemplateData(templateId).distinctUntilChanged()
                .collect { templateData ->
                    if (templateData.size != 1) {
                        //Multiple or no Templates with this Id
                        mutableFlow.emit(null)
                    } else {
                        val data = templateData[0]
                        template = SimpleGraphTemplate(data)
                    }
                }
        }
        //Observe Template Settings
        launch(Dispatchers.IO) {
            db.settingsDAO().getGraphSettings(TEMPLATE_SETTINGS_PROJ_ID, templateId)
                .distinctUntilChanged().collect { settings ->
                    template.customizing = settings
                }
        }
        Unit
    }

}
