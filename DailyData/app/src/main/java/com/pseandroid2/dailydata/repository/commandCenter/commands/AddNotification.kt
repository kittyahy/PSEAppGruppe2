package com.pseandroid2.dailydata.repository.commandCenter.commands

import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.repository.commandCenter.PublishQueue
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Notification
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Project

class AddNotification(projectID: Int, val notification: Notification) :
    ProjectCommand(projectID = projectID) {
    companion object {
        fun isPossible(project: Project): Boolean {
            return ProjectCommand.isPossible(project)
        }

        const val publishable: Boolean = false
    }

    override suspend fun execute(
        repositoryViewModelAPI: RepositoryViewModelAPI,
        publishQueue: PublishQueue
    ) {
        repositoryViewModelAPI.appDataBase.notificationsDAO()
            .insertNotification(projectID!!, notification.toDBEquivalent())
        super.execute(repositoryViewModelAPI, publishQueue)
    }

    override fun publish(
        repositoryViewModelAPI: RepositoryViewModelAPI,
        publishQueue: PublishQueue
    ): Boolean {
        return super.publish(repositoryViewModelAPI, publishQueue) && publishable
    }

}
