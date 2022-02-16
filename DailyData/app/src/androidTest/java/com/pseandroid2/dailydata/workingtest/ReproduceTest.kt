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
     * If anything needs the Kresse - Project
     */
    @InternalCoroutinesApi
    @Test
    fun CreateProjectTest() {

        composeRule.onNodeWithText("Add new Project").performClick()
        composeRule.onNodeWithText("Add Title").performTextInput("Kresse")
        composeRule.onNodeWithText("Add Table Column").performClick()
        composeRule.onNodeWithText("Name").performTextInput("Höhe")
        composeRule.onNodeWithText("Unit").performTextInput("cm")
        composeRule.onNodeWithText("OK").performClick()
        composeRule.onNodeWithText("Button").performClick()
        composeRule.onNodeWithText("Name").performTextInput("1cm erhöhen")
        composeRule.onNodeWithText("Unit").performTextInput("1")
        composeRule.onNodeWithText("OK").performClick()
        composeRule.onNodeWithText("Save").performClick()
    }

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
