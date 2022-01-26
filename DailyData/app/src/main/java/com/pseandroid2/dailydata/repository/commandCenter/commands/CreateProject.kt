package com.pseandroid2.dailydata.repository.commandCenter.commands

import com.pseandroid2.dailydata.model.database.AppDataBase
import com.pseandroid2.dailydata.model.database.daos.ProjectCDManager
import com.pseandroid2.dailydata.model.project.Project
import com.pseandroid2.dailydata.remoteDataSource.RemoteDataSourceAPI

class CreateProject (commandByUser: String,
                     appDataBase: AppDataBase,
                     private val projectCDManager: ProjectCDManager, //TODO Arne fragen, dass dieses feld auch über appDataBase zugänglich ist
                     remoteDataSourceAPI: RemoteDataSourceAPI)
    : ProjectCommand(
    appDataBase,
    remoteDataSourceAPI,
    commandByUser = commandByUser,
    isProjectAdmin = true) {
    override suspend fun execute() {
        val project: Project = TODO() //Arne fragen
        projectCDManager.insertProject(project)
        super.execute()
    }

    override suspend fun publish() {
    }
}