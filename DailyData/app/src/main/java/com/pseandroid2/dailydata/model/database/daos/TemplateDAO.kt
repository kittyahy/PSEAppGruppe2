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
import com.pseandroid2.dailydata.model.database.entities.GraphTemplateEntity
import com.pseandroid2.dailydata.model.database.entities.ProjectTemplateEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class TemplateDAO {

    @Query("SELECT * FROM projectTemplate")
    abstract suspend fun getAllProjectTemplateData(): Flow<List<ProjectTemplateEntity>>

    @Query("SELECT * FROM projectTemplate WHERE id in (:ids)")
    abstract suspend fun getProjectTemplateData(vararg ids: Int): Flow<List<ProjectTemplateEntity>>

    @Query("SELECT * FROM projectTemplate WHERE createdBy = :creator")
    abstract suspend fun getProjectTemplateByCreator(creator: String): Flow<List<ProjectTemplateEntity>>

    @Query("UPDATE projectTemplate SET name = :name WHERE id = :id")
    abstract suspend fun setProjectTemplateName(id: Int, name: String)

    @Query("UPDATE projectTemplate SET description = :desc WHERE id = :id")
    abstract suspend fun setProjectTemplateDescription(id: Int, desc: String)

    @Query("UPDATE projectTemplate SET wallpaper = :wallpaper WHERE id = :id")
    abstract suspend fun setProjectTemplateWallpaper(id: Int, wallpaper: String)

    @Query("SELECT * FROM graphTemplate")
    abstract suspend fun getAllGraphTemplateData(): Flow<List<GraphTemplateEntity>>

    @Query("SELECT * FROM graphTemplate WHERE createdBy = :creator")
    abstract suspend fun getGraphTemplateByCreator(creator: String): Flow<List<GraphTemplateEntity>>

    @Query("UPDATE graphTemplate SET name = :name WHERE id = :id")
    abstract suspend fun setGraphTemplateName(id: Int, name: String)

    @Query("UPDATE graphTemplate SET description = :desc WHERE id = :id")
    abstract suspend fun setGraphTemplateDescription(id: Int, desc: String)

    /*========================SHOULD ONLY BE CALLED FROM INSIDE THE MODEL=========================*/
    @Insert
    abstract suspend fun insertProjectTemplate(project: ProjectTemplateEntity)

    @Delete
    abstract suspend fun deleteProjectTemplate(project: ProjectTemplateEntity)

    @Query("DELETE FROM projectTemplate WHERE id = :id")
    abstract suspend fun deleteProjectTemplateById(id: Int)

    @Insert
    abstract suspend fun insertGraphTemplate(graph: GraphTemplateEntity)

    @Delete
    abstract suspend fun deleteGraphTemplate(graph: GraphTemplateEntity)
}