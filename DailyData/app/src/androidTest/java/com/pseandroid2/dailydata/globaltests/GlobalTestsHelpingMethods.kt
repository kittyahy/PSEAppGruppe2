package com.pseandroid2.dailydata.globaltests

import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.statement.UiThreadStatement
import com.pseandroid2.dailydata.MainActivity
import com.pseandroid2.dailydata.ui.navigation.Routes
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

object GlobalTestsHelpingMethods {
    /**
     * Creates a testproject with the projectName and returns to the project selection screen
     *
     */
    fun createTestProject(composeRule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>, projectName: String) {
        if (composeRule.onAllNodesWithText(projectName).fetchSemanticsNodes().isNotEmpty()){
            return
        }

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

        runBlocking { delay(2000) }
        UiThreadStatement.runOnUiThread {
            composeRule.activity.onBackPressed()
        }
        runBlocking { delay(1000) }
    }

    fun loginUser(composeRule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>, eMail: String, password: String, signOut: Boolean) {
        TODO("Implementiere die Funktionalität: Nutzende können sich anmelden")
        composeRule.onNodeWithTag(Routes.SERVER).performClick()
        runBlocking { delay(1000) }
        if(signOut) {
            composeRule.onNodeWithText("SignOut").performClick()
            runBlocking { delay(1000) }
        }
        composeRule.onNodeWithText("Email").performTextInput(TestUsers.eMail[1])
        composeRule.onNodeWithText("Password").performTextInput(TestUsers.password[1])
        composeRule.onNodeWithText("Login").performClick()
        runBlocking { delay(2000) }
        composeRule.onNodeWithTag(Routes.PROJECT).performClick()
        runBlocking { delay(1000) }
    }
}