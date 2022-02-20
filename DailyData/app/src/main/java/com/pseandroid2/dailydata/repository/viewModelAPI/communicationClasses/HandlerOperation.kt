package com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses

import com.pseandroid2.dailydata.repository.commandCenter.commands.CreateProject
import com.pseandroid2.dailydata.repository.commandCenter.commands.JoinOnlineProject
import com.pseandroid2.dailydata.repository.viewModelAPI.ProjectHandler

enum class HandlerOperation(val id: String, val adminOp: Boolean) {
    CREATE_PROJECT("CREATE_PROJECT", false) {
        override fun isIllegalByData(projectHandler: ProjectHandler): Boolean {
            return CreateProject.isIllegal(projectHandler)
        }
    },
    JOIN_ONLINE_PROJECT("JOIN_ONLINE_PROJECT", false) {
        override fun isIllegalByData(projectHandler: ProjectHandler): Boolean {
            return JoinOnlineProject.isIllegal(projectHandler)
        }
    };


    open fun isIllegalByData(projectHandler: ProjectHandler): Boolean = false
}