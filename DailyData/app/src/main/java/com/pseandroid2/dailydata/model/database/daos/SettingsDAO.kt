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

package com.pseandroid2.dailydata.model.database.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Insert
import androidx.room.Delete
import androidx.room.Update
import com.pseandroid2.dailydata.model.Settings
import com.pseandroid2.dailydata.model.MapSettings
import com.pseandroid2.dailydata.model.database.entities.GraphSettingEntity
import com.pseandroid2.dailydata.model.database.entities.ProjectSettingEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.TreeMap

@Dao
abstract class SettingsDAO {
    suspend fun getProjectSettings(projectId: Int): Flow<Settings> {
        return getProjectSettingEntities(projectId).map {
            val map: MutableMap<String, String> = TreeMap()
            for (setting: ProjectSettingEntity in it) {
                map[setting.key] = setting.value
            }
            MapSettings(map)
        }
    }

    suspend fun getGraphSettings(projectId: Int, graphId: Int): Flow<Settings> {
        return getGraphSettingEntities(projectId, graphId).map {
            val map: MutableMap<String, String> = TreeMap()
            for (setting: GraphSettingEntity in it) {
                map[setting.key] = setting.value
            }
            MapSettings(map)
        }
    }

    suspend fun changeProjectSetting(projectId: Int, key: String, value: String) {
        changeProjectSettingEntity(ProjectSettingEntity(projectId, key, value))
    }

    suspend fun changeGraphSetting(projectId: Int, graphId: Int, key: String, value: String) {
        changeGraphSettingEntity(GraphSettingEntity(projectId, graphId, key, value))
    }

    suspend fun createProjectSetting(projectId: Int, key: String, value: String) {
        insertProjectSettingEntity(ProjectSettingEntity(projectId, key, value))
    }

    suspend fun createGraphSetting(projectId: Int, graphId: Int, key: String, value: String) {
        insertGraphSettingsEntity(GraphSettingEntity(projectId, graphId, key, value))
    }

    suspend fun deleteProjectSetting(projectId: Int, key: String) {
        deleteProjectSettingEntity(ProjectSettingEntity(projectId, key, ""))
    }

    suspend fun deleteGraphSetting(projectId: Int, graphId: Int, key: String) {
        deleteGraphSettingEntity(GraphSettingEntity(projectId, graphId, key, ""))
    }

    /*========================SHOULD ONLY BE CALLED FROM INSIDE THE MODEL=========================*/
    @Query("SELECT * FROM projectSetting WHERE projectId = :projectId")
    abstract fun getProjectSettingEntities(projectId: Int): Flow<List<ProjectSettingEntity>>

    @Query("SELECT * FROM graphSetting WHERE projectId = :projectId AND graphId = :graphId")
    abstract fun getGraphSettingEntities(
        projectId: Int,
        graphId: Int
    ): Flow<List<GraphSettingEntity>>

    @Update
    abstract suspend fun changeProjectSettingEntity(setting: ProjectSettingEntity)

    @Update
    abstract suspend fun changeGraphSettingEntity(setting: GraphSettingEntity)

    @Insert
    abstract suspend fun insertProjectSettingEntity(setting: ProjectSettingEntity)

    @Insert
    abstract suspend fun insertGraphSettingsEntity(setting: GraphSettingEntity)

    @Delete
    abstract suspend fun deleteProjectSettingEntity(setting: ProjectSettingEntity)

    @Delete
    abstract suspend fun deleteGraphSettingEntity(setting: GraphSettingEntity)
}