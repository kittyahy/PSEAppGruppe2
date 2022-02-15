package com.pseandroid2.dailydata.repository.commandCenter

import com.pseandroid2.dailydata.remoteDataSource.queue.ProjectCommandInfo
import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.repository.commandCenter.commands.ProjectCommand

object CommandUtility {
    fun setServerInfo(
        projectCommand: ProjectCommand,
        projectCommandInfo: ProjectCommandInfo,
        repositoryViewModelAPI: RepositoryViewModelAPI
    ): ProjectCommand {
        projectCommand.cameFromServer = true
        projectCommand.wentOnline = projectCommandInfo.wentOnline
        projectCommand.commandByUser = projectCommandInfo.commandByUser
        projectCommand.isProjectAdmin = projectCommandInfo.isProjectAdmin
        @Suppress("DEPRECATION")
        projectCommand.projectID = repositoryViewModelAPI.appDataBase.projectDataDAO()
            .getIdForOnlineId(projectCommandInfo.onlineProjectID)
        return projectCommand
    }
}