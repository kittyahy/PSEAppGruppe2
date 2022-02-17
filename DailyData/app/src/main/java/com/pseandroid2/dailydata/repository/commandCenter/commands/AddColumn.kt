package com.pseandroid2.dailydata.repository.commandCenter.commands

import com.pseandroid2.dailydata.model.project.Project
import com.pseandroid2.dailydata.model.table.ColumnData
import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.DataType

class AddColumn(projectID: Int, specs: ColumnData, api: RepositoryViewModelAPI) :
    ProjectCommand(projectID = projectID, repositoryViewModelAPI = api) {
    override val publishable: Boolean = true

    companion object {
        fun isIllegal(project: Project, type: DataType): Boolean {
            var total = DataType.storageSizeBaseline
            for (col in project.table.layout) {
                total += col.type.storageSize
            }
            total += type.storageSize
            return total > DataType.maxStorageSize
        }
    }

    override suspend fun execute() {
        @Suppress("Deprecation")
        repositoryViewModelAPI.appDataBase.tableContentDAO()
        TODO("insertColumn(row.toDBEquivalent(), projectId)")
        super.execute()
    }

}
