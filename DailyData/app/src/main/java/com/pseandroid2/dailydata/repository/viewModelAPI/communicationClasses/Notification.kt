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

import com.pseandroid2.dailydata.model.database.daos.NotificationsDAO
import com.pseandroid2.dailydata.model.notifications.Notification
import com.pseandroid2.dailydata.model.notifications.TimeNotification
import com.pseandroid2.dailydata.model.project.Project
import com.pseandroid2.dailydata.model.project.ProjectBuilder
import com.pseandroid2.dailydata.repository.commandCenter.commands.IllegalOperationException
import java.time.LocalTime

//TODO("Robin changes")
fun Notification(
    id: Int,
    message: String,
    time: LocalTime
) : com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Notification {
    TODO()
}

class Notification(
    override val id: Int,
    val message: String,
    val time: LocalTime,
    private val notificationsDAO: NotificationsDAO? = null,
    val projectID: Int
) : Identifiable(), Convertible<Notification> {
    constructor(
        timeNotification: TimeNotification,
        notificationsDAO: NotificationsDAO?,
        projectID: Int
    ) : this(
        timeNotification.id,
        timeNotification.getMessage(),
        TODO(), //timeNotification.send
        notificationsDAO,
        projectID
    )

    override fun deleteIsPossible(): Boolean {
        return true
    }

    //@throws IllegalOperationException
    override suspend fun delete() {
        TODO()
    }

    override fun toDBEquivalent(): TimeNotification {
        return TimeNotification(message, time, id)
    }

    override fun addYourself(builder: ProjectBuilder<out Project>) {
        builder.addNotifications(listOf(toDBEquivalent()))
    }
}
