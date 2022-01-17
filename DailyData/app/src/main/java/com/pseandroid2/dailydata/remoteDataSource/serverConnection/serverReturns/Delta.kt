package com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverReturns

import java.time.LocalDateTime

// A Delta is a FetchRequest
data class Delta(val addedToServer: LocalDateTime, val user: String, val projectCommand: String, val project: Long, val requestedBy: String, val isAdmin: Boolean)
