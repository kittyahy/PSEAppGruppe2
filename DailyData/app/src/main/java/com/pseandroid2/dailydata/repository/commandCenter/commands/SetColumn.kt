package com.pseandroid2.dailydata.repository.commandCenter.commands

import com.pseandroid2.dailydata.model.project.Project
import com.pseandroid2.dailydata.model.table.ColumnData
import com.pseandroid2.dailydata.model.table.TableLayout
import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.DataType

class SetColumn(projectID: Int, private val specs: ColumnData, api: RepositoryViewModelAPI) :
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
        TODO()//repositoryViewModelAPI.appDataBase.layoutDAO().
    }

    override suspend fun publish(): Boolean {
        return super.publish() && publishable
    }

}
