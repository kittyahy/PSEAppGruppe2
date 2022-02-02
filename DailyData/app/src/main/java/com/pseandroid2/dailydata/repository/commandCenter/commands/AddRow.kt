package com.pseandroid2.dailydata.repository.commandCenter.commands

import com.pseandroid2.dailydata.model.database.AppDataBase
import com.pseandroid2.dailydata.remoteDataSource.RemoteDataSourceAPI
import com.pseandroid2.dailydata.repository.commandCenter.PublishQueue
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Row

class AddRow(private val projectId: Int, private val row: Row) :
    ProjectCommand(projectID = projectId) {
    override val publishable: Boolean = true

    override suspend fun execute(
        appDataBase: AppDataBase,
        remoteDataSourceAPI: RemoteDataSourceAPI,
        publishQueue: PublishQueue
    ) {
        appDataBase.tableContentDAO().insertRow(row.toDBEquivalent(), projectId)
        super.execute(appDataBase, remoteDataSourceAPI, publishQueue)
    }

}
