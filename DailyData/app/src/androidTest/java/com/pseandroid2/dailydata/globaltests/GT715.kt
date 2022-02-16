package com.pseandroid2.dailydata.globaltests

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.pseandroid2.dailydata.MainActivity
import kotlinx.coroutines.InternalCoroutinesApi
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test


/**
 * testing: "Ändere Projektnamen", 7.1.2
 */
class GT715 {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()
    
    /**
     * [Kresse-project] [com.pseandroid2.dailydata.workingtest.ReproduceTest.CreateProjectTest]
     */
    @Ignore("back to overview is missing, needs a \"Kresse\" Project")
    @Test
    //TODO(needs a project "Kresse")
    @InternalCoroutinesApi

    fun changeProjectDescription() {
        composeRule.onAllNodesWithText("Kresse").onFirst().performClick()
        composeRule.onNodeWithText("Settings").performClick()
        composeRule.onNodeWithText("Add Description").performTextInput("Das ist meine Kresse")
        composeRule.onNodeWithText("Save").performClick()
        TODO("Back to overview")
        composeRule.onNodeWithText("Neue Kresse").assertExists()
    }


}