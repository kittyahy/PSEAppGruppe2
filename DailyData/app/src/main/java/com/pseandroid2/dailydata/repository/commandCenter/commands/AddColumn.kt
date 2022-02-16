package com.pseandroid2.dailydata.repository.commandCenter.commands

import com.pseandroid2.dailydata.model.table.ColumnData
import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.repository.commandCenter.PublishQueue
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.DataType
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.ViewModelProject

class AddColumn(projectID: Int, private val specs: ColumnData, api: RepositoryViewModelAPI) :
    ProjectCommand(projectID = projectID, repositoryViewModelAPI = api) {
    companion object {
        fun isPossible(viewModelProject: ViewModelProject, type: DataType): Boolean {
            var total = DataType.storageSizeBaseline
            for (col in viewModelProject.table.layout) {
                total += col.type.storageSize
            }
            total += type.storageSize
            return total <= DataType.maxStorageSize
        }

        const val isAdminOperation: Boolean = true

        const val publishable: Boolean = true
    }

    override suspend fun execute() {
        @Suppress("Deprecation")
        repositoryViewModelAPI.appDataBase.tableContentDAO()
        TODO("insertColumn(row.toDBEquivalent(), projectId)")
        super.execute()
    }

    override suspend fun publish(): Boolean {
        return super.publish() && publishable
    }

}
