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

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.pseandroid2.dailydata.model.database.AppDataBase
import com.pseandroid2.dailydata.model.database.daos.SettingsDAO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class SettingsDatabaseTester {
    private lateinit var db: AppDataBase
    private lateinit var settingsDAO: SettingsDAO

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDataBase::class.java).build()
        settingsDAO = db.settingsDAO()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun checkInsertion() = runTest {
        val map = mapOf<String, String>(Pair("A", "1"), Pair("B", "2"))
        val settings = MapSettings(map)
        val result = async {
            settingsDAO.getProjectSettings(1).first {
                @Suppress("Deprecation")
                it.getAllSettings().size == 2
            }
        }
        for (setting in settings) {
            settingsDAO.createProjectSetting(1, setting.first, setting.second)
        }

        val awaitedResult = result.await()

        assertEquals(map["A"], awaitedResult["A"])
        assertEquals(map["B"], awaitedResult["B"])
    }
}