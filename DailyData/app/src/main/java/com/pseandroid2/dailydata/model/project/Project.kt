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

package com.pseandroid2.dailydata.model.project

import android.graphics.drawable.Drawable
import com.pseandroid2.dailydata.model.Graph
import com.pseandroid2.dailydata.model.settings.Settings
import com.pseandroid2.dailydata.model.users.User
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

    fun createTransformationFromString(transformationString: String): DataTransformation<out Any>

    /**
     * @param D Type of the elements that this DataTransformation will output as DataSets
     */
    abstract class DataTransformation<D : Any> private constructor(
        private val table: Table,
        private val function: TransformationFunction<D>,
        private vararg val cols: Int
    ) {

        fun recalculate(): List<D> {
            return function.execute(map(table))
        }

        fun toFunctionString(): String {
            return table.getLayout().toJSON() + "|#|" + function.toCompleteString()
        }

        private fun map(table: Table): List<List<Any>> {
            val returnList = mutableListOf<List<Any>>()
            for (i in cols) {
                returnList.add(table.getColumn(i))
            }
            return returnList
        }

    }

}

interface ProjectSkeleton {

    var id: Int

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