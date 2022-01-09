package com.pseandroid2.dailydata.model.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.pseandroid2.dailydata.model.User
import com.pseandroid2.dailydata.model.database.entities.MissingSlotEntity
import com.pseandroid2.dailydata.model.database.entities.ProjectData
import com.pseandroid2.dailydata.model.database.entities.ProjectEntity
import com.pseandroid2.dailydata.model.database.entities.ProjectUserMap
import kotlinx.coroutines.flow.Flow

@Dao
abstract class ProjectDataDAO {
    /*===================================PROJECT RELATED STUFF====================================*/
    @Query("SELECT id, name, description, wallpaper, onlineId FROM project")
    abstract fun getAllProjectData(): Flow<List<ProjectData>>

    @Query("SELECT id, name, description, wallpaper, onlineId FROM project WHERE id IN (:ids)")
    abstract fun getProjectDataByIds(vararg ids: Int): Flow<List<ProjectData>>

    @Query("SELECT id, name, description, wallpaper, onlineId FROM project WHERE id = :id")
    abstract fun getProjectData(id: Int): Flow<ProjectData>

    @Query("UPDATE project SET name = :name WHERE id = :id")
    abstract fun setName(id: Int, name: String)

    @Query("UPDATE project SET description = :description WHERE id = :id")
    abstract fun setDescription(id: Int, description: String)

    @Query("UPDATE project SET wallpaper = :wallpaper WHERE id = :id")
    abstract fun setWallpaper(id: Int, wallpaper: String)

    @Query("UPDATE project SET onlineId = :onlineID WHERE id = :id")
    abstract fun setOnlineID(id: Int, onlineID: Long)

    /*=====================================USER RELATED STUFF=====================================*/
    @Query("SELECT * FROM user WHERE projectId IN (:ids)")
    abstract fun getUsersByIds(vararg ids: Int): Flow<List<ProjectUserMap>>

    @Query("SELECT id AS projectId, admin AS user FROM project WHERE id IN (:ids)")
    abstract fun getAdminByIds(vararg ids: Int): Flow<List<ProjectUserMap>>

    fun addUser(projectId: Int, user: User) {
        insertProjectUserMap(ProjectUserMap(projectId, user))
    }

    fun removeUsers(projectId: Int, vararg users: User) {
        for (user: User in users) {
            deleteProjectUserMap(ProjectUserMap(projectId, user))
        }
    }

    @Query("UPDATE project SET admin = :admin WHERE id = :projectId")
    abstract fun changeAdmin(projectId: Int, admin: User)

    /*=====================================DELTA RELATED STUFF====================================*/
    @Insert
    abstract fun insertMissingSlot(slot: MissingSlotEntity)

    @Delete
    abstract fun deleteMissingSlot(slot: MissingSlotEntity)

    @Query("SELECT * FROM missingSlot WHERE projectId = :projectId")
    abstract fun getMissingSlots(projectId: Int): List<MissingSlotEntity>

    /*========================SHOULD ONLY BE CALLED FROM INSIDE THE MODEL=========================*/
    @Insert
    abstract suspend fun insertProjectEntity(entity: ProjectEntity)

    @Delete
    abstract fun deleteProjectEntity(entity: ProjectEntity)

    @Insert
    abstract fun insertProjectUserMap(userMap: ProjectUserMap)

    @Delete
    abstract fun deleteProjectUserMap(userMap: ProjectUserMap)
}