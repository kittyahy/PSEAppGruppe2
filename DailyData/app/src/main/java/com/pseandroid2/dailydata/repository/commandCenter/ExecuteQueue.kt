package com.pseandroid2.dailydata.repository.commandCenter

import com.pseandroid2.dailydata.model.database.AppDataBase
import com.pseandroid2.dailydata.remoteDataSource.RemoteDataSourceAPI
import com.pseandroid2.dailydata.remoteDataSource.queue.ProjectCommandQueueObserver
import com.pseandroid2.dailydata.repository.commandCenter.commands.CommandWrapper
import com.pseandroid2.dailydata.repository.commandCenter.commands.ProjectCommand
import kotlinx.coroutines.launch

class ExecuteQueue(
    appDataBase: AppDataBase,
    remoteDataSourceAPI: RemoteDataSourceAPI,
    private val publishQueue: PublishQueue
) :
    CommandQueue(appDataBase, remoteDataSourceAPI), ProjectCommandQueueObserver {
    override suspend fun performCommandAction(command: ProjectCommand) {
        command.execute(appDataBase, remoteDataSourceAPI, publishQueue)
    }

    //No contingency plan required because execution will always succeed
    override suspend fun commandActionSucceedsProbably(): Boolean {
        return true
    }

    //No contingency plan required because execution will always succeed
    override suspend fun beforeCommandActionAttempt(command: ProjectCommand) {}

    //No contingency plan required because execution will always succeed
    override suspend fun commandFailedAction(command: ProjectCommand) {}

    override fun update() {
        while (remoteDataSourceAPI.getProjectCommandQueueLength() > 0) {
            val projectCommandInfo = remoteDataSourceAPI.getProjectCommandFromQueue()!!
            var command = CommandWrapper.fromJson(projectCommandInfo.projectCommand)
            command = CommandUtility.setServerInfo(command, projectCommandInfo)
            scope.launch {
                add(command)
            }
        }
    }
}