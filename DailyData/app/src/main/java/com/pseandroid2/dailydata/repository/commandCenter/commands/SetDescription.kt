package com.pseandroid2.dailydata.repository.commandCenter.commands

import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.repository.commandCenter.PublishQueue
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Project

class SetDescription(project: Project, private val newDescription: String) :
    OnlineAdminCommand(project) {

    override val publishable = true

    companion object {
        fun isPossible(project: Project): Boolean {
            return OnlineAdminCommand.isPossible(project)
        }
    }

    override suspend fun execute(
        repositoryViewModelAPI: RepositoryViewModelAPI,
        publishQueue: PublishQueue
    ) {
        repositoryViewModelAPI.appDataBase.projectDataDAO()
            .setDescription(projectID!!, newDescription)
    }
}