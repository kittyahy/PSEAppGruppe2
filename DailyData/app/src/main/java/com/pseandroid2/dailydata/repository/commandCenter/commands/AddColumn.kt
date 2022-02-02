package com.pseandroid2.dailydata.repository.commandCenter.commands

import com.pseandroid2.dailydata.model.database.AppDataBase
import com.pseandroid2.dailydata.remoteDataSource.RemoteDataSourceAPI
import com.pseandroid2.dailydata.repository.commandCenter.PublishQueue
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Column

class AddColumn(projectID: Int, column: Column) : ProjectCommand(projectID = projectID) {

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
