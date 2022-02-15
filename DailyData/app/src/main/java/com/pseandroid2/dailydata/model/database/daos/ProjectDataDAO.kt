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
import com.pseandroid2.dailydata.model.database.entities.MissingSlotEntity
import com.pseandroid2.dailydata.model.database.entities.ProjectData
import com.pseandroid2.dailydata.model.database.entities.ProjectEntity
import com.pseandroid2.dailydata.model.database.entities.ProjectUserMap
import com.pseandroid2.dailydata.model.users.User
import kotlinx.coroutines.flow.Flow

/**
 * This class provides Methods and Queries to mange meta information for a project.
 * The meta data are information how the project looks like and which users participate in the project.
 * It also manges where Deltas may be missing.
 */
@Dao
abstract class ProjectDataDAO {
    /*===================================PROJECT RELATED STUFF====================================*/
    /**
     * It returns all meta information (id, name, description, wallpaper, onlineId and color) for every project.
     */
    @Query("SELECT id, name, description, wallpaper, onlineId, color FROM project")
    abstract fun getAllProjectData(): Flow<List<ProjectData>>

    /**
     * It returns all meta information (id, name, description, wallpaper, onlineId and color) for all specified projects.
     */
    @Query("SELECT id, name, description, wallpaper, onlineId, color FROM project WHERE id IN (:ids)")
    abstract fun getProjectDataByIds(vararg ids: Int): Flow<List<ProjectData>>

    /**
     * It returns all meta information (id, name, description, wallpaper, onlineId and color) a specified project.
     */
    @Query("SELECT id, name, description, wallpaper, onlineId, color FROM project WHERE id = :id")
    abstract fun getProjectData(id: Int): Flow<ProjectData?>

    @Query("SELECT layout FROM project WHERE id = :id")
    abstract suspend fun getCurrentLayout(id: Int): String

    @Query("SELECT isOnline FROM project WHERE id = :id")
    abstract fun isOnline(id: Int): Flow<Boolean>

    @Query("SELECT onlineId FROM project WHERE id = :id")
    abstract fun getOnlineId(id: Int): Long

    @Query("SELECT id FROM project WHERE onlineId = :onlineId LIMIT 1")
    abstract fun getIdForOnlineId(onlineId: Long): Int

    @Query("SELECT EXISTS (SELECT * FROM project WHERE id = :id)")
    abstract fun projectExists(id: Int): Boolean


    /**
     * It changes the name of a specified project if it is available.
     */
    @Query("UPDATE project SET name = :name WHERE id = :id")
    abstract suspend fun setName(id: Int, name: String)

    /**
     * It changes the description of a specified project if it is available.
     */
    @Query("UPDATE project SET description = :description WHERE id = :id")
    abstract suspend fun setDescription(id: Int, description: String)

    /**
     * It changes the wallpaper of a specified project if it is available.
     */
    @Query("UPDATE project SET wallpaper = :wallpaper WHERE id = :id")
    abstract suspend fun setWallpaper(id: Int, wallpaper: String)

    /**
     * It changes the onlineID of a specified project if it is available.
     */
    @Query("UPDATE project SET onlineId = :onlineID WHERE id = :id")
    abstract suspend fun setOnlineID(id: Int, onlineID: Long)

    /**
     * It changes the color of a specified project if it is available.
     */
    @Query("UPDATE project SET color = :color WHERE id = :id")
    abstract suspend fun setColor(id: Int, color: Int)

    /*=====================================USER RELATED STUFF=====================================*/
    /**
     * It returns all users from all specified projects.
     */
    @Query("SELECT * FROM user WHERE projectId IN (:ids)")
    abstract fun getUsersByIds(vararg ids: Int): Flow<List<ProjectUserMap>>

    /**
     * It returns the admin from all specified projects.
     */
    @Query("SELECT id AS projectId, admin AS user FROM project WHERE id IN (:ids)")
    abstract fun getAdminByIds(vararg ids: Int): Flow<List<ProjectUserMap>>

    /**
     * Gets the current admin of the specified project from the database
     */
    @Query("SELECT id AS projectId, admin AS user FROM project WHERE id = :id")
    abstract suspend fun getCurrentAdmin(id: Int): ProjectUserMap

    /**
     * It adds a specified user to the given project.
     */
    suspend fun addUser(projectId: Int, user: User) {
        insertProjectUserMap(ProjectUserMap(projectId, user))
    }

    /**
     * It removes all specified users from a given project.
     */
    suspend fun removeUsers(projectId: Int, vararg users: User) {
        for (user: User in users) {
            deleteProjectUserMap(ProjectUserMap(projectId, user))
        }
    }

    /**
     * It changes the admin to the specified user in the given project.
     */
    @Query("UPDATE project SET admin = :admin WHERE id = :projectId")
    abstract suspend fun changeAdmin(projectId: Int, admin: User)

    /*=====================================DELTA RELATED STUFF====================================*/
    /**
     * It adds the given missingSlotEntity.
     */
    @Insert
    abstract suspend fun insertMissingSlot(slot: MissingSlotEntity)

    /**
     * It deletes the given missingSlotEntity.
     */
    @Delete
    abstract suspend fun deleteMissingSlot(slot: MissingSlotEntity)

    /**
     * It returns all missingSlotEntities from a specified project.
     */
    @Query("SELECT * FROM missingSlot WHERE projectId = :projectId")
    abstract fun getMissingSlots(projectId: Int): List<MissingSlotEntity>

    /*========================SHOULD ONLY BE CALLED FROM INSIDE THE MODEL=========================*/
    /**
     * It saves the given projectEntity to the table.
     */
    @Deprecated("This method should only be used from within the model, use ProjectCDManager.insertProject instead")
    @Insert
    abstract suspend fun insertProjectEntity(entity: ProjectEntity)

    /**
     * It deletes  the given projectEntity from the table.
     */
    @Deprecated("This method should only be used from within the model, use ProjectCDManager.deleteProject instead")
    @Delete
    abstract suspend fun deleteProjectEntity(entity: ProjectEntity)

    /**
     * It deletes a projectEntity by their given id.
     */
    @Deprecated("This method should only be used from within the model, use GraphCDManager.deleteProject instead")
    @Query("DELETE FROM project WHERE id = :id")
    abstract suspend fun deleteProjectEntityById(id: Int)

    /**
     * It saves the given projectUserMap to the table.
     */
    @Deprecated("This method should only be used from within the model, use addUser instead")
    @Insert
    abstract suspend fun insertProjectUserMap(userMap: ProjectUserMap)

    /**
     * It deletes the given projectUsermap from the table.
     */
    @Deprecated("This method should only be used from within the model, use removeUsers instead")
    @Delete
    abstract suspend fun deleteProjectUserMap(userMap: ProjectUserMap)

    /**
     * It deletes all users from the specified project.
     */
    @Deprecated("This method should only be used from within the model")
    @Query("DELETE FROM user WHERE projectId = :projectId")
    abstract suspend fun deleteAllUsers(projectId: Int)
}