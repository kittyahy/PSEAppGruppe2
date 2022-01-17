package com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverParameter

import java.time.LocalDateTime

data class ProvideOldDataParameter(val token: String, val command: String, val forUser: String, val initialAdded: LocalDateTime, val initialAddedBy: String, val wasAdmin: Boolean)
