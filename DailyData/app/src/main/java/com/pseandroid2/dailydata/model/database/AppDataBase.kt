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

package com.pseandroid2.dailydata.model.database

import android.app.Application
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
import com.pseandroid2.dailydata.model.database.daos.SettingsDAO
import com.pseandroid2.dailydata.model.database.daos.TableContentDAO
import com.pseandroid2.dailydata.model.database.daos.TemplateDAO
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

/**
 * Room Database Class. Creation should be done via getInstance() instead of Constructor.
 */
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
abstract class AppDataBase protected constructor() : RoomDatabase() {

    private var _graphCDManager: GraphCDManager? = null
    private var _projectCDManager: ProjectCDManager? = null

    companion object {
        private var instance: AppDataBase? = null

        /**
         * @throws NullPointerException when database creation fails
         */
        fun getInstance(context: Application): AppDataBase {
            if (instance == null) {
                synchronized(this)
                {
                    instance =
                        Room.databaseBuilder(context, AppDataBase::class.java, "app_database")
                            .build()
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

    abstract fun settingsDAO(): SettingsDAO

    abstract fun templateDAO(): TemplateDAO

    /**
     * @throws NullPointerException when database creation fails
     */
    fun graphCDManager(): GraphCDManager {
        if (_graphCDManager == null) {
            synchronized(this)
            {
                _graphCDManager = GraphCDManager(this)
            }
        }
        return _graphCDManager!!
    }

    /**
     * @throws NullPointerException when database creation fails
     */
    fun projectCDManager(): ProjectCDManager {
        if (_graphCDManager == null) {
            graphCDManager()
        }
        if (_projectCDManager == null) {
            synchronized(this)
            {
                _projectCDManager = ProjectCDManager(this)
            }
        }
        return ProjectCDManager(this)
    }
}