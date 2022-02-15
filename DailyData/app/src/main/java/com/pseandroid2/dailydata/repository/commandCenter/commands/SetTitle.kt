package com.pseandroid2.dailydata.repository.commandCenter.commands

import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.repository.commandCenter.PublishQueue
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.ViewModelProject

class SetTitle(viewModelProject: ViewModelProject, private val newTitle: String) : OnlineAdminCommand(viewModelProject) {

    override val publishable = true

    companion object {
        fun isPossible(viewModelProject: ViewModelProject): Boolean {
            return OnlineAdminCommand.isPossible(viewModelProject)
        }
    }

    override suspend fun execute(
        repositoryViewModelAPI: RepositoryViewModelAPI,
        publishQueue: PublishQueue
    ) {
        repositoryViewModelAPI.appDataBase.projectDataDAO().setName(projectID!!, newTitle)
    }
}