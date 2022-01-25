package com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverReturns

/**
 * @param id: The id of the fetchRequest
 * @param user: The UserID of the User who created the fetchRequest
 * @param project: The ProjectID of the project to which the fetchRequest belongs
 * @param requestInfo: The FetchRequest as JSON
 */
data class FetchRequest(val id: Int = -1,
                        val user: String = "",
                        val project: Long = -1,
                        val requestInfo: String = "")
