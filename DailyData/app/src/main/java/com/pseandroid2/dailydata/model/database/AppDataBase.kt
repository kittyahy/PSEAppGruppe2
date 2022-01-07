package com.pseandroid2.dailydata.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.pseandroid2.dailydata.model.database.daos.GraphCDManager
import com.pseandroid2.dailydata.model.database.daos.GraphDAO
import com.pseandroid2.dailydata.model.database.daos.NotificationsDAO
import com.pseandroid2.dailydata.model.database.daos.ProjectCDManager
import com.pseandroid2.dailydata.model.database.daos.ProjectDataDAO
import com.pseandroid2.dailydata.model.database.daos.TableContentDAO
import com.pseandroid2.dailydata.model.database.daos.UIElementDAO
import com.pseandroid2.dailydata.model.database.entities.GraphEntity
import com.pseandroid2.dailydata.model.database.entities.ProjectEntity
import com.pseandroid2.dailydata.model.database.entities.ProjectUserMap
import com.pseandroid2.dailydata.model.database.entities.RowEntity
import com.pseandroid2.dailydata.model.database.entities.UIElementMap

@Database(
    entities = [
        ProjectEntity::class,
        ProjectUserMap::class,
        RowEntity::class,
        GraphEntity::class,
        UIElementMap::class
    ],
    version = 1
)
@TypeConverters(
    DateTimeConversion::class,
    UserConversion::class,
    TransformationConversion::class,
    GraphTypeConversion::class,
    UIElementTypeConversion::class
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun projectDataDAO(): ProjectDataDAO

    abstract fun uiElementDAO(): UIElementDAO

    abstract fun graphDAO(): GraphDAO

    abstract fun notificationsDAO(): NotificationsDAO

    abstract fun tableContentDAO(): TableContentDAO

    fun projectCDManager(): ProjectCDManager {
        return ProjectCDManager.getInstance(this)
    }

    fun graphCDManager(): GraphCDManager {
        return GraphCDManager.getInstance(this)
    }
}