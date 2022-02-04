package com.pseandroid2.dailydata.repository.commandCenter.commands

import com.pseandroid2.dailydata.model.database.AppDataBase
import com.pseandroid2.dailydata.remoteDataSource.RemoteDataSourceAPI
import com.pseandroid2.dailydata.repository.commandCenter.PublishQueue
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Project
import java.time.LocalDateTime

/**
 * This class defines what a projectCommand has. A project command contains any change, what changes
 * were made in the project.
 */
abstract class ProjectCommand(
    var projectID: Int? = null,
    var onlineProjectID: Long? = null,
    var wentOnline: LocalDateTime? = null,
    var serverRemoveTime: LocalDateTime? = null,
    var commandByUser: String? = null,
    var isProjectAdmin: Boolean? = null
) {
    companion object {
        fun isPossible(project: Project): Boolean {
            return false
        }
    }

    var cameFromServer = false
    open suspend fun execute(
        appDataBase: AppDataBase,
        remoteDataSourceAPI: RemoteDataSourceAPI,
        publishQueue: PublishQueue
    ) {
        if (publish(appDataBase, remoteDataSourceAPI, publishQueue)) {
            publishQueue.add(this)
        }
    }

    /**
     * It published the command to the server.
     */
    open suspend fun publish(
        appDataBase: AppDataBase,
        remoteDataSourceAPI: RemoteDataSourceAPI,
        publishQueue: PublishQueue
    ): Boolean {
        return onlineProjectID != null && !cameFromServer
    }
}