package com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverParameter

/**
 * @param token: The authentication token
 * @param userToRemove: The UserID of the user that should be removed from the project
 * @param projectID: The ID of the Project from which the user should be removed
 */
data class RemoveUserParameter(val userToRemove: String, val projectID: Long)
