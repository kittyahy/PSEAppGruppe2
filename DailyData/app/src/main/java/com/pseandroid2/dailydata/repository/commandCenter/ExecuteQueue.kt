package com.pseandroid2.dailydata.repository.commandCenter

import com.pseandroid2.dailydata.model.database.AppDataBase
import com.pseandroid2.dailydata.remoteDataSource.RemoteDataSourceAPI
import com.pseandroid2.dailydata.repository.commandCenter.commands.ProjectCommand

class ExecuteQueue(appDataBase: AppDataBase, remoteDataSourceAPI: RemoteDataSourceAPI) :
    CommandQueue( appDataBase, remoteDataSourceAPI) {
    override suspend fun performCommandAction(command: ProjectCommand) {
        command.execute(appDataBase, remoteDataSourceAPI)
    }

    //No contingency plan required because execution will always succeed
    override suspend fun commandActionSucceedsProbably(): Boolean {
        return true
    }

    //No contingency plan required because execution will always succeed
    override suspend fun beforeCommandActionAttempt(command: ProjectCommand) {}

    //No contingency plan required because execution will always succeed
    override suspend fun commandFailedAction(command: ProjectCommand) {}
}