package com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverReturns

import java.time.LocalDateTime

// A Delta is a ProjectCommand
data class Delta(val addedToServer: LocalDateTime = java.time.LocalDateTime.parse("0001-01-01T00:00"),
                 val user: String = "",
                 val projectCommand: String = "",
                 val project: Long = -1,
                 val requestedBy: String = "",
                 val isAdmin: Boolean = false)
