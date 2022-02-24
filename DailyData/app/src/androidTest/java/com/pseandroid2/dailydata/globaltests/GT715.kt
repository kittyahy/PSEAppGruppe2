package com.pseandroid2.dailydata.globaltests

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
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
 * Tests: "Ändere Projektbeschreibung", 7.1.5
 */
class GT715 {
    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    private val projectName = "GT7.1.5"
    private val projectDescription = "This project tests if it's possible to change the project description"
    private val newDescription = "new project description"

    @Ignore("Saving project changes does not work")
    @Test
    @ExperimentalCoroutinesApi
    @InternalCoroutinesApi
    fun changeProjectDescription() = runTest {
        // Create and open new project
        GlobalTestsHelpingMethods.createTestProject(composeRule, projectName, projectDescription)
        composeRule.onAllNodesWithText(projectName).onFirst().performClick()
        composeRule.onNodeWithText("Settings").performClick()
        composeRule.onNodeWithText(projectDescription).performTextInput(newDescription)
        // TODO("Implementiere die Funktionalität: Speichere Projektänderungen")
        composeRule.onNodeWithText("Save").performClick()

        // Go back to the start screen
        launch(Dispatchers.Main) {
            composeRule.activity.onBackPressed()
        }
        delay(2000)

        // Check if the project hast the new description
        composeRule.onAllNodesWithText(projectName).onFirst().performClick()
        composeRule.onNodeWithText("Settings").performClick()
        composeRule.onNodeWithText(newDescription).assertExists()
    }
}