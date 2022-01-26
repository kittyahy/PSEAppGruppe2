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
import com.pseandroid2.dailydata.model.notifications.TimeNotification
import java.time.LocalDateTime

class Notification(
    override val id: Long,
    val message: String,
    val time: LocalDateTime,
    val notificationsDAO: NotificationsDAO,
    val projectID: Long
) : Identifiable {
    constructor(
        timeNotification: TimeNotification,
        notificationsDAO: NotificationsDAO,
        projectID: Long
    ) : this(
        timeNotification.id.toLong(),
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
        notificationsDAO.deleteNotification(projectID.toInt(), id.toInt())
    }

    fun toDBEquivalent(): TimeNotification {
        return TimeNotification(message, time.toLocalTime(), id.toInt())
    }
}
