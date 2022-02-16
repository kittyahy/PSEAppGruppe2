package com.pseandroid2.dailydata.repository.commandCenter.commands

import com.pseandroid2.dailydata.model.table.Row
import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.ViewModelProject

class AddRow(projectID: Int, private val row: Row, api: RepositoryViewModelAPI) :
    ProjectCommand(projectID = projectID, repositoryViewModelAPI = api) {
    companion object {
        fun isIllegal(viewModelProject: ViewModelProject): Boolean {
            return ProjectCommand.isIllegal(viewModelProject)
        }
    }

    override val publishable: Boolean = true

    override suspend fun execute() {
        @Suppress("Deprecation")
        repositoryViewModelAPI.appDataBase.tableContentDAO().insertRow(row, projectID!!)
        super.execute()
    }

}
