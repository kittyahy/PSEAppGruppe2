package com.pseandroid2.dailydata.repository.commandCenter.commands

import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.repository.commandCenter.PublishQueue
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Notification
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Project

class AddNotification(val id: Int, val notification: Notification) : ProjectCommand() {
    companion object {
        fun isPossible(project: Project): Boolean {
            return ProjectCommand.isPossible(project)
        }
    }
    override val publishable: Boolean = false

    override suspend fun execute(
        repositoryViewModelAPI: RepositoryViewModelAPI,
        publishQueue: PublishQueue
    ) {
        repositoryViewModelAPI.appDataBase.notificationsDAO().insertNotification(id, notification.toDBEquivalent())
        super.execute(repositoryViewModelAPI, publishQueue)
    }

}