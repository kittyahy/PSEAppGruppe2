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

import com.pseandroid2.dailydata.model.users.User
import com.pseandroid2.dailydata.model.table.Table
import com.pseandroid2.dailydata.model.users.NullUser

class SimpleProject
@Deprecated("Directly constructing a project is Deprecated, Use ProjectBuilder instead")
constructor(
    private val skeleton: ProjectSkeleton,
    override var table: Table,
    override var admin: User = NullUser(),
    override var isOnline: Boolean = false,
    override var users: MutableList<User>
) : Project {

    init {
        if (isOnline && admin is NullUser) {
            throw IllegalArgumentException("Online Projects must have an admin!")
        }
    }

    @Suppress("Deprecation")
    @Deprecated("Properties of Project should be accessed directly, access via Skeleton is deprecated")
    override fun getProjectSkeleton() = skeleton

    override fun addUsers(usersToAdd: Collection<User>) {
        users.addAll(usersToAdd)
    }

    override fun createTransformationFromString(transformationString: String): Project.DataTransformation<out Any> {
        TODO("Not yet implemented")
    }
}