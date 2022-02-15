package com.pseandroid2.dailydata.repository.commandCenter.commands

import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.repository.commandCenter.PublishQueue
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.ViewModelProject
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Row

class AddRow(private val projectId: Int, private val row: Row) :
    ProjectCommand(projectID = projectId) {
    companion object {
        fun isPossible(viewModelProject: ViewModelProject): Boolean {
            return ProjectCommand.isPossible(viewModelProject)
        }
    }

    override val publishable: Boolean = true

    override suspend fun execute(
        repositoryViewModelAPI: RepositoryViewModelAPI,
        publishQueue: PublishQueue
    ) {
        repositoryViewModelAPI.appDataBase.tableContentDAO().insertRow(row.toDBEquivalent(), projectId)
        super.execute(repositoryViewModelAPI, publishQueue)
    }

}
