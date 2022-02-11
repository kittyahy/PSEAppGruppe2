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

import com.pseandroid2.dailydata.model.notifications.Notification
import com.pseandroid2.dailydata.model.notifications.TimeNotification
import com.pseandroid2.dailydata.model.project.ProjectBuilder
import com.pseandroid2.dailydata.repository.commandCenter.ExecuteQueue
import kotlinx.coroutines.flow.Flow
import java.time.LocalTime
import com.pseandroid2.dailydata.model.project.Project as ModelProject


class Notification(
    override var id: Int,
    val message: String,
    val time: LocalTime
) : Identifiable, Convertible<Notification> {
    override lateinit var executeQueue: ExecuteQueue
    override lateinit var project: Project

    constructor(timeNotification: TimeNotification) : this(
        timeNotification.id,
        timeNotification.getMessage(),
        timeNotification.getSent(),
    )

    override fun deleteIsPossible(): Flow<Boolean> {
        return TODO("deleteIsPossibleNotif")
    }

    //@throws IllegalOperationException
    override suspend fun delete() {
        TODO("DeleteNotification")
    }

    override fun toDBEquivalent(): TimeNotification {
        return TimeNotification(message, time, id)
    }

    override fun addYourself(builder: ProjectBuilder<out ModelProject>) {
        builder.addNotifications(listOf(toDBEquivalent()))
    }
}
