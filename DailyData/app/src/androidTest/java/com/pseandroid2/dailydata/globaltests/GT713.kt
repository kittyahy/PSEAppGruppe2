package com.pseandroid2.dailydata.globaltests

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.internal.runner.junit4.statement.UiThreadStatement
import com.pseandroid2.dailydata.MainActivity
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
 * testing: "Neues Projekt aus Projekttemplate erstellen", 7.1.3
 */
class GT713 {
    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    val projectName = "GT713"

    @Ignore("Templates do not exist")
    @ExperimentalCoroutinesApi
    @InternalCoroutinesApi
    @Test
    fun createProjectFromProjectTemplate() = runTest {
        // Create project from template
        composeRule.onNodeWithText("Project from Template").performClick()
        // TODO("Es existieren keine Templates")

        composeRule.onNodeWithText("Add Title").performTextInput(projectName)
        composeRule.onNodeWithText("Save").performClick()
        delay(3000)

        // Check if project is in the start screen
        UiThreadStatement.runOnUiThread {
            composeRule.activity.onBackPressed()
        }
        delay(2000)
        composeRule.onNodeWithText("projectName").assertExists()

    }
}