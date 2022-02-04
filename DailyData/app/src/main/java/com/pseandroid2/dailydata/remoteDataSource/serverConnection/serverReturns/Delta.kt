/*

    DailyData is an android app to easily create diagrams from data one has collected
    Copyright (C) 2022  Antonia Heiming, Anton Kadelbach, Arne Kuchenbecker, Merlin Opp, Robin Amman

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.

*/
package com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverReturns

import java.time.LocalDateTime

/**
 * A dataclass which will be received from the server. It stores the details of a single project command
 * (A Delta is a ProjectCommand)
 *
 * @param addedToServerS: The time, when the Delta initially was added to the server. (so the user can recreate,
 *                          when the change was made)
 * @param user:           The UserID of the user who uploaded the delta
 * @param projectCommand: The projectCommand as JSON
 * @param project:        The ProjectID of the project to which this delta belongs
 * @param admin:          Was the creator of the delta an admin
 * @param requestedBy:    The participant, who needs this Delta
 */
data class Delta(
    val addedToServerS: LocalDateTime = LocalDateTime.parse("0001-01-01T01:01:01.123456"),
    val user: String = "",
    val projectCommand: String = "",
    val project: Long = -1,
    val requestedBy: String = "",
    val admin: Boolean = false
)
