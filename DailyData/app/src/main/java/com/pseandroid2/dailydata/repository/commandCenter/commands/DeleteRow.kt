package com.pseandroid2.dailydata.repository.commandCenter.commands

import com.pseandroid2.dailydata.model.table.Row
import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.repository.commandCenter.PublishQueue
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Project

class DeleteRow(projectID: Int, private val row: Row) :
    ProjectCommand(projectID = projectID) {
    companion object {
        fun isPossible(project: Project): Boolean {
            return ProjectCommand.isPossible(project)
        }

        const val issuerNeedsAdminRights: Boolean = false

        const val publishable: Boolean = true
    }

    override suspend fun execute(
        repositoryViewModelAPI: RepositoryViewModelAPI,
        publishQueue: PublishQueue
    ) {
        @Suppress("DEPRECATION")
        repositoryViewModelAPI.appDataBase.tableContentDAO()
            .deleteRows(projectID!!, row)
        super.execute(repositoryViewModelAPI, publishQueue)
    }

    override suspend fun publish(
        repositoryViewModelAPI: RepositoryViewModelAPI,
        publishQueue: PublishQueue
    ): Boolean {
        return super.publish(repositoryViewModelAPI, publishQueue) && publishable
    }

}
