package com.pseandroid2.dailydata.repository.commandCenter.commands

import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.repository.commandCenter.PublishQueue
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Project

class PublishProject(private val project: Project) :
    ProjectCommand(projectID = project.id) {
    companion object {
        fun isPossible(project: Project): Boolean {
            return !project.isOnlineProject
        }

        const val publishable: Boolean = false
    }

    override suspend fun execute(
        repositoryViewModelAPI: RepositoryViewModelAPI,
        publishQueue: PublishQueue
    ) {
        repositoryViewModelAPI.appDataBase.projectDataDAO().setOnlineID(
            projectID!!,
            repositoryViewModelAPI.remoteDataSourceAPI.createNewOnlineProject("")
        ) //Todo Fehlerbehandlung, falls publishen fehl schlägt
        project.isOnlineProject = true
        if (publish(repositoryViewModelAPI, publishQueue)) {
            publishQueue.add(
                CreateProject(
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

    override fun publish(
        repositoryViewModelAPI: RepositoryViewModelAPI,
        publishQueue: PublishQueue
    ): Boolean {
        return super.publish(repositoryViewModelAPI, publishQueue) && publishable
    }

}
