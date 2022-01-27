package com.pseandroid2.dailydata.repository.commandCenter.commands

import com.pseandroid2.dailydata.model.database.AppDataBase
import com.pseandroid2.dailydata.model.database.daos.ProjectCDManager
import com.pseandroid2.dailydata.model.project.Project
import com.pseandroid2.dailydata.remoteDataSource.RemoteDataSourceAPI

class CreateProject (commandByUser: String,
                     private val projectCDManager: ProjectCDManager, //TODO Arne fragen, dass dieses feld auch über appDataBase zugänglich ist
)
    : ProjectCommand(
    commandByUser = commandByUser,
    isProjectAdmin = true) {
    override suspend fun execute(
        appDataBase: AppDataBase,
        remoteDataSourceAPI: RemoteDataSourceAPI
    ) {
        val project: Project = TODO() //Arne fragen
        projectCDManager.insertProject(project)
        super.execute(appDataBase, remoteDataSourceAPI)
    }

    override suspend fun publish() {
    }
}