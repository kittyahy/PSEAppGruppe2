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
 * A dataclass which will be received from the server. It stores the details of a single template
 *
 * @param id: The id of the template
 * @param detail: The template as a JSON
 * @param isProjectTemplate: If true, than a projectTemplate is described. If false, than a graphTemplate is described
 */
data class TemplateDetail(
    val id: Int = 0,
    val detail: String = "",
    val isProjectTemplate: Boolean = false
)
