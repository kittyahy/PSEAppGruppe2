package com.pseandroid2.dailydata.repository.commandCenter.commands

import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.repository.commandCenter.PublishQueue
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Project

class PublishProject(private val id: Int, private val project: Project) : ProjectCommand() {
    override val publishable: Boolean = true

    override suspend fun publish(
        repositoryViewModelAPI: RepositoryViewModelAPI,
        publishQueue: PublishQueue
    ): Boolean {
        return super.publish(repositoryViewModelAPI, publishQueue)
    }

    companion object {
        fun isPossible(project: Project): Boolean {
            return !project.isOnlineProject
        }
    }

    override suspend fun execute(
        repositoryViewModelAPI: RepositoryViewModelAPI,
        publishQueue: PublishQueue
    ) {
        repositoryViewModelAPI.appDataBase.projectDataDAO().setOnlineID(
            project.id,
            repositoryViewModelAPI.remoteDataSourceAPI.addProject()
        ) //Todo Fehlerbehandlung, falls publishen fehl schlägt
        project.isOnlineProject = true
        if (publish(repositoryViewModelAPI, publishQueue)) {
            publishQueue.add(
                CreateProject(
                    repositoryViewModelAPI.remoteDataSourceAPI.getUserID(), //Todo mit Arne Absprechen, was wir nehmen
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
