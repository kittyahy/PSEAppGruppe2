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

package com.pseandroid2.dailydata.model.notifications

import android.util.Log
import com.pseandroid2.dailydata.model.database.entities.NotificationEntity
import com.pseandroid2.dailydata.util.Consts.LOG_TAG
import java.time.LocalDate
import java.time.LocalTime

/**
 * This class represents a notification, which can be sent by a given time. It saves,
 * when the notification was last sent, and what.
 */
class TimeNotification(
    private val messageString: String,
    private val send: LocalTime,
    initId: Int
) : Notification {
    private var lastSent: LocalDate = LocalDate.now()
    override var id: Int = initId

    companion object {
        fun fromString(paramString: String, message: String, id: Int): TimeNotification {
            val params = paramString.split(",")
            val lastSent: LocalDate = LocalDate.parse(params[0])
            val send: LocalTime = LocalTime.parse(params[1])
            val notification = TimeNotification(message, send, id)
            notification.setSent(lastSent)
            return notification
        }
    }

    override fun getMessage(): String {
        return messageString
    }

    override fun sendNow(vararg args: Any): Boolean {
        if (args.isNotEmpty()) {
            throw IllegalArgumentException()
        }

        if (LocalDate.now() <= lastSent) {
            return false
        }
        return send <= LocalTime.now()
    }

    override fun toFactoryString(): String {
        Log.d(LOG_TAG, "TIME|$lastSent,$send")
        return "TIME|$lastSent,$send"
    }

    fun setSent() {
        lastSent = LocalDate.now()
    }

    fun setSent(date: LocalDate) {
        lastSent = date
    }
}