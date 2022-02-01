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
import com.pseandroid2.dailydata.model.database.entities.GraphTemplateData
import com.pseandroid2.dailydata.model.database.entities.GraphTemplateEntity
import com.pseandroid2.dailydata.model.database.entities.ProjectTemplateData
import com.pseandroid2.dailydata.model.database.entities.ProjectTemplateEntity
import com.pseandroid2.dailydata.model.table.TableLayout
import com.pseandroid2.dailydata.model.users.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Dao
abstract class TemplateDAO {
    fun getAllProjectTemplateData(): Flow<List<ProjectTemplateData>> {
        @Suppress("Deprecation")
        return mapToProjectTemplateData(getAllProjectTemplateEntities())
    }

    fun getProjectTemplateData(vararg ids: Int): Flow<List<ProjectTemplateData>> {
        @Suppress("Deprecation")
        return mapToProjectTemplateData(getProjectTemplateEntities(*ids))
    }

    fun getProjectTemplateDataByCreator(creator: User): Flow<List<ProjectTemplateData>> {
        @Suppress("Deprecation")
        return mapToProjectTemplateData(getProjectTemplateEntityByCreator(creator))
    }

    fun getAllGraphTemplateData(): Flow<List<GraphTemplateData>> {
        @Suppress("Deprecation")
        return mapToGraphTemplateData(getAllGraphTemplateEntities())
    }

    fun getGraphTemplateData(vararg ids: Int): Flow<List<GraphTemplateData>> {
        @Suppress("Deprecation")
        return mapToGraphTemplateData(getGraphTemplateEntities(*ids))
    }

    fun getGraphTemplateDataByCreator(creator: User): Flow<List<GraphTemplateData>> {
        @Suppress("Deprecation")
        return mapToGraphTemplateData(getGraphTemplateEntitiesByCreator(creator))
    }

    @Query("UPDATE projectTemplate SET name = :name WHERE id = :id")
    abstract suspend fun setProjectTemplateName(id: Int, name: String)

    @Query("UPDATE projectTemplate SET description = :desc WHERE id = :id")
    abstract suspend fun setProjectTemplateDescription(id: Int, desc: String)

    @Query("UPDATE projectTemplate SET wallpaper = :wallpaper WHERE id = :id")
    abstract suspend fun setProjectTemplateWallpaper(id: Int, wallpaper: String)

    @Query("UPDATE graphTemplate SET name = :name WHERE id = :id")
    abstract suspend fun setGraphTemplateName(id: Int, name: String)

    @Query("UPDATE graphTemplate SET description = :desc WHERE id = :id")
    abstract suspend fun setGraphTemplateDescription(id: Int, desc: String)

    /*========================SHOULD ONLY BE CALLED FROM INSIDE THE MODEL=========================*/
    @Deprecated("Should only be used from inside the Model. Use ProjectCDManager.insertProjectTemplate instead")
    @Insert
    abstract suspend fun insertProjectTemplate(project: ProjectTemplateEntity)

    @Deprecated("Should only be used from inside the Model. Use ProjectCDManager.deleteProjectTemplate instead")
    @Delete
    abstract suspend fun deleteProjectTemplate(project: ProjectTemplateEntity)

    @Deprecated("Should only be used from inside the Model. Use ProjectCDManager.deleteProjectTemplate instead")
    @Query("DELETE FROM projectTemplate WHERE id = :id")
    abstract suspend fun deleteProjectTemplateById(id: Int)

    @Deprecated("Should only be used from inside the Model. Use GraphCDManager.insertGraphTemplate instead")
    @Insert
    abstract suspend fun insertGraphTemplate(graph: GraphTemplateEntity)

    @Deprecated("Should only be used from inside the Model. Use GraphCDManager.deleteGraphTemplate instead")
    @Delete
    abstract suspend fun deleteGraphTemplate(graph: GraphTemplateEntity)

    @Deprecated("Should only be used from inside the Model. Use GraphCDManager.deleteGraphTemplate instead")
    @Query("DELETE FROM graphTemplate WHERE id = :id")
    abstract suspend fun deleteGraphTemplateById(id: Int)

    @Deprecated(
        "Should only be used from inside the model. Use getAllProjectTemplateData() instead",
        ReplaceWith("getAllProjectTemplateData()")
    )
    @Query("SELECT * FROM projectTemplate")
    abstract fun getAllProjectTemplateEntities(): Flow<List<ProjectTemplateEntity>>

    @Deprecated(
        "Should only be used from inside the model. Use getProjectTemplateData() instead",
        ReplaceWith("getProjectTemplateData()")
    )
    @Query("SELECT * FROM projectTemplate WHERE id in (:ids)")
    abstract fun getProjectTemplateEntities(vararg ids: Int): Flow<List<ProjectTemplateEntity>>

    @Deprecated(
        "Should only be used from inside the model. Use getProjectTemplateData() instead",
        ReplaceWith("getProjectTemplateDataByCreator()")
    )
    @Query("SELECT * FROM projectTemplate WHERE createdBy = :creator")
    abstract fun getProjectTemplateEntityByCreator(creator: User)
            : Flow<List<ProjectTemplateEntity>>

    @Deprecated(
        "Should only be used from inside the model. Use getProjectTemplateData() instead",
        ReplaceWith("getAllGraphTemplateData()")
    )
    @Query("SELECT * FROM graphTemplate")
    abstract fun getAllGraphTemplateEntities(): Flow<List<GraphTemplateEntity>>

    @Deprecated(
        "Should only be used from inside the model. Use getProjectTemplateData() instead",
        ReplaceWith("getGraphTemplateData()")
    )
    @Query("SELECT * FROM graphTemplate WHERE id IN (:ids)")
    abstract fun getGraphTemplateEntities(vararg ids: Int): Flow<List<GraphTemplateEntity>>

    @Deprecated(
        "Should only be used from inside the model. Use getProjectTemplateData() instead",
        ReplaceWith("getGraphTemplateDataByCreator()")
    )
    @Query("SELECT * FROM graphTemplate WHERE createdBy = :creator")
    abstract fun getGraphTemplateEntitiesByCreator(creator: User)
            : Flow<List<GraphTemplateEntity>>

    private fun mapToProjectTemplateData(flow: Flow<List<ProjectTemplateEntity>>)
            : Flow<List<ProjectTemplateData>> {
        return flow.map { list ->
            val dataList = mutableListOf<ProjectTemplateData>()
            for (template in list) {
                val skeleton = template.skeleton
                dataList.add(
                    ProjectTemplateData(
                        skeleton.id,
                        skeleton.name,
                        skeleton.description,
                        skeleton.onlineId,
                        skeleton.wallpaper,
                        skeleton.color,
                        template.createdBy,
                        TableLayout.fromJSON(skeleton.layout)
                    )
                )
            }
            dataList.toList()
        }
    }

    private fun mapToGraphTemplateData(flow: Flow<List<GraphTemplateEntity>>)
            : Flow<List<GraphTemplateData>> {
        return flow.map { list ->
            val dataList = mutableListOf<GraphTemplateData>()
            for (template in list) {
                dataList.add(
                    GraphTemplateData(
                        template.id,
                        template.name,
                        template.description,
                        template.type,
                        template.createdBy
                    )
                )
            }
            dataList.toList()
        }
    }
}
