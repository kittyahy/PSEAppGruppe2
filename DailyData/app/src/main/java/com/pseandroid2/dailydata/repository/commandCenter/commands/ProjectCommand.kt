package com.pseandroid2.dailydata.repository.commandCenter.commands

import java.time.LocalDateTime

abstract class ProjectCommand (
    var projectID: Int,
    var onlineProjectID: Long? = null,
    var wentOnline: LocalDateTime? = null,
    var serverRemoveTime: LocalDateTime? = null,
    var commandByUser: String? = null,
    var isProjectAdmin: Boolean? = null
){
    open fun execute() {
        TODO()
    }
    open fun publish() {
        TODO()
    }
}