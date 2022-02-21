package com.pseandroid2.dailydata.globaltests

import android.util.Log
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
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

/**
 * testing: "Neues Projekt aus Projekttemplate erstellen", 7.1.3
 */
class GT713 {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()


    @Ignore("Templates do not exist")
    @InternalCoroutinesApi
    @Test
    fun createProjectFromProjectTemplate(){
        composeRule.onNodeWithText("Project from Template").performClick()
        TODO("If anything need to be in the template filled, fill it")
        TODO("Save the template")

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

        composeRule.onNodeWithText("TODO: Project name").assertExists()

    }
}