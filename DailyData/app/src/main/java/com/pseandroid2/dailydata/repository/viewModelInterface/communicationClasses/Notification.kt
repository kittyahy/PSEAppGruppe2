package com.pseandroid2.dailydata.repository.viewModelInterface.communicationClasses

import java.time.LocalDateTime

class Notification(
    override val id: Long,
    val message: String,
    val time: LocalDateTime
): Identifiable {
    override fun deleteIsPossible(): Boolean {
        TODO("Not yet implemented")
    }

    //@throws IllegalOperationException
    override fun delete() {
        TODO("Not yet implemented")
    }
}
