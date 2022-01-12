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
    abstract fun getProjectData(id: Int): Flow<ProjectData?>

    @Query("UPDATE project SET name = :name WHERE id = :id")
    abstract suspend fun setName(id: Int, name: String)

    @Query("UPDATE project SET description = :description WHERE id = :id")
    abstract suspend fun setDescription(id: Int, description: String)

    @Query("UPDATE project SET wallpaper = :wallpaper WHERE id = :id")
    abstract suspend fun setWallpaper(id: Int, wallpaper: String)

    @Query("UPDATE project SET onlineId = :onlineID WHERE id = :id")
    abstract suspend fun setOnlineID(id: Int, onlineID: Long)

    /*=====================================USER RELATED STUFF=====================================*/
    @Query("SELECT * FROM user WHERE projectId IN (:ids)")
    abstract fun getUsersByIds(vararg ids: Int): Flow<List<ProjectUserMap>>

    @Query("SELECT id AS projectId, admin AS user FROM project WHERE id IN (:ids)")
    abstract fun getAdminByIds(vararg ids: Int): Flow<List<ProjectUserMap>>

    suspend fun addUser(projectId: Int, user: User) {
        insertProjectUserMap(ProjectUserMap(projectId, user))
    }

    suspend fun removeUsers(projectId: Int, vararg users: User) {
        for (user: User in users) {
            deleteProjectUserMap(ProjectUserMap(projectId, user))
        }
    }

    @Query("UPDATE project SET admin = :admin WHERE id = :projectId")
    abstract suspend fun changeAdmin(projectId: Int, admin: User)

    /*=====================================DELTA RELATED STUFF====================================*/
    @Insert
    abstract suspend fun insertMissingSlot(slot: MissingSlotEntity)

    @Delete
    abstract suspend fun deleteMissingSlot(slot: MissingSlotEntity)

    @Query("SELECT * FROM missingSlot WHERE projectId = :projectId")
    abstract fun getMissingSlots(projectId: Int): List<MissingSlotEntity>

    /*========================SHOULD ONLY BE CALLED FROM INSIDE THE MODEL=========================*/
    @Insert
    abstract suspend fun insertProjectEntity(entity: ProjectEntity)

    @Delete
    abstract suspend fun deleteProjectEntity(entity: ProjectEntity)

    @Insert
    abstract suspend fun insertProjectUserMap(userMap: ProjectUserMap)

    @Delete
    abstract suspend fun deleteProjectUserMap(userMap: ProjectUserMap)
}