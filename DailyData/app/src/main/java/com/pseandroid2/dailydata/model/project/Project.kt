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
import com.pseandroid2.dailydata.model.graph.Graph
import com.pseandroid2.dailydata.model.graph.GraphTemplate
import com.pseandroid2.dailydata.model.notifications.Notification
import com.pseandroid2.dailydata.model.table.Table
import com.pseandroid2.dailydata.model.table.TableLayout
import com.pseandroid2.dailydata.model.transformation.TransformationFunction
import com.pseandroid2.dailydata.model.users.User
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Operation
import kotlinx.coroutines.flow.Flow

/**
 * Contains all data of one specific Project
 */
interface Project {

    companion object {
        const val MAXIMUM_PROJECT_USERS = 24
    }

    val isIllegalOperation: Map<Operation, Flow<Boolean>>

    @Deprecated("Access via skeleton is deprecated")
    val skeleton: ProjectSkeleton

    var id: Int
        set(value) {
            @Suppress("Deprecation")
            skeleton.id = value
        }
        @Suppress("Deprecation")
        get() = skeleton.id

    var onlineId: Long
        set(value) {
            @Suppress("Deprecation")
            skeleton.onlineId = value
        }
        @Suppress("Deprecation")
        get() = skeleton.onlineId

    val name: String
        @Suppress("Deprecation")
        get() = skeleton.name

    suspend fun setName(name: String)

    val desc: String
        @Suppress("Deprecation")
        get() = skeleton.desc

    suspend fun setDesc(desc: String)

    val path: String
        @Suppress("Deprecation")
        get() = skeleton.path

    suspend fun setPath(path: String)

    val color: Int
        @Suppress("Deprecation")
        get() = skeleton.color

    suspend fun setColor(color: Int)

    var graphs: MutableList<Graph<*, *>>

    var notifications: MutableList<Notification>
        set(value) {
            @Suppress("Deprecation")
            skeleton.notifications = value
        }
        @Suppress("Deprecation")
        get() = skeleton.notifications

    /**
     * It adds the given notification to the the project.
     */
    suspend fun addNotification(notification: Notification) {
        notifications.add(notification)
    }

    suspend fun removeNotification(id: Int)

    var table: Table

    var admin: User

    var isOnline: Boolean

    var users: MutableList<User>

    /**
     * It adds all given users to the project.
     */
    fun addUsers(usersToAdd: Collection<User>) = users.addAll(usersToAdd)

    /**
     * It creates a transformation for the data of the project from the given String.
     */
    fun createTransformationFromString(transformationString: String): DataTransformation<out Any>

    /**
     * It creates a new Data Transformation
     */
    @Suppress("Deprecation")
    fun <D : Any> createDataTransformation(
        function: TransformationFunction<D>,
        cols: List<Int> = IntArray(table.layout.size) { it }.toList()
    ) = DataTransformation(table, function, cols)

    /**
     * This class represents the Data transformation. It specifies how the data of a given project should be handled.
     * Every DataTransformation belongs to a project.
     * @param D Type of the elements that this DataTransformation will output as DataSets
     */
    class DataTransformation<D : Any>
    @Deprecated("Should only be created via a Project. Use Project.createDataTransformation() instead")
    constructor(
        private val table: Table,
        private val function: TransformationFunction<D>,
        val cols: List<Int>
    ) {

        /**
         * This method reapplied the function to the table.
         */
        fun recalculate(): List<D> {
            return function.execute(map(table))
        }

        /**
         * This method returns the function of the transformation as String.
         */
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

/**
 * This interface defines what a project should contain.
 */
interface ProjectSkeleton {

    var id: Int

    var onlineId: Long

    var name: String

    var desc: String

    /**
     * This methods returns the wallpaper of the instance, which extends the projectSkeleton.
     */
    fun getWallpaper(): Bitmap?

    var path: String
    var color: Int

    var notifications: MutableList<Notification>

}

/**
 * This interface defines, what a project template can and has.
 *
 */
interface ProjectTemplate {
    /**
     * This method returns the project Skeleton of the Project Template
     */
    @Deprecated("Properties of Project should be accessed directly, access via Skeleton is deprecated")
    val skeleton: ProjectSkeleton

    var id: Int
        set(value) {
            @Suppress("Deprecation")
            skeleton.id = value
        }
        @Suppress("Deprecation")
        get() = skeleton.id

    var onlineId: Long
        set(value) {
            @Suppress("Deprecation")
            skeleton.onlineId = value
        }
        @Suppress("Deprecation")
        get() = skeleton.onlineId

    var name: String
        set(value) {
            @Suppress("Deprecation")
            skeleton.name = value
        }
        @Suppress("Deprecation")
        get() = skeleton.name

    var desc: String
        set(value) {
            @Suppress("Deprecation")
            skeleton.desc = value
        }
        @Suppress("Deprecation")
        get() = skeleton.desc

    var path: String
        set(value) {
            @Suppress("Deprecation")
            skeleton.path = value
        }
        @Suppress("Deprecation")
        get() = skeleton.path

    var color: Int
        set(value) {
            @Suppress("Deprecation")
            skeleton.color = value
        }
        @Suppress("Deprecation")
        get() = skeleton.color

    var graphs: MutableList<GraphTemplate>

    /**
     * This function adds all given graphTemplates to the projectTemplate
     */
    fun addGraphs(graphsToAdd: Collection<GraphTemplate>) = graphs.addAll(graphsToAdd)

    var notifications: MutableList<Notification>
        set(value) {
            @Suppress("Deprecation")
            skeleton.notifications = value
        }
        @Suppress("Deprecation")
        get() = skeleton.notifications

    /**
     * This method adds a new notification to the Template
     */
    fun addNotifications(notificationsToAdd: Collection<Notification>) {
        for (notification in notificationsToAdd) {
            notifications.add(notification)
        }
    }

    /**
     * This method returns the tableLayout of the projectTemplate
     */
    fun getTableLayout(): TableLayout

    /**
     * Its provides the creator of the projectTemplate.
     */
    fun getCreator(): User

}
