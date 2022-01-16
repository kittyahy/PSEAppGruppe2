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

package com.pseandroid2.dailydata.model

import com.pseandroid2.dailydata.exceptions.SettingNotFoundException

class MapSettings(settings: Map<String, String>) : Settings {
    private val settings = settings.toMutableMap()

    @Suppress("OverridingDeprecatedMember")
    override fun getAllSettings(): Map<String, String> {
        return settings.toMap()
    }

    override fun get(key: String): String {
        return settings[key] ?: throw SettingNotFoundException()
    }

    override fun iterator(): Iterator<Pair<String, String>> {
        return MapSettingsIterator(this)
    }
}

class MapSettingsIterator(settings: MapSettings) : Iterator<Pair<String, String>> {
    @Suppress("Deprecation")
    val iterator = settings.getAllSettings().iterator()

    override fun hasNext(): Boolean {
        return iterator.hasNext()
    }

    override fun next(): Pair<String, String> {
        return iterator.next().toPair()
    }

}