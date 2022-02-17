package com.pseandroid2.dailydata.globaltests

import android.util.Log
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.printToString
import androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread
import com.pseandroid2.dailydata.Main
import com.pseandroid2.dailydata.MainActivity
import io.mockk.InternalPlatformDsl.toArray
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import java.lang.AssertionError
import java.lang.reflect.Modifier

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
        createTestProject(projectName)
        composeRule.onNodeWithText("Project").performClick()
    }

    //@Ignore("TODO: Lösche das Projekt nach dem Test, sodass es nicht mehrere Projekte mit dem gleichen Namen existieren, da sonst hier ein Error geworfen wird")
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

    private fun createTestProject(projectName: String) {
        if (composeRule.onAllNodesWithText(projectName).fetchSemanticsNodes().isNotEmpty()){
            return
        }

        composeRule.onNodeWithText(projectName).assertDoesNotExist()

        composeRule.onNodeWithText("Add new Project").performClick()

        composeRule.onNodeWithText("Add Title").performTextInput(projectName)

        composeRule.onNodeWithText("Add Description")
            .performTextInput("Projekt für einen globalen Test")

        composeRule.onNodeWithText("Change Wallpaper").performClick()
        composeRule.onNodeWithText("Green").performClick()

        composeRule.onNodeWithText("Add Table Column").performClick()
        composeRule.onNodeWithText("Name").performTextInput("Höhe")
        composeRule.onNodeWithText("Unit").performTextInput("cm")
        composeRule.onNodeWithText("OK").performClick()

        composeRule.onNodeWithText("Save").performClick()

        runBlocking {delay(2000)}
        runOnUiThread {
            composeRule.activity.onBackPressed()
        }
        runBlocking {delay(1000)}
    }
}