package com.pseandroid2.dailydata.globaltests

import android.util.Log
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.pseandroid2.dailydata.MainActivity
import com.pseandroid2.dailydata.util.Consts
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import java.time.LocalTime


/**
 * testing: "Wallpaper ändern", 7.1.8
 */
class GT718 {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    /**
     * [Kresse-project] [com.pseandroid2.dailydata.globaltests.DefaultProject.createProjectTest]
     */
    @Ignore("needs a \"Kresse\" Project, save does not work, background check is missing")
    @Test
    @InternalCoroutinesApi

    fun changeNotification() {

        composeRule.onAllNodesWithText("Kresse").onFirst().performClick()
        composeRule.onNodeWithText("Settings").performClick()
        composeRule.onNodeWithText("Change Wallpaper").performClick()
        composeRule.onNodeWithText("Blue").performClick()
        composeRule.onNodeWithText("Save").performClick()
        TODO("Save does not work yet")
        runBlocking {
            launch(Dispatchers.Main) {
                composeRule.activity.onBackPressed()
                Log.d(Consts.LOG_TAG, "Hit the Back Button")
            }
        }
        runBlocking {
            delay(2000)
        }
        composeRule.onAllNodesWithText("Kresse").onFirst().performClick()
        composeRule.onNodeWithText("Settings").performClick()
        composeRule.onNodeWithTag("WallpaperColor").assertIsDisplayed()
        TODO("find out how to check the background")
    }
}

