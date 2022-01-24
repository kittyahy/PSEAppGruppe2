package com.pseandroid2.dailydata.repository.commandCenter.commands

import java.time.LocalDateTime

abstract class ProjectCommand (
    projectID: Int,
    onlineProjectID: Long?,
    wentOnline: LocalDateTime?,
    serverRemoveTime: LocalDateTime?,
    commandByUser: String?,
    isProjectAdmin: Boolean?
){
    fun execute() {
        TODO()
    }
    fun publish() {
        TODO()
    }
    abstract fun individualExecution()
}