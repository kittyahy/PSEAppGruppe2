package com.pseandroid2.dailydata.globaltests

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
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
 * Tests: "Wallpaper ändern", 7.1.8
 */
class GT718 {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    private val projectName = "GT7.1.7"
    private val blueColor = "#2196F3"

    @ExperimentalCoroutinesApi
    @Ignore("Saving project changes does not work")
    @Test
    @InternalCoroutinesApi
    fun changeNotification() = runTest {
        // Create and open new project
        GlobalTestsHelpingMethods.createTestProject(composeRule, projectName)
        composeRule.onAllNodesWithText(projectName).onFirst().performClick()
        composeRule.onNodeWithText("Settings").performClick()
        delay(500)

        // Change the Wallpaper
        composeRule.onNodeWithText("Change Wallpaper").performClick()
        composeRule.onNodeWithText("Blue").performClick()
        composeRule.onNodeWithText("Save").performClick()
        // TODO("Implementiere die Funktionalität: Speichere Projektänderungen")

        // Go back to the start screen
        launch(Dispatchers.Main) {
            composeRule.activity.onBackPressed()
        }
        delay(2000)

        // Open the created project and check if the changes are saved
        composeRule.onAllNodesWithText(projectName).onFirst().performClick()
        composeRule.onNodeWithText("Settings").performClick()
        composeRule.onNodeWithTag("Wallpaper").assertIsDisplayed()
        // TODO("Finde eine Möglichkeit an die Farbe der, mit dem Tag gefundener Node, heranzugelangen")
    }
}

