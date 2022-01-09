package com.pseandroid2.dailydata.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
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
import com.pseandroid2.dailydata.model.database.entities.GraphSettingEntity
import com.pseandroid2.dailydata.model.database.entities.GraphTemplateEntity
import com.pseandroid2.dailydata.model.database.entities.MissingSlotEntity
import com.pseandroid2.dailydata.model.database.entities.NotificationEntity
import com.pseandroid2.dailydata.model.database.entities.ProjectEntity
import com.pseandroid2.dailydata.model.database.entities.ProjectSettingEntity
import com.pseandroid2.dailydata.model.database.entities.ProjectTemplateEntity
import com.pseandroid2.dailydata.model.database.entities.ProjectUserMap
import com.pseandroid2.dailydata.model.database.entities.RowEntity
import com.pseandroid2.dailydata.model.database.entities.UIElementMap

@Database(
    entities = [
        ProjectEntity::class,
        ProjectUserMap::class,
        RowEntity::class,
        GraphEntity::class,
        UIElementMap::class,
        MissingSlotEntity::class,
        GraphTemplateEntity::class,
        ProjectTemplateEntity::class,
        NotificationEntity::class,
        ProjectSettingEntity::class,
        GraphSettingEntity::class
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

    companion object {
        private var instance: AppDataBase? = null

        /**
         * @throws NullPointerException when database creation fails
         */
        fun getInstance(context: Context): AppDataBase {
            if (instance == null) {
                synchronized(this)
                {
                    Room.databaseBuilder(context, AppDataBase::class.java, "app_database").build()
                }
            }
            return instance!!
        }
    }

    abstract fun projectDataDAO(): ProjectDataDAO

    abstract fun uiElementDAO(): UIElementDAO

    abstract fun graphDAO(): GraphDAO

    abstract fun notificationsDAO(): NotificationsDAO

    abstract fun tableContentDAO(): TableContentDAO
}