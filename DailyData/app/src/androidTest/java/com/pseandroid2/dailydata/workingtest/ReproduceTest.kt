package com.pseandroid2.dailydata.workingtest

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


class ReproduceTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @InternalCoroutinesApi
    @Test
    fun CreateProjectTest() {
        composeRule.setContent {
            Main()
        }
        composeRule.onNodeWithText("Add new Project").performClick()
        composeRule.onNodeWithText("Add Title").performTextInput("Kresse")
        composeRule.onNodeWithText("Add Table Column").performClick()
        composeRule.onNodeWithText("Name").performTextInput("Höhe")
        composeRule.onNodeWithText("Unit").performTextInput("cm")
        composeRule.onNodeWithText("OK").performClick()
        composeRule.onNodeWithText("Button").performClick()
        composeRule.onNodeWithText("Name").performTextInput("1cm erhöhen")
        composeRule.onNodeWithText("Unit").performTextInput("1")
        composeRule.onNodeWithText("OK").performClick()
        composeRule.onNodeWithText("Save").performClick()
    }

    @Ignore
    //@assert(needs a project with name Kresse)
    @InternalCoroutinesApi
    @Test
    fun addButtonTest() {
        composeRule.setContent {
            Main()
        }
        composeRule.onAllNodes(matcher = hasText("Kresse")).onFirst().performClick()
        composeRule.onNodeWithText("Add").assertExists()
        composeRule.onNodeWithText("Add").performClick()
    }
}