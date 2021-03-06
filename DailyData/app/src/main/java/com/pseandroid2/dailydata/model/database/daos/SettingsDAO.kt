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
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.pseandroid2.dailydata.model.database.entities.GraphSettingEntity
import com.pseandroid2.dailydata.model.database.entities.ProjectSettingEntity
import com.pseandroid2.dailydata.model.settings.MapSettings
import com.pseandroid2.dailydata.model.settings.Settings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.TreeMap


/**
 * This class provides Methods and Queries to saves and change and remove Settings for Projects and Graphs to their tables.
 */
@Dao
abstract class SettingsDAO {
    /**
     * It provides the settings for a specified project.
     */
    fun getProjectSettings(projectId: Int): Flow<Settings> {
        @Suppress("Deprecation")
        return getProjectSettingEntities(projectId).map {
            val map: MutableMap<String, String> = TreeMap()
            for (setting: ProjectSettingEntity in it) {
                map[setting.settingKey] = setting.value
            }
            MapSettings(map)
        }
    }

    /**
     * It provides the settings for a specified graph in the specified project.
     */
    fun getGraphSettings(projectId: Int, graphId: Int): Flow<Settings> {
        @Suppress("Deprecation")
        return getGraphSettingEntities(projectId, graphId).map {
            val map: MutableMap<String, String> = TreeMap()
            for (setting: GraphSettingEntity in it) {
                map[setting.settingKey] = setting.value
            }
            MapSettings(map)
        }
    }


    suspend fun getSingleGraphSettings(projectId: Int, graphId: Int): Settings {
        @Suppress("Deprecation")
        val settings = getSingleGraphSettingsEntities(projectId, graphId)
        val map = mutableMapOf<String, String>()
        for (setting in settings) {
            map[setting.settingKey] = setting.value
        }
        return MapSettings(map)
    }


    /**
     * It changes a setting, which is specified by the key, to the value in a given project.
     */
    suspend fun changeProjectSetting(projectId: Int, key: String, value: String) {
        @Suppress("Deprecation")
        changeProjectSettingEntity(ProjectSettingEntity(projectId, key, value))
    }

    /**
     * It changes a setting, which is specified by the key, to the value in a given graph and project.
     */
    suspend fun changeGraphSetting(projectId: Int, graphId: Int, key: String, value: String) {
        @Suppress("Deprecation")
        changeGraphSettingEntity(GraphSettingEntity(projectId, graphId, key, value))
    }

    /**
     * It creates a new setting for a specified project and saves it to the table.
     */
    suspend fun createProjectSetting(projectId: Int, key: String, value: String) {
        @Suppress("Deprecation")
        insertProjectSettingEntity(ProjectSettingEntity(projectId, key, value))
    }

    /**
     * It creates a new setting for a specified project and graph and saves it to the table.
     */
    suspend fun createGraphSetting(projectId: Int, graphId: Int, key: String, value: String) {
        @Suppress("Deprecation")
        insertGraphSettingsEntity(GraphSettingEntity(projectId, graphId, key, value))
    }

    /**
     * It deletes a specified setting form a specified project.
     */
    @Query("DELETE FROM projectSetting WHERE projectId = :projectId AND settingKey = :key")
    abstract suspend fun deleteProjectSetting(projectId: Int, key: String)

    /**
     * It deletes all settings form a specified project.
     */
    @Query("DELETE FROM projectSetting WHERE projectId = :projectId")
    abstract suspend fun deleteAllProjectSettings(projectId: Int)

    /**
     * It deletes a specified setting form a specified project and graph.
     */
    @Query("DELETE FROM graphSetting WHERE projectId = :projectId AND graphId = :graphId AND settingKey = :key")
    abstract suspend fun deleteGraphSetting(projectId: Int, graphId: Int, key: String)

    /**
     * It deletes all settings form a specified project and graph.
     */
    @Query("DELETE FROM graphSetting WHERE projectId = :projectId AND graphId = :graphId")
    abstract suspend fun deleteAllGraphSettings(projectId: Int, graphId: Int)

    /*========================SHOULD ONLY BE CALLED FROM INSIDE THE MODEL=========================*/


    /**
     * It provides all ProjectSettingEntities form a specified project.
     */
    @Deprecated("Should only be used from inside the model. Use getProjectSettings() instead")
    @Query("SELECT * FROM projectSetting WHERE projectId = :projectId")
    abstract fun getProjectSettingEntities(projectId: Int): Flow<List<ProjectSettingEntity>>

    /**
     * It provides all GraphSettingsEntities form a specified project and graph as flow.
     */
    @Deprecated("Should only be used from inside the model. Use getGraphSettings() instead")

    @Query("SELECT * FROM graphSetting WHERE projectId = :projectId AND graphId = :graphId")
    abstract fun getGraphSettingEntities(
        projectId: Int,
        graphId: Int
    ): Flow<List<GraphSettingEntity>>


    /**
     * It provides all GraphSettingEntities from a specified project and graph.
     */
    @Deprecated("Should only be used from inside the model. Use getSingleGraphSettings() instead")
    @Query("SELECT * FROM graphSetting WHERE projectId = :projectId AND graphId = :graphId")
    abstract suspend fun getSingleGraphSettingsEntities(
        projectId: Int,
        graphId: Int
    ): List<GraphSettingEntity>

    /**
     * It changes a specified ProjectSettingEntity to the given Entity.
     */
    @Deprecated("Should only be used from inside the model. Use changeProjectSetting() instead")
    @Update
    abstract suspend fun changeProjectSettingEntity(setting: ProjectSettingEntity)

    /**
     * It changes a specified GraphSettingEntity to the given Entity.
     */
    @Deprecated("Should only be used from inside the model. Use changeGraphSetting() instead")
    @Update
    abstract suspend fun changeGraphSettingEntity(setting: GraphSettingEntity)

    /**
     * It adds a new ProjectSettingEntity to the table.
     */
    @Deprecated("Should only be used from inside the model. Use createProjectSetting() instead")
    @Insert
    abstract suspend fun insertProjectSettingEntity(setting: ProjectSettingEntity)

    /**
     * It adds a new Graph SettingEntity to the table.
     */
    @Deprecated("Should only be used from inside the model. Use createGraphSetting() instead")
    @Insert
    abstract suspend fun insertGraphSettingsEntity(setting: GraphSettingEntity)

    /**
     * It deletes the specified ProjectSettingEntity from the table.
     */
    @Deprecated("Should only be used from inside the model. Use deleteProjectSetting() instead")
    @Delete
    abstract suspend fun deleteProjectSettingEntity(setting: ProjectSettingEntity)

    /**
     * It deletes the specified GraphSettingEntity from the table.
     */
    @Deprecated("Should only be used from inside the model. Use deleteGraphSetting() instead")
    @Delete
    abstract suspend fun deleteGraphSettingEntity(setting: GraphSettingEntity)
}