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


class GraphTemplateFlowProvider(private val db: AppDataBase) :
    FlowProvider<List<GraphTemplate>>() {
    private var templates = mutableListOf<GraphTemplate>()
    override suspend fun initialize() = coroutineScope {
        //Observe GraphTemplateData
        launch(Dispatchers.IO) {
            db.templateDAO().getAllGraphTemplateData().distinctUntilChanged()
                .collect { templateData ->
                    for (data in templateData) {
                        templates.add(SimpleGraphTemplate(data))
                    }
                    mutableFlow.emit(templates)
                }
        }
        //Observe Template Settings
        for (id in getIds()) {
            launch(Dispatchers.IO) {
                db.settingsDAO().getGraphSettings(TEMPLATE_SETTINGS_PROJ_ID, id)
                    .distinctUntilChanged().collect { settings ->
                        getTemplateWithId(id)!!.customizing = settings
                        mutableFlow.emit(templates)
                    }
            }
        }
    }

    private fun getIds() = Array(templates.size) { index ->
        templates[index].id
    }

    private fun getTemplateWithId(id: Int): GraphTemplate? {
        for (template in templates) {
            if (template.id == id) {
                return template
            }
        }
        return null
    }
}


