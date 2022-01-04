package com.pseandroid2.dailydata.model.database.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Insert
import androidx.room.Delete
import androidx.room.Update
import com.pseandroid2.dailydata.model.database.entities.GraphSettingEntity
import com.pseandroid2.dailydata.model.database.entities.ProjectSettingEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.TreeMap

@Dao
abstract class SettingsDAO {
    fun getProjectSettings(projectId: Int): Flow<Map<String, String>> {
        return getProjectSettingEntities(projectId).map {
            val map: MutableMap<String, String> = TreeMap()
            for (setting: ProjectSettingEntity in it) {
                map[setting.key] = setting.value
            }
            map.toMap()
        }
    }

    fun getGraphSettings(projectId: Int, graphId: Int): Flow<Map<String, String>> {
        return getGraphSettingEntities(projectId, graphId).map {
            val map: MutableMap<String, String> = TreeMap()
            for (setting: GraphSettingEntity in it) {
                map[setting.key] = setting.value
            }
            map.toMap()
        }
    }

    fun changeProjectSetting(projectId: Int, key: String, value: String) {
        changeProjectSettingEntity(ProjectSettingEntity(projectId, key, value))
    }

    fun changeGraphSetting(projectId: Int, graphId: Int, key: String, value: String) {
        changeGraphSettingEntity(GraphSettingEntity(projectId, graphId, key, value))
    }

    fun createProjectSetting(projectId: Int, key: String, value: String) {
        insertProjectSettingEntity(ProjectSettingEntity(projectId, key, value))
    }

    fun createGraphSetting(projectId: Int, graphId: Int, key: String, value: String) {
        insertGraphSettingsEntity(GraphSettingEntity(projectId, graphId, key, value))
    }

    fun deleteProjectSetting(projectId: Int, key: String) {
        deleteProjectSettingEntity(ProjectSettingEntity(projectId, key, ""))
    }

    fun deleteGraphSetting(projectId: Int, graphId: Int, key: String) {
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
    abstract fun changeProjectSettingEntity(setting: ProjectSettingEntity)

    @Update
    abstract fun changeGraphSettingEntity(setting: GraphSettingEntity)

    @Insert
    abstract fun insertProjectSettingEntity(setting: ProjectSettingEntity)

    @Insert
    abstract fun insertGraphSettingsEntity(setting: GraphSettingEntity)

    @Delete
    abstract fun deleteProjectSettingEntity(setting: ProjectSettingEntity)

    @Delete
    abstract fun deleteGraphSettingEntity(setting: GraphSettingEntity)
}