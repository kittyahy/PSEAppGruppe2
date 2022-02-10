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

package com.pseandroid2.dailydata.model.settings

/**
 *  This interface defines how settings should be handled. It stores settings.
 */
interface Settings : Iterable<Pair<String, String>> {

    /**
     * It provides all settings, which are saved.
     */
    @Deprecated("Should not be used outside the model. Use get or iterate over Settings directly")
    fun getAllSettings(): Map<String, String>

    /**
     * It provides a setting to the given key.
     */
    operator fun get(key: String): String

    /**
     * It checks if a key is in the map
     */
    fun containsKey(key: String): Boolean


    operator fun set(key: String, value: String)

}