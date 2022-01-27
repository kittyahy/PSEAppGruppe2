package com.pseandroid2.dailydata.repository.commandCenter.commands

import com.pseandroid2.dailydata.model.database.AppDataBase
import com.pseandroid2.dailydata.remoteDataSource.RemoteDataSourceAPI
import java.time.LocalDateTime

abstract class ProjectCommand (
    var projectID: Int? = null,
    var onlineProjectID: Long? = null,
    var wentOnline: LocalDateTime? = null,
    var serverRemoveTime: LocalDateTime? = null,
    var commandByUser: String? = null,
    var isProjectAdmin: Boolean? = null
){
    open suspend fun execute(appDataBase: AppDataBase, remoteDataSourceAPI: RemoteDataSourceAPI) {
        TODO()
    }
    open suspend fun publish() {
        TODO()
    }
}