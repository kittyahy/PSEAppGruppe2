package com.pseandroid2.dailydata.model.database

import androidx.room.Dao
import androidx.room.Query
import com.pseandroid2.dailydata.model.database.entities.ProjectData

@Dao
abstract class ProjectDataDAO {
    @Query("SELECT id, name, description, wallpaper, onlineId FROM project")
    public abstract fun getAllProjectData(): List<ProjectData>
}