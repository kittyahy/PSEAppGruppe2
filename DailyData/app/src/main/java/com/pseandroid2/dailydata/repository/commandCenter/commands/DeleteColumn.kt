package com.pseandroid2.dailydata.repository.commandCenter.commands

import com.pseandroid2.dailydata.model.project.Project
import com.pseandroid2.dailydata.model.table.ColumnData
import com.pseandroid2.dailydata.model.table.TableLayout
import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.DataType

class DeleteColumn(projectID: Int, private val columnId: Int, api: RepositoryViewModelAPI) :
    ProjectCommand(projectID = projectID, repositoryViewModelAPI = api) {
    companion object {
        fun isIllegal(project: Project): Boolean {
            return project.table.layout.iterator().hasNext()
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
