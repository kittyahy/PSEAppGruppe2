package com.pseandroid2.dailydata.repository.commandCenter.commands

import com.pseandroid2.dailydata.model.database.AppDataBase
import com.pseandroid2.dailydata.remoteDataSource.RemoteDataSourceAPI
import com.pseandroid2.dailydata.repository.commandCenter.PublishQueue
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Project

class PublishProject(private val id: Int, private val project: Project) : ProjectCommand() {
    override val publishable: Boolean = true

    override suspend fun publish(
        appDataBase: AppDataBase,
        remoteDataSourceAPI: RemoteDataSourceAPI,
        publishQueue: PublishQueue
    ): Boolean {
        return super.publish(appDataBase, remoteDataSourceAPI, publishQueue)
    }

    companion object {
        fun isPossible(project: Project): Boolean {
            return !project.isOnlineProject
        }
    }

    override suspend fun execute(
        appDataBase: AppDataBase,
        remoteDataSourceAPI: RemoteDataSourceAPI,
        publishQueue: PublishQueue
    ) {
        appDataBase.projectDataDAO().setOnlineID(
            project.id,
            remoteDataSourceAPI.addProject()
        ) //Todo Fehlerbehandlung, falls publishen fehl schlägt
        project.isOnlineProject = true
        if (publish(appDataBase, remoteDataSourceAPI, publishQueue)) {
            publishQueue.add(
                CreateProject(
                    remoteDataSourceAPI.getUserID(), //Todo mit Arne Absprechen, was wir nehmen
                    project.title,
                    project.description,
                    project.wallpaper,
                    project.table,
                    project.buttons,
                    project.notifications,
                    project.graphs //Todo Problem wenn teilnehmende noch nicht die Verwendeten Graphtypen hätten
                )
            )
        }
    }

}
