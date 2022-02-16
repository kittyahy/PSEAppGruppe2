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

import com.pseandroid2.dailydata.model.graph.Graph
import com.pseandroid2.dailydata.model.table.ArrayListTable
import com.pseandroid2.dailydata.model.table.Table
import com.pseandroid2.dailydata.model.users.NullUser
import com.pseandroid2.dailydata.model.users.User
import com.pseandroid2.dailydata.repository.viewModelAPI.ProjectHandler
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Operation
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.Flow

class CacheOnlyProject
constructor(
    @Deprecated("Access via Skeleton is Deprecated")
    override val skeleton: ProjectSkeleton = SimpleSkeleton(),
    override var table: Table = ArrayListTable(),
    override var admin: User = NullUser(),
    override var isOnline: Boolean = false,
    override var users: MutableList<User> = mutableListOf(),
    override var graphs: MutableList<Graph<*, *>> = mutableListOf(),
    override val isIllegalOperation: Map<Operation, Flow<Boolean>> = mapOf()
) : Project {

    init {
        if (isOnline && admin is NullUser) {
            throw IllegalArgumentException("Online Projects must have an admin!")
        }
    }

    override suspend fun setName(name: String) {
        @Suppress("DEPRECATION")
        skeleton.name = name
    }

    override suspend fun setDesc(desc: String) {
        @Suppress("DEPRECATION")
        skeleton.desc = desc
    }

    override suspend fun setPath(path: String) {
        @Suppress("DEPRECATION")
        skeleton.path = path
    }

    override suspend fun setColor(color: Int) {
        @Suppress("DEPRECATION")
        skeleton.color = color
    }

    override suspend fun removeNotification(id: Int) {
        @Suppress("DEPRECATION")
        skeleton.notifications.removeAt(id)
    }

    override fun createTransformationFromString(transformationString: String): Project.DataTransformation<out Any> {
        TODO("Not yet implemented")
    }

    suspend fun writeToDBAsync(projectHandler: ProjectHandler): Deferred<Int> {
        return projectHandler.newProjectAsync(this)
    }
}