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

/**
 * A dataclass which will be received from the server. It stores a single fetch request
 *
 * @param id: The id of the fetchRequest
 * @param user: The UserID of the User who created the fetchRequest
 * @param project: The ProjectID of the project to which the fetchRequest belongs
 * @param requestInfo: The FetchRequest as JSON
 */
data class FetchRequest(val id: Int = -1,
                        val user: String = "",
                        val project: Long = -1,
                        val requestInfo: String = "")
