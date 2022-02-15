package com.pseandroid2.dailydata.repository.commandCenter.commands

import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.repository.commandCenter.PublishQueue
import com.pseandroid2.dailydata.model.notifications.Notification
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.ViewModelProject

class AddNotification(val id: Int, val notification: Notification) : ProjectCommand() {
    companion object {
        fun isPossible(viewModelProject: ViewModelProject): Boolean {
            return ProjectCommand.isPossible(viewModelProject)
        }
    }

    override val publishable: Boolean = false

    override suspend fun execute(
        repositoryViewModelAPI: RepositoryViewModelAPI,
        publishQueue: PublishQueue
    ) {
        @Suppress("Deprecation")
        repositoryViewModelAPI.appDataBase.notificationsDAO().insertNotification(id, notification)
        super.execute(repositoryViewModelAPI, publishQueue)
    }

}
