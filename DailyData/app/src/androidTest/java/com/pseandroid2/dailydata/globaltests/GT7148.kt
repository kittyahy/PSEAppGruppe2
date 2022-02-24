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

class GT7148 {
    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    private val projectName: String = "GT7147";

    @Test
    fun timeToOpenProject() {
        //create project
        //mock data
        //go to project
        //measure time

    }
}