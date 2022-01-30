package com.pseandroid2.dailydata.repository.commandCenter.commands

import com.pseandroid2.dailydata.model.database.AppDataBase
import com.pseandroid2.dailydata.remoteDataSource.RemoteDataSourceAPI
import com.pseandroid2.dailydata.repository.commandCenter.PublishQueue
import java.time.LocalDateTime

abstract class ProjectCommand (
    var projectID: Int? = null,
    var onlineProjectID: Long? = null,
    var wentOnline: LocalDateTime? = null,
    var serverRemoveTime: LocalDateTime? = null,
    var commandByUser: String? = null,
    var isProjectAdmin: Boolean? = null
){
    val cameFromServer = false
    open suspend fun execute(
        appDataBase: AppDataBase,
        remoteDataSourceAPI: RemoteDataSourceAPI,
        publishQueue: PublishQueue
    ) {
        if (publish(appDataBase, remoteDataSourceAPI, publishQueue)) {
            publishQueue.add(this)
        }
    }
    open suspend fun publish(
        appDataBase: AppDataBase,
        remoteDataSourceAPI: RemoteDataSourceAPI,
        publishQueue: PublishQueue): Boolean {
        return onlineProjectID != null && !cameFromServer
    }
}