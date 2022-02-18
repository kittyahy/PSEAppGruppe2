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

/**
 * testing: "Projektbenachrichtigung hinzufügen", 7.1.8
 */
class GT7118 {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()


    @Ignore("Save does not work, needs a Kresse project")
    @InternalCoroutinesApi
    @Test
    fun addNotificationTest(){
        composeRule.onAllNodesWithText("Kresse").onFirst().performClick()
        composeRule.onNodeWithText("Settings").performClick()
        composeRule.onNodeWithText("Add Notification").performClick()
        composeRule.onNodeWithText("Name").performTextInput("Neue Notification")
        composeRule.onNodeWithText("OK").performClick()
        composeRule.onNodeWithText("Save").performClick()
        TODO("Save button does not work")
        runBlocking {
            delay(3000)
        }
        runBlocking {
            launch(Dispatchers.Main) {
                composeRule.activity.onBackPressed()
            }
        }
        runBlocking {
            delay(2000)
        }
        composeRule.onAllNodesWithText("Kresse").onFirst().performClick()
        composeRule.onNodeWithText("Settings").performClick()
        composeRule.onNodeWithTag("DeleteTime").assertExists()
    }
}