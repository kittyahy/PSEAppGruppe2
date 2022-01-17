package com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverParameter

import java.util.Date

data class ProvideOldDataParameter(val token: String, val command: String, val forUser: String, val initialAdded: Date, val initialAddedBy: String, val wasAdmin: Boolean)
