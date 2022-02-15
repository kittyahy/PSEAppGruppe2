package com.pseandroid2.dailydata.repository.commandCenter.commands

import com.pseandroid2.dailydata.model.table.ColumnData
import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.repository.commandCenter.PublishQueue
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.DataType
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.ViewModelProject

class AddColumn(projectID: Int, specs: ColumnData) : ProjectCommand(projectID = projectID) {
    override val publishable: Boolean = true

    companion object {
        fun isPossible(viewModelProject: ViewModelProject, type: DataType): Boolean {
            var total = DataType.storageSizeBaseline
            for (col in viewModelProject.table.layout) {
                total += col.type.storageSize
            }
            total += type.storageSize
            return total <= DataType.maxStorageSize
        }
    }

    override suspend fun execute(
        repositoryViewModelAPI: RepositoryViewModelAPI,
        publishQueue: PublishQueue
    ) {
        repositoryViewModelAPI.appDataBase.tableContentDAO()
        TODO("insertColumn(row.toDBEquivalent(), projectId)")
        super.execute(repositoryViewModelAPI, publishQueue)
    }

}
