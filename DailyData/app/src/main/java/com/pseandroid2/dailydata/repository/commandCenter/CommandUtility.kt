package com.pseandroid2.dailydata.repository.commandCenter

import com.pseandroid2.dailydata.remoteDataSource.queue.ProjectCommandInfo
import com.pseandroid2.dailydata.repository.commandCenter.commands.ProjectCommand

object CommandUtility {
    fun setServerInfo(
        projectCommand: ProjectCommand,
        projectCommandInfo: ProjectCommandInfo
    ): ProjectCommand {
        projectCommand.cameFromServer = true
        projectCommand.wentOnline = projectCommandInfo.wentOnline
        projectCommand.commandByUser = projectCommandInfo.commandByUser
        projectCommand.isProjectAdmin = projectCommandInfo.isProjectAdmin
        projectCommand.projectID =
            TODO("ProjectID") //Todo Arne Fragen wie ich zwischen online/Offline ids übersetzen kann
        return projectCommand
    }
}