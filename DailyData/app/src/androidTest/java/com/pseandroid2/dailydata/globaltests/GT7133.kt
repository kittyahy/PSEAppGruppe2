package com.pseandroid2.dailydata.globaltests

import android.content.Context
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.pseandroid2.dailydata.Main
import com.pseandroid2.dailydata.MainActivity
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test

/**
 * Tests: "Link erstellen", 7.1.33
 */
class GT7133 {
    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    private val projectName: String = "GT7.1.33";

    @InternalCoroutinesApi
    @Before
    fun setup() {
        composeRule.setContent {
            Main()
        }
        GlobalTestsHelpingMethods.createTestProject(composeRule, projectName)
    }

    @Ignore("Not all for this test necessary features exists")
    @InternalCoroutinesApi
    @Test
    fun createLink() {
        // Create and open a new project
        composeRule.onAllNodesWithText(projectName).onFirst().performClick()
        runBlocking { delay(1000) }
        composeRule.onNodeWithText("Settings").performClick()
        runBlocking { delay(1000) }

        // Get a join participants link
        // TODO("Implementiere die Funktionalität: Offlineprojekt zu Onlinprojekt wechseln")
        composeRule.onNodeWithText("Create Online Project").performClick()
        runBlocking { delay(500) }
        composeRule.onNodeWithText("Create Link").performClick()
        runBlocking { delay(1000) }
        val clipboardManager =
            composeRule.activity.baseContext.getSystemService(Context.CLIPBOARD_SERVICE) as androidx.compose.ui.platform.ClipboardManager
        val clipboard = clipboardManager.getText() // gets the stored Link from Clipboard

        // Check if the link is valid
        Assert.assertNotEquals(clipboard, null)
        Assert.assertTrue(clipboard!!.startsWith("https://dailydata.page.link/?link=https://www.dailydata.com/?projectid%3D"))
    }
}