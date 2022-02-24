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
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import java.time.LocalTime

/**
 * Tests: "Projektbenachrichtigung ändern", 7.1.6
 */
class GT716 {
    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    private val projectName = "GT7.1.6"

    @Ignore("Saving project changes does not work")
    @Test
    @InternalCoroutinesApi
    fun changeNotification() {
        // Create and open new project
        GlobalTestsHelpingMethods.createTestProject(composeRule, projectName)
        composeRule.onAllNodesWithText(projectName).onFirst().performClick()
        composeRule.onNodeWithText("Settings").performClick()

        // Modify notification (corresponds to deleting and creating a new notification)
        composeRule.onNodeWithTag("Delete").performClick()
        composeRule.onNodeWithText("Add Notification").performClick()
        var time = LocalTime.now()
        composeRule.onNodeWithText("Name").performTextInput("Modified Notification")
        composeRule.onNodeWithText("OK").performClick()

        composeRule.onNodeWithText("Save").performClick()
        // TODO("Implementiere die Funktionalität: Speichere Projektänderungen")

        // Go back to the start screen
        runBlocking {
            launch(Dispatchers.Main) {
                composeRule.activity.onBackPressed()
            }
        }
        runBlocking {
            delay(2000)
        }

        // Open the created project and check if the changes are saved
        composeRule.onAllNodesWithText(projectName).onFirst().performClick()
        composeRule.onNodeWithText("Settings").performClick()
        composeRule.onNodeWithText(time.hour.toString() + ":" + time.minute.toString())
            .assertExists()
    }
}

