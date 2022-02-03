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

package com.pseandroid2.dailydata.remoteDataSource.queue.observerLogic

/**
 * A class which allows the testing of the observers
 */
class UpdatedByObserverForTesting {
    private var updated: Int = 0

    /**
     * updates the Observer
     */
    fun update() {
        ++updated
    }

    /**
     * @return Boolean: True, wenn es schon vom Observer geupdated wurde, sonst false
     */
    fun isUpdated(): Boolean {
        if (updated == 0) {
            return false
        }
        return true
    }

    /**
     * @return Int: Wie oft dieses Objekt vom Observer geupdated wurde
     */
    fun getUpdated(): Int {
        return updated
    }

    /**
     * resets the update variable
     */
    fun resetUpdate() {
        updated = 0
    }
}