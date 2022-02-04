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

/**
 * This class provides Methods and Queries to saves and change and remove templates, graph templates as well as project templatesm for their tables.
 */
@Dao
abstract class TemplateDAO {
    /**
     * It provides ProjectTemplateData for all ProjectTemplates
     */
    fun getAllProjectTemplateData(): Flow<List<ProjectTemplateData>> {
        @Suppress("Deprecation")
        return mapToProjectTemplateData(getAllProjectTemplateEntities())
    }

    /**
     * It provides ProjectTemplateData for specified ProjectTemplates
     */
    fun getProjectTemplateData(vararg ids: Int): Flow<List<ProjectTemplateData>> {
        @Suppress("Deprecation")
        return mapToProjectTemplateData(getProjectTemplateEntities(*ids))
    }

    /**
     * It provides ProjectTemplateData specified by the creator of the ProjectTemplate
     */
    fun getProjectTemplateDataByCreator(creator: User): Flow<List<ProjectTemplateData>> {
        @Suppress("Deprecation")
        return mapToProjectTemplateData(getProjectTemplateEntityByCreator(creator))
    }

    /**
     * It provides GraphTemplateData for all GraphTemplates
     */
    fun getAllGraphTemplateData(): Flow<List<GraphTemplateData>> {
        @Suppress("Deprecation")
        return mapToGraphTemplateData(getAllGraphTemplateEntities())
    }

    /**
     * It provides GraphTemplateData for specified GraphTemplates
     */
    fun getGraphTemplateData(vararg ids: Int): Flow<List<GraphTemplateData>> {
        @Suppress("Deprecation")
        return mapToGraphTemplateData(getGraphTemplateEntities(*ids))
    }


    /**
     * It provides GraphTemplateData specified by the creator of the GraphTemplate
     */
    fun getGraphTemplatesForProjectTemplate(id: Int): Flow<List<GraphTemplateData>> {
        @Suppress("Deprecation")
        return mapToGraphTemplateData(getGraphTemplateEntitiesForTemplate(id))
    }


    fun getGraphTemplateDataByCreator(creator: User): Flow<List<GraphTemplateData>> {
        @Suppress("Deprecation")
        return mapToGraphTemplateData(getGraphTemplateEntitiesByCreator(creator))
    }

    /**
     * It changes the name of a specified ProjectTemplate
     */
    @Query("UPDATE projectTemplate SET name = :name WHERE id = :id")
    abstract suspend fun setProjectTemplateName(id: Int, name: String)

    /**
     * It changes the description of a specified ProjectTemplate
     */
    @Query("UPDATE projectTemplate SET description = :desc WHERE id = :id")
    abstract suspend fun setProjectTemplateDescription(id: Int, desc: String)

    /**
     * It changes the wallpaper of a specified ProjectTemplate
     */
    @Query("UPDATE projectTemplate SET wallpaper = :wallpaper WHERE id = :id")
    abstract suspend fun setProjectTemplateWallpaper(id: Int, wallpaper: String)

    /**
     * It changes the name of a specified GraphTemplate
     */
    @Query("UPDATE graphTemplate SET name = :name WHERE id = :id")
    abstract suspend fun setGraphTemplateName(id: Int, name: String)

    /**
     * It changes the description of a specified GraphTemplate
     */
    @Query("UPDATE graphTemplate SET description = :desc WHERE id = :id")
    abstract suspend fun setGraphTemplateDescription(id: Int, desc: String)

    /*========================SHOULD ONLY BE CALLED FROM INSIDE THE MODEL=========================*/
    /**
     * It inserts the given ProjectTemplateEntity to the table.
     */
    @Deprecated("Should only be used from inside the Model. Use ProjectCDManager.insertProjectTemplate instead")
    @Insert
    abstract suspend fun insertProjectTemplate(project: ProjectTemplateEntity)

    /**
     * It deletes the given ProjectTemplateEntity from the table.
     */
    @Deprecated("Should only be used from inside the Model. Use ProjectCDManager.deleteProjectTemplate instead")
    @Delete
    abstract suspend fun deleteProjectTemplate(project: ProjectTemplateEntity)

    /**
     * It deletes the ProjectTemplateEntity with the given id from the table.
     */
    @Deprecated("Should only be used from inside the Model. Use ProjectCDManager.deleteProjectTemplate instead")
    @Query("DELETE FROM projectTemplate WHERE id = :id")
    abstract suspend fun deleteProjectTemplateById(id: Int)

    /**
     * It inserts the given GraphTemplateEntity to the table.
     */
    @Deprecated("Should only be used from inside the Model. Use GraphCDManager.insertGraphTemplate instead")
    @Insert
    abstract suspend fun insertGraphTemplate(graph: GraphTemplateEntity)

    /**
     * It deletes the given GraphTemplateEntity from the table.
     */
    @Deprecated("Should only be used from inside the Model. Use GraphCDManager.deleteGraphTemplate instead")
    @Delete
    abstract suspend fun deleteGraphTemplate(graph: GraphTemplateEntity)

    /**
     * It deletes the GraphTemplateEntity with the given id from the table.
     */
    @Deprecated("Should only be used from inside the Model. Use GraphCDManager.deleteGraphTemplate instead")
    @Query("DELETE FROM graphTemplate WHERE id = :id")
    abstract suspend fun deleteGraphTemplateById(id: Int)

    /**
     * It provides all ProjectTemplateEntities.
     */
    @Deprecated(
        "Should only be used from inside the model. Use getAllProjectTemplateData() instead",
        ReplaceWith("getAllProjectTemplateData()")
    )
    @Query("SELECT * FROM projectTemplate")
    abstract fun getAllProjectTemplateEntities(): Flow<List<ProjectTemplateEntity>>

    /**
     * It provides all ProjectTemplateEntities with one of the given ids.
     */
    @Deprecated(
        "Should only be used from inside the model. Use getProjectTemplateData() instead",
        ReplaceWith("getProjectTemplateData()")
    )
    @Query("SELECT * FROM projectTemplate WHERE id in (:ids)")
    abstract fun getProjectTemplateEntities(vararg ids: Int): Flow<List<ProjectTemplateEntity>>

    /**
     * It provides all ProjectTemplateEntities, with the specified creator.
     */
    @Deprecated(
        "Should only be used from inside the model. Use getProjectTemplateData() instead",
        ReplaceWith("getProjectTemplateDataByCreator()")
    )
    @Query("SELECT * FROM projectTemplate WHERE createdBy = :creator")
    abstract fun getProjectTemplateEntityByCreator(creator: User)
            : Flow<List<ProjectTemplateEntity>>

    /**
     * It provides all GraphTemplateEntities.
     */
    @Deprecated(
        "Should only be used from inside the model. Use getProjectTemplateData() instead",
        ReplaceWith("getAllGraphTemplateData()")
    )
    @Query("SELECT * FROM graphTemplate")
    abstract fun getAllGraphTemplateEntities(): Flow<List<GraphTemplateEntity>>

    /**
     * It provides all GraphTemplateEntities with one of the given ids.
     */
    @Deprecated(
        "Should only be used from inside the model. Use getGraphTemplateData() instead",
        ReplaceWith("getGraphTemplateData()")
    )
    @Query("SELECT * FROM graphTemplate WHERE id IN (:ids)")
    abstract fun getGraphTemplateEntities(vararg ids: Int): Flow<List<GraphTemplateEntity>>

    /**
     * It provides all GraphTemplateEntities, with the specified creator.
     */
    @Deprecated(
        "Should only be used from inside the model. Use getGraphTemplatesForProjectTemplate() instead",
        ReplaceWith("getGraphTemplatesForProjectTemplate()")
    )
    @Query("SELECT * FROM graphTemplate WHERE projectTemplateId = :id")
    abstract fun getGraphTemplateEntitiesForTemplate(id: Int): Flow<List<GraphTemplateEntity>>

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
                        template.projectTemplateId,
                        template.name,
                        template.description,
                        template.type,
                        template.color,
                        template.createdBy,
                        template.onlineId
                    )
                )
            }
            dataList.toList()
        }
    }
}
