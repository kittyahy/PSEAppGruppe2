package com.pseandroid2.dailydata.globaltests

import androidx.compose.ui.test.junit4.createAndroidComposeRule
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

class GT7147 {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    private val projectName: String = "GT7147";

    @InternalCoroutinesApi
    @Before
    fun setup() {
        composeRule.setContent {
            Main()
        }
        GlobalTestsHelpingMethods.createTestProject(composeRule, projectName)
    }

    @Ignore("TODO: Lösche das Projekt nach dem Test, sodass es nicht mehrere Projekte mit dem gleichen Namen existieren, da sonst hier ein Error geworfen wird")
    /**
     * tests: "Zeit zum Öffnen eines Projekts", 7.1.47
     */
    @InternalCoroutinesApi
    @Test
    fun timeToOpenProject() {
        val maxTime = 1000;

        val startTime = composeRule.mainClock.currentTime
        composeRule.onNodeWithText(projectName).performClick()
        composeRule.onNodeWithText("Input").assertExists() // Means that the switch to the project screen succeeded
        Assert.assertTrue((composeRule.mainClock.currentTime-startTime) < maxTime)
    }
}