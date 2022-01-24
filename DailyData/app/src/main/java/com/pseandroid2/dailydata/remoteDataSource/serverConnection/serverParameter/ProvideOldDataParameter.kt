package com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverParameter

import java.time.LocalDateTime

/**
 * @param token: The authentication token
 * @param command: The delta as JSON
 * @param forUser: The UserID of the user that needs the delta
 * @param initialAdded: The time when this delta was uploaded
 * @param initialAddedBy: The ID of the user who initially uploaded this command to the server
 * @param wasAdmin: Was the user who initially uploaded this project command to the server an admin
 */
data class ProvideOldDataParameter(val command: String, val forUser: String, val initialAdded: LocalDateTime, val initialAddedBy: String, val wasAdmin: Boolean)
