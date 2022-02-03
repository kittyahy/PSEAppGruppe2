package com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.adapters.flows

import com.google.firebase.auth.ktx.actionCodeSettings
import com.pseandroid2.dailydata.model.database.AppDataBase
import com.pseandroid2.dailydata.model.project.ProjectTemplate
import com.pseandroid2.dailydata.model.project.SimpleProjectTemplate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class ProjectTemplateFlowProvider(private val templateId: Int, private val db: AppDataBase) :
    FlowProvider<ProjectTemplate?>() {
    private var projectTemplate = SimpleProjectTemplate(TODO(), TODO(), TODO())
    override suspend fun initialize() = coroutineScope {
        //Observe Template Data
        launch(Dispatchers.IO) {
            db.templateDAO().getProjectTemplateData(templateId).distinctUntilChanged()
                .collect { templateData ->
                    if (templateData.size != 1) {
                        //Multiple or no template with that id
                        mutableFlow.emit(null)
                    } else {
                        projectTemplate = SimpleProjectTemplate(templateData[0])
                        mutableFlow.emit(projectTemplate)
                    }
                }
        }
        //Observe Settings for Template
        launch(Dispatchers.IO) {
            db.settingsDAO().getProjectSettings(templateId).distinctUntilChanged()
                .collect { settings ->
                    projectTemplate.settings = settings
                    mutableFlow.emit(projectTemplate)
                }
        }
        //Observe Graphs for Template
        launch(Dispatchers.IO) {
            GraphFlowProvider(templateId, db).provideFlow.distinctUntilChanged().collect { graphs ->
                projectTemplate.graphs = graphs.toMutableList()
                mutableFlow.emit(projectTemplate)
            }
        }
        TODO()
        Unit
    }
}