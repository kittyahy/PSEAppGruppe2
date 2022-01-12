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

interface Notification {

    var id: Int

    /**
     * @return the Message that should be delivered by this Notification
     */
    fun getMessage(): String

    fun sendNow(vararg args: Any): Boolean

    /**
     * @return a String from which to recreate this Notification.
     * Has to start with an identifier followed by a '|'.
     */
    fun toFactoryString(): String
    
    companion object {
        fun fromString(notificationString: String, message: String, id: Int): Notification {
            return when (notificationString.split("|")[0]) {
                "TIME" -> TimeNotification.fromString(notificationString, message, id)
                else -> {
                    throw IllegalArgumentException()
                }
            }
        }
    }

}