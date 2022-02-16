package com.pseandroid2.dailydata.globaltests

import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.pseandroid2.dailydata.Main
import com.pseandroid2.dailydata.MainActivity
import kotlinx.coroutines.InternalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test

class GT7147 {
    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @InternalCoroutinesApi
    @Before
    fun setup() {
        composeRule.setContent {
            Main()
        }
        createProject()
        composeRule.onNodeWithText("Project").performClick()
    }

    /**
     * tests: "Zeit zum Öffnen eines Projekts", 7.1.47
     */
    @Ignore
    @InternalCoroutinesApi
    @Test
    fun timeToOpenProject() {
        val maxTime = 1000;
        val startTime = System.currentTimeMillis()
        composeRule.onNodeWithText("Kresse").performClick()
        // TODO: Prüfe die Zeit, bis projekt geöffnet ist
        Assert.assertTrue((System.currentTimeMillis()-startTime) < maxTime)
    }

    private fun createProject() {
        composeRule.onNodeWithText("Add new Project").performClick()

        composeRule.onNodeWithText("Add Title").performTextInput("Kresse")

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

        composeRule.onNodeWithText("Add Graph").performClick()
        composeRule.onNodeWithText("Line Chart").performClick()

        composeRule.onNodeWithText("Save").performClick()
    }
}