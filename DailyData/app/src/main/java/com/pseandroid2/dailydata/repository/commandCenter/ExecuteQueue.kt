package com.pseandroid2.dailydata.repository.commandCenter

import com.pseandroid2.dailydata.remoteDataSource.queue.ProjectCommandQueueObserver
import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.repository.commandCenter.commands.CommandWrapper
import com.pseandroid2.dailydata.repository.commandCenter.commands.ProjectCommand
import kotlinx.coroutines.launch

class ExecuteQueue(
    override val repositoryViewModelAPI: RepositoryViewModelAPI,
    private val publishQueue: PublishQueue
) :
    CommandQueue(repositoryViewModelAPI), ProjectCommandQueueObserver {
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

    override fun update() {
        while (repositoryViewModelAPI.remoteDataSourceAPI.getProjectCommandQueueLength() > 0) {
            val projectCommandInfo =
                repositoryViewModelAPI.remoteDataSourceAPI.getProjectCommandFromQueue()!!
            var command = CommandWrapper.fromJson(projectCommandInfo.projectCommand)
            command =
                CommandUtility.setServerInfo(command, projectCommandInfo, repositoryViewModelAPI)
            scope.launch {
                add(command)
            }
        }
    }
}