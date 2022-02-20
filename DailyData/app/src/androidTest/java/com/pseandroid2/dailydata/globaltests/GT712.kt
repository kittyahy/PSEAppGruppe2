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
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test

/**
 * testing: "Leeres Projekt erstellen", 7.1.2
 */
class GT712 {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()


    @Ignore("Graph error, back to Overview is missing")
    @InternalCoroutinesApi
    @Test
    fun createEmptyProjectTest() {

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
        TODO("com.pseandroid2.dailydata.workingtest.ReproduceTest.addGraphTest")

        TODO("return to project overview")

        composeRule.onNodeWithText("Add new Project").assertExists()
        composeRule.onAllNodes(matcher = hasText("Kresse")).onFirst().assertExists()
    }

}