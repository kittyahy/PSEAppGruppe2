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
    abstract fun getAllProjectTemplateData(): Flow<List<ProjectTemplateEntity>>

    @Query("SELECT * FROM projectTemplate WHERE id in (:ids)")
    abstract fun getProjectTemplateData(vararg ids: Int): Flow<List<ProjectTemplateEntity>>

    @Query("SELECT * FROM projectTemplate WHERE createdBy = :creator")
    abstract fun getProjectTemplateByCreator(creator: String): Flow<List<ProjectTemplateEntity>>

    @Query("UPDATE projectTemplate SET name = :name WHERE id = :id")
    abstract fun setProjectTemplateName(id: Int, name: String)

    @Query("UPDATE projectTemplate SET description = :desc WHERE id = :id")
    abstract fun setProjectTemplateDescription(id: Int, desc: String)

    @Query("UPDATE projectTemplate SET wallpaper = :wallpaper WHERE id = :id")
    abstract fun setProjectTemplateWallpaper(id: Int, wallpaper: String)

    @Query("SELECT * FROM graphTemplate")
    abstract fun getAllGraphTemplateData(): Flow<List<GraphTemplateEntity>>

    @Query("SELECT * FROM graphTemplate WHERE createdBy = :creator")
    abstract fun getGraphTemplateByCreator(creator: String): Flow<List<GraphTemplateEntity>>

    @Query("UPDATE graphTemplate SET name = :name WHERE id = :id")
    abstract fun setGraphTemplateName(id: Int, name: String)

    @Query("UPDATE graphTemplate SET description = :desc WHERE id = :id")
    abstract fun setGraphTemplateDescription(id: Int, desc: String)

    /*========================SHOULD ONLY BE CALLED FROM INSIDE THE MODEL=========================*/
    @Insert
    abstract fun insertProjectTemplate(project: ProjectTemplateEntity)

    @Delete
    abstract fun deleteProjectTemplate(project: ProjectTemplateEntity)

    @Insert
    abstract fun insertGraphTemplate(graph: GraphTemplateEntity)

    @Delete
    abstract fun deleteGraphTemplate(graph: GraphTemplateEntity)
}