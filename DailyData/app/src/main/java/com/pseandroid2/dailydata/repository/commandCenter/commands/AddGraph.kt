package com.pseandroid2.dailydata.repository.commandCenter.commands

import com.pseandroid2.dailydata.model.database.AppDataBase
import com.pseandroid2.dailydata.remoteDataSource.RemoteDataSourceAPI
import com.pseandroid2.dailydata.repository.commandCenter.PublishQueue
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Graph

class AddGraph(val id: Int, val graph: Graph) : ProjectCommand() {
    override val publishable: Boolean = false
    override suspend fun execute(
        appDataBase: AppDataBase,
        remoteDataSourceAPI: RemoteDataSourceAPI,
        publishQueue: PublishQueue
    ) {
        appDataBase.graphCDManager().insertGraph(id, graph.toDBEquivalent())
        super.execute(appDataBase, remoteDataSourceAPI, publishQueue)
    }
}
