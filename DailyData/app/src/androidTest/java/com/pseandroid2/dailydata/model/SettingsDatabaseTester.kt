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