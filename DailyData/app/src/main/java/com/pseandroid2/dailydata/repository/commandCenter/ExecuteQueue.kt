package com.pseandroid2.dailydata.repository.commandCenter

import com.pseandroid2.dailydata.remoteDataSource.RemoteDataSourceAPI
import com.pseandroid2.dailydata.repository.commandCenter.commands.ProjectCommand

class ExecuteQueue(remoteDataSourceAPI: RemoteDataSourceAPI) : CommandQueue(remoteDataSourceAPI) {
    override suspend fun performCommandAction(command: ProjectCommand) {
        command.execute()
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