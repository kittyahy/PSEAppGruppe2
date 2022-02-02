package com.pseandroid2.dailydata.repository.commandCenter.commands

import com.pseandroid2.dailydata.model.database.AppDataBase
import com.pseandroid2.dailydata.remoteDataSource.RemoteDataSourceAPI
import com.pseandroid2.dailydata.repository.commandCenter.PublishQueue
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Column
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.DataType
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Project

class AddColumn(projectID: Int, column: Column) : ProjectCommand(projectID = projectID) {
    override val publishable: Boolean = true

    companion object {
        fun isPossible(project: Project, type: DataType): Boolean {
            var total = DataType.storageSizeBaseline
            for (col in project.table) {
                col.dataType.storageSize
            }
            total += type.storageSize
            return total <= DataType.maxStorageSize
        }
    }

    override suspend fun execute(
        appDataBase: AppDataBase,
        remoteDataSourceAPI: RemoteDataSourceAPI,
        publishQueue: PublishQueue
    ) {
        appDataBase.tableContentDAO()
        TODO("insertColumn(row.toDBEquivalent(), projectId)")
        super.execute(appDataBase, remoteDataSourceAPI, publishQueue)
    }

}
