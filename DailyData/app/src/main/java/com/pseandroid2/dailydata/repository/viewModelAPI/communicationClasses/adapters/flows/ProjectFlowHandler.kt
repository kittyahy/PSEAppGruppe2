package com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.adapters.flows

import android.util.Log
import com.google.gson.Gson
import com.pseandroid2.dailydata.model.database.AppDataBase
import com.pseandroid2.dailydata.model.project.Project
import com.pseandroid2.dailydata.model.project.SimpleProjectBuilder
import com.pseandroid2.dailydata.util.Consts
import com.pseandroid2.dailydata.util.Consts.LOG_TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProjectFlowHandler(private val projId: Int, private val db: AppDataBase) {
    private val project = SimpleProjectBuilder().setId(projId).build()
    private val projectFlow = MutableSharedFlow<Project?>()

    suspend fun initialize() = coroutineScope {
        //Observe changes to ProjectData
        launch(Dispatchers.IO) {
            Log.i(LOG_TAG, "Start observing ProjectData for Project with id $projId.")
            db.projectDataDAO().getProjectData(projId).distinctUntilChanged().collect {
                if (it != null) {
                    project.name = it.name
                    project.desc = it.description
                    project.onlineId = it.onlineId
                    project.path = it.wallpaper
                    project.color = it.color
                    projectFlow.emit(project)
                } else {
                    Log.d(LOG_TAG, "ProjectData was null")
                    projectFlow.emit(null)
                }
            }
        }
        //Observe changes to Project Settings
        launch(Dispatchers.IO) {
            Log.i(LOG_TAG, "Start observing Settings for Project with id $projId.")
            db.settingsDAO().getProjectSettings(projId).distinctUntilChanged().collect { settings ->
                project.settings = settings
                projectFlow.emit(project)
            }
        }
    }

    fun getProject() = projectFlow
}