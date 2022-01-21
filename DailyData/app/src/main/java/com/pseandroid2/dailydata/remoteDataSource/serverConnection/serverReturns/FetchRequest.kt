package com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverReturns

data class FetchRequest(val id: Int = -1,
                        val user: String = "",
                        val project: Long = -1,
                        val requestInfo: String = "")
