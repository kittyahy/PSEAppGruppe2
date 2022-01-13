package com.pseandroid2.dailydata.model

import com.pseandroid2.dailydata.exceptions.SettingNotFoundException
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