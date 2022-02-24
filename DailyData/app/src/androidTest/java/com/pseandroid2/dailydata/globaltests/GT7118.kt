package com.pseandroid2.dailydata.globaltests

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.pseandroid2.dailydata.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test

/**
 * Tests: "Projektbenachrichtigung hinzufügen", 7.1.18
 */
class GT7118 {
    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    private val projectName = "GT7.1.18"

    @Ignore("Saving project changes does not work")
    @ExperimentalCoroutinesApi
    @InternalCoroutinesApi
    @Test
    fun addNotificationTest() = runTest {
        // Create and open new project
        composeRule.onAllNodesWithText(projectName).onFirst().performClick()
        composeRule.onNodeWithText("Settings").performClick()
        delay(500)

        // Add new notification
        composeRule.onNodeWithText("Add Notification").performClick()
        composeRule.onNodeWithText("Name").performTextInput("Neue Notification")
        composeRule.onNodeWithText("OK").performClick()
        composeRule.onNodeWithText("Save").performClick()
        // TODO("Implementiere die Funktionalität: Speichere Projektänderungen")
        delay(1000)

        // Go back to the start screen
        launch(Dispatchers.Main) {
            composeRule.activity.onBackPressed()
        }
        delay(2000)

        // Open the created project and check if the changes are saved
        composeRule.onAllNodesWithText(projectName).onFirst().performClick()
        composeRule.onNodeWithText("Settings").performClick()
        composeRule.onNodeWithTag("Delete").assertExists()
    }
}