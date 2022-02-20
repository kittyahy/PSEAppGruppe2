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

package com.pseandroid2.dailydata

import android.util.Log
import org.junit.Test
import org.junit.AfterClass
import org.junit.Before

// This class is done as a reference on how to implement @AfterClass Methods in kotlin
class AfterClassTest {

    private var test: String = "Complete"

    @Before
    fun setup() {
        // Set Values in companion object for correct teardown
        setTest(test)
    }

    companion object Teardown{
        private var test: String = ""

        fun setTest(newTest: String) {
            test = newTest
        }

        @AfterClass @JvmStatic fun teardown() {
            Log.d("Teardown", test)
        }
    }

    @Test
    fun test() {

    }
}