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

package com.pseandroid2.dailydata.workingtest

import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.pseandroid2.dailydata.MainActivity
import kotlinx.coroutines.InternalCoroutinesApi
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test

/**
 * Test to reproduce bugs
 */

class ReproduceTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()


    /**
     * [Kresse-project] [com.pseandroid2.dailydata.workingtest.ReproduceTest.CreateProjectTest]
     */
    @Ignore("needs a KresseProject")
    //@assert(needs a project with name Kresse)
    @InternalCoroutinesApi
    @Test
    fun addButtonTest() {
        composeRule.onAllNodes(matcher = hasText("Kresse")).onFirst().performClick()
        composeRule.onNodeWithText("Add").assertExists()
        composeRule.onNodeWithText("Add").performClick()
    }

    @Ignore("To fix")
    @InternalCoroutinesApi
    @Test
    fun addAGraphToAProjectTest() {
        composeRule.onNodeWithText("Add new Project").performClick()
        composeRule.onNodeWithText("Add Title").performTextInput("LiniengraphProjekt")
        composeRule.onNodeWithText("Add Table Column").performClick()
        composeRule.onNodeWithText("Name").performTextInput("XAchse")
        composeRule.onNodeWithText("Unit").performTextInput("X")
        composeRule.onNodeWithText("OK").performClick()
        composeRule.onNodeWithText("Button").performClick()
        composeRule.onNodeWithText("Name").performTextInput("XWert")
        composeRule.onNodeWithText("Unit").performTextInput("1")
        composeRule.onNodeWithText("OK").performClick()
        composeRule.onNodeWithText("Add Graph").performClick()
        composeRule.onNodeWithText("Line Chart").performClick()
        composeRule.onNodeWithText("Save").performClick()
    }
}
