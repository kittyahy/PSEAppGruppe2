package com.pseandroid2.dailydata.repository.commandCenter.commands

import com.pseandroid2.dailydata.model.project.Project
import com.pseandroid2.dailydata.model.table.Row
import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI

class DeleteRow(projectID: Int, private val row: Row, api: RepositoryViewModelAPI) :
    ProjectCommand(projectID = projectID, repositoryViewModelAPI = api) {
    companion object {
        fun isPossible(project: Project): Boolean {
            return ProjectCommand.isPossible(project)
        }

        const val isAdminOperation: Boolean = false

        const val publishable: Boolean = true
    }

    override suspend fun execute() {
        @Suppress("DEPRECATION")
        repositoryViewModelAPI.appDataBase.tableContentDAO()
            .deleteRows(projectID!!, row)
        super.execute()
    }

    override suspend fun publish(): Boolean {
        return super.publish() && publishable
    }

}
