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

import android.graphics.Color
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
    @Deprecated("Properties of Project should be accessed directly, access via Skeleton is deprecated")
    fun getProjectSkeleton(): ProjectSkeleton

    var id: Int
        set(value) {
            @Suppress("Deprecation")
            getProjectSkeleton().id = value
        }
        @Suppress("Deprecation")
        get() = getProjectSkeleton().id

    var onlineId: Long
        set(value) {
            @Suppress("Deprecation")
            getProjectSkeleton().onlineId = value
        }
        @Suppress("Deprecation")
        get() = getProjectSkeleton().onlineId

    var name: String
        set(value) {
            @Suppress("Deprecation")
            getProjectSkeleton().name = value
        }
        @Suppress("Deprecation")
        get() = getProjectSkeleton().name

    var desc: String
        set(value) {
            @Suppress("Deprecation")
            getProjectSkeleton().desc = value
        }
        @Suppress("Deprecation")
        get() = getProjectSkeleton().desc

    var path: String
        set(value) {
            @Suppress("Deprecation")
            getProjectSkeleton().path = value
        }
        @Suppress("Deprecation")
        get() = getProjectSkeleton().path

    var color: Color
        set(value) {
            @Suppress("Deprecation")
            getProjectSkeleton().color = value
        }
        @Suppress("Deprecation")
        get() = getProjectSkeleton().color

    @Suppress("Deprecation")
    fun getGraphs() = getProjectSkeleton().getGraphs()

    @Suppress("Deprecation")
    fun addGraphs(graphs: Collection<Graph>) = getProjectSkeleton().addGraphs(graphs)

    @Suppress("Deprecation")
    fun getSettings() = getProjectSkeleton().getProjectSettings()

    @Suppress("Deprecation")
    fun addSettings(settings: Settings) = getProjectSkeleton().addProjectSettings(settings)

    @Suppress("Deprecation")
    fun getNotifications() = getProjectSkeleton().getNotifications()

    @Suppress("Deprecation")
    fun addNotifications(notifications: Collection<Notification>) =
        getProjectSkeleton().addNotifications(notifications)

    var table: Table

    var admin: User

    var isOnline: Boolean

    fun getUsers(): Collection<User>
    fun addUsers(users: Collection<User>)

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

    var onlineId: Long

    var name: String

    var desc: String

    fun getWallpaper(): Drawable

    var path: String
    var color: Color

    fun getGraphs(): Collection<Graph>
    fun addGraphs(graphs: Collection<Graph>)

    fun getProjectSettings(): Settings
    fun addProjectSettings(settings: Settings)

    fun getNotifications(): Collection<Notification>
    fun addNotifications(notifications: Collection<Notification>)

}

interface ProjectTemplate {
    @Deprecated("Properties of Project should be accessed directly, access via Skeleton is deprecated")
    fun getProjectSkeleton(): ProjectSkeleton

    var id: Int
        set(value) {
            @Suppress("Deprecation")
            getProjectSkeleton().id = value
        }
        @Suppress("Deprecation")
        get() = getProjectSkeleton().id

    var onlineId: Long
        set(value) {
            @Suppress("Deprecation")
            getProjectSkeleton().onlineId = value
        }
        @Suppress("Deprecation")
        get() = getProjectSkeleton().onlineId

    var name: String
        set(value) {
            @Suppress("Deprecation")
            getProjectSkeleton().name = value
        }
        @Suppress("Deprecation")
        get() = getProjectSkeleton().name

    var desc: String
        set(value) {
            @Suppress("Deprecation")
            getProjectSkeleton().desc = value
        }
        @Suppress("Deprecation")
        get() = getProjectSkeleton().desc

    var path: String
        set(value) {
            @Suppress("Deprecation")
            getProjectSkeleton().path = value
        }
        @Suppress("Deprecation")
        get() = getProjectSkeleton().path

    var color: Color
        set(value) {
            @Suppress("Deprecation")
            getProjectSkeleton().color = value
        }
        @Suppress("Deprecation")
        get() = getProjectSkeleton().color

    @Suppress("Deprecation")
    fun getGraphs() = getProjectSkeleton().getGraphs()

    @Suppress("Deprecation")
    fun addGraphs(graphs: Collection<Graph>) = getProjectSkeleton().addGraphs(graphs)

    @Suppress("Deprecation")
    fun getSettings() = getProjectSkeleton().getProjectSettings()

    @Suppress("Deprecation")
    fun addSettings(settings: Settings) = getProjectSkeleton().addProjectSettings(settings)

    @Suppress("Deprecation")
    fun getNotifications() = getProjectSkeleton().getNotifications()

    @Suppress("Deprecation")
    fun addNotifications(notifications: Collection<Notification>) =
        getProjectSkeleton().addNotifications(notifications)

    fun getTableLayout(): TableLayout

    fun getCreator(): User

}