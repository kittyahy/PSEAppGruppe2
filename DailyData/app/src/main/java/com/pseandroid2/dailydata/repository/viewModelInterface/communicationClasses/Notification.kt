package com.pseandroid2.dailydata.repository.viewModelInterface.communicationClasses

import java.time.LocalDateTime

data class Notification(
    override val id: Long,
    val message: String,
    val time: LocalDateTime
): Identifiable
