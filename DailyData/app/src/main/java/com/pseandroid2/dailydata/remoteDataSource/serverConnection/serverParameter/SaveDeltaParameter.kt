package com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverParameter

/**
 * @param token: The authentication token
 * @param command: The project command that should be uploaded
 */
data class SaveDeltaParameter(val token: String, val command: String)
