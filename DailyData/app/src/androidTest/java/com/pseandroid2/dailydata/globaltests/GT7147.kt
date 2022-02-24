package com.pseandroid2.dailydata.globaltests

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.pseandroid2.dailydata.Main
import com.pseandroid2.dailydata.MainActivity
import kotlinx.coroutines.InternalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test

/**
 * tests: "Zeit zum Öffnen eines Projekts", 7.1.47
 */
class GT7147 {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    private val projectName: String = "GT7.1.47";

    @InternalCoroutinesApi
    @Before
    fun setup() {
        composeRule.setContent {
            Main()
        }
        GlobalTestsHelpingMethods.createTestProject(composeRule, projectName)
    }

    @Ignore("TODO: Delete the project after the test. Otherwise it's possible that there are too many projects on the project selection screen, so it's not possible for the tests see the right one.")
    @InternalCoroutinesApi
    @Test
    fun timeToOpenProject() {
        val maxTime = 1000;

        val startTime = composeRule.mainClock.currentTime
        composeRule.onAllNodesWithText(projectName).onFirst().performClick()
        composeRule.onNodeWithText("Input")
            .assertExists() // Means that the switch to the project screen succeeded
        Assert.assertTrue((composeRule.mainClock.currentTime - startTime) < maxTime)
    }
}