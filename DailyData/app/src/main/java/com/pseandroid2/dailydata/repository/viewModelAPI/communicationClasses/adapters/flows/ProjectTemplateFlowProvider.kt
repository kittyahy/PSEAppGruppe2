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
    private var template = SimpleProjectTemplate()
    override suspend fun initialize() = coroutineScope {
        //Observe Template Data
        launch(Dispatchers.IO) {
            db.templateDAO().getProjectTemplateData(templateId).distinctUntilChanged()
                .collect { templateData ->
                    if (templateData.size != 1) {
                        //multiple or no templates with this id
                        mutableFlow.emit(null)
                    } else {
                        template = SimpleProjectTemplate(templateData[0])
                        mutableFlow.emit(template)
                    }
                }
        }
        //Observe Settings for Template
        launch(Dispatchers.IO) {
            db.settingsDAO().getProjectSettings(templateId).distinctUntilChanged()
                .collect { settings ->
                    template.settings = settings
                    mutableFlow.emit(template)
                }
        }
        //Observe GraphTemplates for Template
        launch(Dispatchers.IO) {
            GraphTemplateFlowProvider(templateId, db).provideFlow.distinctUntilChanged()
                .collect { graphs ->
                    template.graphs = graphs.toMutableList()
                    mutableFlow.emit(template)
                }
        }
        //Observe Notifications for Template
        launch(Dispatchers.IO) {
            db.notificationsDAO().getNotifications(templateId).distinctUntilChanged()
                .collect { notifications ->
                    template.notifications = notifications.toMutableList()
                    mutableFlow.emit(template)
                }
        }
        Unit
    }
}