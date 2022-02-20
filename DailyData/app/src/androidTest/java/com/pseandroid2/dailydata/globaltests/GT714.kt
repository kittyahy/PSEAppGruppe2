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

package com.pseandroid2.dailydata.globaltests

import android.util.Log
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTextReplacement
import com.pseandroid2.dailydata.Main
import com.pseandroid2.dailydata.MainActivity
import com.pseandroid2.dailydata.util.Consts.LOG_TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test

/**
 * testing: "Ändere Projektnamen", 7.1.4
 */
class GT714 {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    /**
     * [Kresse-project] [com.pseandroid2.dailydata.globaltests.DefaultProject.createProjectTest]
     */
   @Ignore(" needs a \"Kresse\" Project, save does not work")
    @Test
    @InternalCoroutinesApi
    fun changeProjectName() {

        composeRule.onAllNodesWithText("Kresse").onFirst().performClick()
        composeRule.onNodeWithText("Settings").performClick()
        composeRule.onNodeWithText("Kresse").performTextReplacement("Neue Kresse")
        TODO("Save does not work yet")
        composeRule.onNodeWithText("Save").performClick()
        runBlocking {
            launch(Dispatchers.Main) {
                composeRule.activity.onBackPressed()
            }
        }
        runBlocking {
            delay(2000)
        }
        composeRule.onNodeWithText("Neue Kresse").assertExists()
    }


}