package com.pseandroid2.dailydata.globaltests

import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.internal.runner.junit4.statement.UiThreadStatement
import com.pseandroid2.dailydata.MainActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

/**
 * Tests: "Leeres Projekt erstellen", 7.1.2
 */
class GT712 {
    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    private val projectName: String = "GT7.1.2"

    //@Ignore("Graph error")
    @ExperimentalCoroutinesApi
    @InternalCoroutinesApi
    @Test
    fun createEmptyProjectTest() = runTest {
        // Create a project
        composeRule.onNodeWithText("Add new Project").performClick()

        composeRule.onNodeWithText("Add Title").performTextInput(projectName)

        composeRule.onNodeWithText("Add Description")
            .performTextInput("Hier speichere ich die Höhe meiner Kresse")

        composeRule.onNodeWithText("Change Wallpaper").performClick()
        composeRule.onNodeWithText("Green").performClick()

        composeRule.onNodeWithText("Add Table Column").performClick()
        composeRule.onNodeWithText("Name").performTextInput("Höhe")
        composeRule.onNodeWithText("Unit").performTextInput("cm")
        composeRule.onNodeWithText("OK").performClick()

        composeRule.onNodeWithText("Button").performClick()
        composeRule.onNodeWithText("Name").performTextInput("1cm erhöhen")
        composeRule.onNodeWithText("Unit").performTextInput("1")
        composeRule.onNodeWithText("OK").performClick()

        composeRule.onNodeWithText("Add Notification").performClick()
        composeRule.onNodeWithText("Name").performTextInput("Meine Kresse")
        composeRule.onNodeWithText("OK").performClick()

        // TODO("Implementiere die Funktionalität: Füge einen Graphen zu dem Projekt hinzu")
        composeRule.onNodeWithText("Add Graph").performClick()
        composeRule.onNodeWithText("Line Chart").performClick()


        composeRule.onNodeWithText("Save").performClick()

        // Check if project exists in the start screen
        delay(3000)
        UiThreadStatement.runOnUiThread {
            composeRule.activity.onBackPressed()
        }
        delay(2000)

        composeRule.onNodeWithText("Add new Project").assertExists()
        composeRule.onAllNodes(matcher = hasText(projectName)).onFirst().assertExists()
    }
}