package com.pseandroid2.dailydata.repository.commandCenter.commands

import com.pseandroid2.dailydata.model.table.TableLayout
import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI

class DeleteColumn(projectID: Int, private val columnId: Int, api: RepositoryViewModelAPI) :
    ProjectCommand(projectID = projectID, repositoryViewModelAPI = api) {
    companion object {
        fun isIllegal(layout: TableLayout): Boolean {
            return layout.iterator().hasNext()
        }

        const val isAdminOperation: Boolean = true

        const val publishable: Boolean = true

    }

    override suspend fun execute() {
        @Suppress("Deprecation")
        repositoryViewModelAPI.appDataBase.layoutDAO().removeColumnById(projectID, columnId)
    }

    override suspend fun publish(): Boolean {
        return super.publish() && publishable
    }

}
