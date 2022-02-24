package com.pseandroid2.dailydata.globaltests

import android.util.Log
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTextReplacement
import androidx.test.internal.runner.junit4.statement.UiThreadStatement
import com.pseandroid2.dailydata.Main
import com.pseandroid2.dailydata.MainActivity
import com.pseandroid2.dailydata.util.Consts.LOG_TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test

/**
 * testing: "Ändere Projektnamen", 7.1.4
 */
class GT714 {
    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    val projectName = "GT714"
    val newProjectName = "ChangedName$projectName"
    @Ignore("Saving project changes does not work")
    @ExperimentalCoroutinesApi
    @Test
    @InternalCoroutinesApi
    fun changeProjectName() = runTest {
        // Create and open new project
        GlobalTestsHelpingMethods.createTestProject(composeRule, projectName)
        composeRule.onAllNodesWithText(projectName).onFirst().performClick()
        delay(500)

        // Change the project name
        composeRule.onNodeWithText("Settings").performClick()
        delay(500)
        composeRule.onNodeWithText(projectName).performTextReplacement(newProjectName)
        // TODO("Implementiere die Funktionalität: Speichere Projektänderungen")
        composeRule.onNodeWithText("Save").performClick()
        UiThreadStatement.runOnUiThread {
            composeRule.activity.onBackPressed()
        }
        delay(2000)

        // Check if project name changed
        composeRule.onNodeWithText(newProjectName).assertExists()
    }


}