package com.pseandroid2.dailydata.globaltests

import android.util.Log
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.pseandroid2.dailydata.Main
import com.pseandroid2.dailydata.MainActivity
import com.pseandroid2.dailydata.util.Consts.LOG_TAG
import kotlinx.coroutines.InternalCoroutinesApi
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test

/**
 * testing: "Ändere Projektnamen", 7.1.2
 */
class GT714 {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()


    @Ignore("back to overview is missing, needs a \"Kresse\" Project")
    @Test
    //TODO(braucht ein Projekt "Kresse")
    @InternalCoroutinesApi
    fun changeProjectName(){

        composeRule.onAllNodesWithText("Kresse").onFirst().performClick()
        composeRule.onNodeWithText("Settings").performClick()
        composeRule.onNodeWithText("Kresse").performTextInput("Neue Kresse")
        composeRule.onNodeWithText("Save").performClick()
        TODO("Back to overview")
        composeRule.onNodeWithText("Neue Kresse").assertExists()
    }


}