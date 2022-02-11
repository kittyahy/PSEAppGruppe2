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

/**
 * This interface declares, what a notification can. A notification is a message, which is outside the app.
 */
interface Notification {

    var id: Int

    /**
     * It returns the Message that should be delivered by this Notification
     */
    fun getMessage(): String

    /**
     * It provides, if the notification is sent.
     */
    fun sendNow(vararg args: Any): Boolean

    /**
     * It provides a String from which to recreate this Notification.
     * Has to start with an identifier followed by a '|'.
     */
    fun toFactoryString(): String

    companion object {
        fun fromString(notificationString: String, message: String, id: Int): Notification {
            val splitArray = notificationString.split("|") //Todo Arne validieren
            return when (splitArray[0]) {
                "TIME" -> TimeNotification.fromString(splitArray[1], message, id)
                else -> {
                    throw IllegalArgumentException()
                }
            }
        }
    }

}