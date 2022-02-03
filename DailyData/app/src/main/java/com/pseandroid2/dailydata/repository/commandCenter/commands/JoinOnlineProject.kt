package com.pseandroid2.dailydata.repository.commandCenter.commands

import kotlinx.coroutines.flow.MutableSharedFlow

class JoinOnlineProject(onlineID: Long, idFlow: MutableSharedFlow<Int>) : ProjectCommand() {
    companion object {
        fun isPossible(): Boolean {
            return false
        }
    }

    override val publishable: Boolean = false


}
