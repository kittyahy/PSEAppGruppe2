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

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.pseandroid2.dailydata.model.graph.Graph
import com.pseandroid2.dailydata.model.graph.GraphTemplate
import com.pseandroid2.dailydata.model.notifications.Notification
import com.pseandroid2.dailydata.model.settings.Settings
import com.pseandroid2.dailydata.model.table.Table
import com.pseandroid2.dailydata.model.table.TableLayout
import com.pseandroid2.dailydata.model.transformation.TransformationFunction
import com.pseandroid2.dailydata.model.users.User

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

    var color: Int
        set(value) {
            @Suppress("Deprecation")
            getProjectSkeleton().color = value
        }
        @Suppress("Deprecation")
        get() = getProjectSkeleton().color

    var graphs: MutableList<Graph<*, *>>

    var settings: Settings
        set(value) {
            @Suppress("Deprecation")
            getProjectSkeleton().settings = value
        }
        @Suppress("Deprecation")
        get() = getProjectSkeleton().settings

    fun addSettings(settingsToAdd: Settings) {
        for (setting in settingsToAdd) {
            settings[setting.first] = setting.second
        }
    }

    var notifications: MutableList<Notification>
        set(value) {
            @Suppress("Deprecation")
            getProjectSkeleton().notifications = value
        }
        @Suppress("Deprecation")
        get() = getProjectSkeleton().notifications


    fun addNotifications(notificationsToAdd: Collection<Notification>) {
        for (notification in notificationsToAdd) {
            notifications.add(notification)
        }
    }

    var table: Table

    var admin: User

    var isOnline: Boolean

    var users: MutableList<User>
    fun addUsers(usersToAdd: Collection<User>) = users.addAll(usersToAdd)

    fun createTransformationFromString(transformationString: String): DataTransformation<out Any>

    @Suppress("Deprecation")
    fun <D : Any> createDataTransformation(
        function: TransformationFunction<D>,
        cols: List<Int> = IntArray(table.getLayout().getSize()) { it }.toList()
    ) = DataTransformation(table, function, cols)

    /**
     * @param D Type of the elements that this DataTransformation will output as DataSets
     */
    class DataTransformation<D : Any>
    @Deprecated("Should only be created via a Project. Use Project.createDataTransformation() instead")
    constructor(
        private val table: Table,
        private val function: TransformationFunction<D>,
        val cols: List<Int>
    ) {

        fun recalculate(): List<D> {
            return function.execute(map(table))
        }

        fun toFunctionString(): String {
            return function.toCompleteString()
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

    fun getWallpaper(): Bitmap?

    var path: String
    var color: Int

    var settings: Settings

    var notifications: MutableList<Notification>

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

    var color: Int
        set(value) {
            @Suppress("Deprecation")
            getProjectSkeleton().color = value
        }
        @Suppress("Deprecation")
        get() = getProjectSkeleton().color

    var graphs: MutableList<GraphTemplate>

    fun addGraphs(graphsToAdd: Collection<GraphTemplate>) = graphs.addAll(graphsToAdd)

    var settings: Settings
        set(value) {
            @Suppress("Deprecation")
            getProjectSkeleton().settings = value
        }
        @Suppress("Deprecation")
        get() = getProjectSkeleton().settings

    fun addSettings(settingsToAdd: Settings) {
        for (setting in settingsToAdd) {
            settings[setting.first] = setting.second
        }
    }

    var notifications: MutableList<Notification>
        set(value) {
            @Suppress("Deprecation")
            getProjectSkeleton().notifications = value
        }
        @Suppress("Deprecation")
        get() = getProjectSkeleton().notifications


    fun addNotifications(notificationsToAdd: Collection<Notification>) {
        for (notification in notificationsToAdd) {
            notifications.add(notification)
        }
    }

    fun getTableLayout(): TableLayout

    fun getCreator(): User

}