package com.pseandroid2.dailydata.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.pseandroid2.dailydata.model.database.daos.GraphCDManager
import com.pseandroid2.dailydata.model.database.daos.ProjectCDManager
import com.pseandroid2.dailydata.model.database.daos.ProjectDataDAO
import com.pseandroid2.dailydata.model.database.entities.ProjectEntity
import com.pseandroid2.dailydata.model.database.entities.ProjectUserMap
import com.pseandroid2.dailydata.model.database.entities.RowEntity

@Database(entities = [ProjectEntity::class, ProjectUserMap::class, RowEntity::class], version = 1)
@TypeConverters(
    DateTimeConversion::class,
    UserConversion::class,
    TransformationConversion::class,
    GraphTypeConversion::class,
    UIElementTypeConversion::class
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun projectDataDAO(): ProjectDataDAO

    fun projectCDManager(): ProjectCDManager {
        return ProjectCDManager.getInstance()
    }

    fun graphCDManager(): GraphCDManager {
        return GraphCDManager.getInstance()
    }
}