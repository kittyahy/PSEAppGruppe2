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

package com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses

import com.pseandroid2.dailydata.model.project.Project
import com.pseandroid2.dailydata.model.project.ProjectBuilder
import com.pseandroid2.dailydata.model.users.SimpleUser
import com.pseandroid2.dailydata.model.users.User
import com.pseandroid2.dailydata.repository.commandCenter.ExecuteQueue

class Member(
    override val id: Int,
    val name: String
): Identifiable, Convertible<User>{
    constructor(user: User) : this(user.getId().toInt(), user.getName()) //Todo Arne Fragen ob der Cast passt: Nein, User werden über firebaseID identifiziert und die ist ein String

    override lateinit var executeQueue: ExecuteQueue
    override fun deleteIsPossible(): Boolean {
        TODO("Not yet implemented")
    }

    //@throws IllegalOperationException
    override suspend fun delete() {
        TODO("Not yet implemented. Wish kriterium")
    }

    override fun toDBEquivalent(): User {
        return SimpleUser(id.toString(), name)
    }

    override fun addYourself(builder: ProjectBuilder<out Project>) {
        builder.addUsers(listOf(this.toDBEquivalent()))
    }
}
