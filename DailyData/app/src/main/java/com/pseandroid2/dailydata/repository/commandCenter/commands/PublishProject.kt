package com.pseandroid2.dailydata.repository.commandCenter.commands

import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.repository.commandCenter.PublishQueue
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.ViewModelProject

class PublishProject(private val viewModelProject: ViewModelProject) : ProjectCommand() {
    override val publishable: Boolean = true

    override suspend fun publish(
        repositoryViewModelAPI: RepositoryViewModelAPI,
        publishQueue: PublishQueue
    ): Boolean {
        return super.publish(repositoryViewModelAPI, publishQueue)
    }

    companion object {
        fun isPossible(viewModelProject: ViewModelProject): Boolean {
            return !viewModelProject.isOnlineProject
        }
    }

    override suspend fun execute(
        repositoryViewModelAPI: RepositoryViewModelAPI,
        publishQueue: PublishQueue
    ) {
        repositoryViewModelAPI.appDataBase.projectDataDAO().setOnlineID(
            viewModelProject.id,
            repositoryViewModelAPI.remoteDataSourceAPI.createNewOnlineProject("")
        ) //Todo Fehlerbehandlung, falls publishen fehl schlägt
        viewModelProject.isOnlineProject = true
        if (publish(repositoryViewModelAPI, publishQueue)) {
            publishQueue.add(
                CreateProject(
                    viewModelProject.title,
                    viewModelProject.desc,
                    viewModelProject.wallpaper,
                    viewModelProject.table,
                    viewModelProject.buttons,
                    viewModelProject.notifications,
                    viewModelProject.graphs //Todo Problem wenn teilnehmende noch nicht die Verwendeten Graphtypen hätten
                )
            )
        }
    }

}
