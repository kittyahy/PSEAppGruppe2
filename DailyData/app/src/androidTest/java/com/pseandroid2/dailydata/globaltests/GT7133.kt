package com.pseandroid2.dailydata.globaltests

import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.text.AnnotatedString
import com.pseandroid2.dailydata.Main
import com.pseandroid2.dailydata.MainActivity
import com.pseandroid2.dailydata.util.ui.UiEvent
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import java.lang.AssertionError

class GT7133 {
    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    private val projectName: String = "GT7133";

    @InternalCoroutinesApi
    @Before
    fun setup() {
        composeRule.setContent {
            Main()
        }
        GlobalTestsHelpingMethods.createTestProject(composeRule, projectName)
    }

    @Ignore("")
    /**
     * tests: "Link erstellen", 7.1.33
     */
    @InternalCoroutinesApi
    @Test
    fun timeToOpenProject() {
        composeRule.onNodeWithText(projectName).performClick()
        runBlocking { delay(1000) }
        composeRule.onNodeWithText("Settings").performClick()
        runBlocking { delay(1000) }
        TODO("Implementiere die Funktionalität: Offlineprojekt zu Onlinprojekt wechseln")
        composeRule.onNodeWithText("Create Online Project").performClick()
        runBlocking { delay(500) }
        composeRule.onNodeWithText("Create Link").performClick()
        runBlocking { delay(1000) }
        val clipboardManager = composeRule.activity.baseContext.getSystemService(Context.CLIPBOARD_SERVICE) as androidx.compose.ui.platform.ClipboardManager
        val clipboard = clipboardManager.getText() // gets the stored Link from Clipboard
        Assert.assertNotEquals(clipboard, null)
        Assert.assertTrue(clipboard!!.startsWith("https://dailydata.page.link/?link=https://www.dailydata.com/?projectid%3D"))
    }
}