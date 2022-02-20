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
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.printToString
import androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread
import com.pseandroid2.dailydata.Main
import com.pseandroid2.dailydata.MainActivity
import io.mockk.InternalPlatformDsl.toArray
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import java.lang.AssertionError
import java.lang.reflect.Modifier

class GT7147 {
    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    private val projectName: String = "GT7147";

    @InternalCoroutinesApi
    @Before
    fun setup() {
        composeRule.setContent {
            Main()
        }
        createTestProject(projectName)
        composeRule.onNodeWithText("Project").performClick()
    }

    @Ignore("TODO: Lösche das Projekt nach dem Test, sodass es nicht mehrere Projekte mit dem gleichen Namen existieren, da sonst hier ein Error geworfen wird")
    /**
     * tests: "Zeit zum Öffnen eines Projekts", 7.1.47
     */
    @InternalCoroutinesApi
    @Test
    fun timeToOpenProject() {
        val maxTime = 1000;

        val startTime = composeRule.mainClock.currentTime
        composeRule.onNodeWithText(projectName).performClick()
        composeRule.onNodeWithText("Input").assertExists() // Means that the switch to the project screen succeeded
        Assert.assertTrue((composeRule.mainClock.currentTime-startTime) < maxTime)
    }

    private fun createTestProject(projectName: String) {
        if (composeRule.onAllNodesWithText(projectName).fetchSemanticsNodes().isNotEmpty()){
            return
        }

        composeRule.onNodeWithText(projectName).assertDoesNotExist()

        composeRule.onNodeWithText("Add new Project").performClick()

        composeRule.onNodeWithText("Add Title").performTextInput(projectName)

        composeRule.onNodeWithText("Add Description")
            .performTextInput("Projekt für einen globalen Test")

        composeRule.onNodeWithText("Change Wallpaper").performClick()
        composeRule.onNodeWithText("Green").performClick()

        composeRule.onNodeWithText("Add Table Column").performClick()
        composeRule.onNodeWithText("Name").performTextInput("Höhe")
        composeRule.onNodeWithText("Unit").performTextInput("cm")
        composeRule.onNodeWithText("OK").performClick()

        composeRule.onNodeWithText("Save").performClick()

        runBlocking {delay(2000)}
        runOnUiThread {
            composeRule.activity.onBackPressed()
        }
        runBlocking {delay(1000)}
    }
}