/*

    DailyData is an android app to easily create diagrams from data one has collected
    Copyright (C) 2022  Antonia Heiming, Anton Kadelbach, Arne Kuchenbecker, Merlin Opp, Robin Amman

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.

*/

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
        }/*
        //Observe GraphTemplates for Template
        launch(Dispatchers.IO) {
            GraphTemplateFlowProvider(db).provideFlow.distinctUntilChanged()
                .collect { graphs ->
                    template.graphs = graphs.toMutableList()
                    mutableFlow.emit(template)
                }
        }*/
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