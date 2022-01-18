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

package com.pseandroid2.dailydata.model

import android.graphics.drawable.Drawable
import com.pseandroid2.dailydata.model.notifications.Notification
import com.pseandroid2.dailydata.model.table.Table
import com.pseandroid2.dailydata.model.table.TableLayout
import com.pseandroid2.dailydata.model.transformation.TransformationFunction

/**
 * Contains all data of one specific Project
 */
interface Project {

    /**
     * @return The ProjectSkeleton of this Project
     */
    fun getProjectSkeleton(): ProjectSkeleton

    fun getTable(): Table

    fun getAdmin(): User

    fun isOnline(): Boolean

    fun getUsers(): Collection<User>

    abstract class DataTransformation<O : Any, D : Any>(
        private val table: Table,
        private val function: TransformationFunction<O, out Any, D>
    ) {

        abstract fun recalculate(): List<D>

    }

}

interface ProjectSkeleton {

    fun getID(): Int
    fun setID(id: Int)

    fun getOnlineId(): Long

    fun getName(): String

    fun getDescription(): String

    fun getWallpaper(): Drawable
    fun getWallpaperPath(): String

    fun getGraphs(): Collection<Graph>

    fun getProjectSettings(): Settings

    fun getNotifications(): Collection<Notification>

}

interface ProjectTemplate {

    fun getProjectSkeleton(): ProjectSkeleton

    fun getTableLayout(): TableLayout

    fun getCreator(): User

}