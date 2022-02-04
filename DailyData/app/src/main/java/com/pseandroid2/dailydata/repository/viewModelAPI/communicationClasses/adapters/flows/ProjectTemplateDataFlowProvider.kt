package com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.adapters.flows

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.pseandroid2.dailydata.model.database.AppDataBase
import com.pseandroid2.dailydata.model.database.entities.ProjectTemplateData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class ProjectTemplateDataFlowProvider(private val db: AppDataBase) :
    FlowProvider<List<Pair<ProjectTemplateData, Bitmap?>>>() {
    override suspend fun initialize() = coroutineScope {
        launch(Dispatchers.IO) {
            db.templateDAO().getAllProjectTemplateData().distinctUntilChanged()
                .collect { templateData ->
                    val pairs = mutableListOf<Pair<ProjectTemplateData, Bitmap?>>()
                    for (data in templateData) {
                        pairs.add(Pair(data, BitmapFactory.decodeFile(data.wallpaper)))
                    }
                    mutableFlow.emit(pairs)
                }
        }
        Unit
    }
}