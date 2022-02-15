package com.pseandroid2.dailydata.repository.commandCenter.commands

import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.repository.commandCenter.PublishQueue
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Column
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.DataType
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Project

class AddColumn(projectID: Int, column: Column) : ProjectCommand(projectID = projectID) {
    companion object {
        fun isPossible(project: Project, type: DataType): Boolean {
            var total = DataType.storageSizeBaseline
            for (col in project.table) {
                col.dataType.storageSize
            }
            total += type.storageSize
            return total <= DataType.maxStorageSize
        }

        const val publishable: Boolean = true
    }

    override suspend fun execute(
        repositoryViewModelAPI: RepositoryViewModelAPI,
        publishQueue: PublishQueue
    ) {
        repositoryViewModelAPI.appDataBase.tableContentDAO()
        TODO("insertColumn(row.toDBEquivalent(), projectId)")
        super.execute(repositoryViewModelAPI, publishQueue)
    }

    override fun publish(
        repositoryViewModelAPI: RepositoryViewModelAPI,
        publishQueue: PublishQueue
    ): Boolean {
        return super.publish(repositoryViewModelAPI, publishQueue) && publishable
    }

}
