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
import com.pseandroid2.dailydata.model.settings.MapSettings
import org.junit.Assert
import org.junit.Test

class SettingTester {

    @Test
    fun checkMapSettings() {
        val map =
            mutableMapOf<String, String>(Pair("Test1", "3"), Pair("Test2", "5"), Pair("Test3", "0"))
        val settings = MapSettings(map)

        Assert.assertEquals("3", settings["Test1"])
        Assert.assertEquals("5", settings["Test2"])
        Assert.assertEquals("0", settings["Test3"])
        Assert.assertThrows(SettingNotFoundException::class.java) {
            settings["Some non-existent key"]
        }
    }

}