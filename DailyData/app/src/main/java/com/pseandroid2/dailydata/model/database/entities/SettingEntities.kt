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

package com.pseandroid2.dailydata.model.database.entities

import androidx.room.Entity

/**
 * This Entity represents a projectSetting in their Database. A setting is specified by their project and a key for the setting.
 */
@Entity(tableName = "projectSetting", primaryKeys = ["projectId", "settingKey"])
data class ProjectSettingEntity(
    val projectId: Int,
    val settingKey: String,
    val value: String
)

/**
 * This Entity represents a graphSetting in their Database. A setting is specified by their project and a key for the setting.
 */
@Entity(tableName = "graphSetting", primaryKeys = ["projectId", "graphId", "settingKey"])
data class GraphSettingEntity(
    val projectId: Int,
    val graphId: Int,
    val settingKey: String,
    val value: String
)