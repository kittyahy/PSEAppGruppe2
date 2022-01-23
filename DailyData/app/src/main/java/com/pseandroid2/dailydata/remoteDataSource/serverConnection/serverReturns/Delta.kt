package com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverReturns

import java.time.LocalDateTime

/** A Delta is a ProjectCommand
 *
 * @param addedToServer: When this delta was added to the server
 * @param user: The UserID of the user who uploaded the delta
 * @param projectCommand: The projectCommand as JSON
 * @param project: The ProjectID of the project to which this delta belongs
 * @param requestedBy: The UserID of the user who requested this Delta
 * @param isAdmin: Was the creator of the delta an admin
 */
data class Delta(val addedToServer: LocalDateTime = java.time.LocalDateTime.parse("0001-01-01T00:00"),
                 val user: String = "",
                 val projectCommand: String = "",
                 val project: Long = -1,
                 val requestedBy: String = "",
                 val isAdmin: Boolean = false)
