package com.pseandroid2.dailydata.globaltests

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.pseandroid2.dailydata.MainActivity
import kotlinx.coroutines.InternalCoroutinesApi
import org.junit.Rule
import org.junit.Test

/**
 * Tests: "Projekterstellung öffnen",  7.1.1
 */
class GT711 {
    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @InternalCoroutinesApi
    @Test
    fun openProjectCreation() {
        composeRule.onNodeWithText("Add new Project").performClick()
        composeRule.onNodeWithTag("projectCreation").assertIsDisplayed()
    }
}