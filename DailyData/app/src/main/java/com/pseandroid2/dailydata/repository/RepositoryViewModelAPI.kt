package com.pseandroid2.dailydata.repository

import com.pseandroid2.dailydata.model.database.AppDataBase
import com.pseandroid2.dailydata.repository.viewModelAPI.ProjectHandler
import com.pseandroid2.dailydata.repository.viewModelAPI.ServerHandler
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.adapters.flows.ProjectPreviewFlow
import kotlinx.coroutines.InternalCoroutinesApi

class RepositoryViewModelAPI (appDataBase: AppDataBase){
    val serverHandler = ServerHandler(appDataBase)
    @InternalCoroutinesApi
    val projectHandler = ProjectHandler(ProjectPreviewFlow(appDataBase.projectDataDAO().getAllProjectData()), appDataBase)
}