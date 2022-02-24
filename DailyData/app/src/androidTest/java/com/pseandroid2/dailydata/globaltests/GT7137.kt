package com.pseandroid2.dailydata.globaltests

import android.content.Context
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onParent
import androidx.compose.ui.test.performClick
import com.pseandroid2.dailydata.Main
import com.pseandroid2.dailydata.MainActivity
import com.pseandroid2.dailydata.remoteDataSource.RemoteDataSourceAPI
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.RESTAPI
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.ServerManager
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.URLs
import com.pseandroid2.dailydata.remoteDataSource.userManager.FirebaseManager
import com.pseandroid2.dailydata.remoteDataSource.userManager.SignInTypes
import com.pseandroid2.dailydata.remoteDataSource.userManager.UserAccount
import com.pseandroid2.dailydata.ui.navigation.Routes
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test

/**
 * Tests: "Aus Projekt austreten", 7.1.37
 */
class GT7137 {
    private val rds: RemoteDataSourceAPI = RemoteDataSourceAPI(
        UserAccount(FirebaseManager(null)), ServerManager(RESTAPI(URLs.testServer_BASE_URL))
    )

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    private val projectName: String = "GT7.1.37";


    @ExperimentalCoroutinesApi
    @InternalCoroutinesApi
    @Before
    fun setup() = runTest {
        Assert.assertTrue(RESTAPI(URLs.testServer_BASE_URL).clearServer())

        composeRule.setContent {
            Main()
        }
        GlobalTestsHelpingMethods.createTestProject(composeRule, projectName)
    }

    @ExperimentalCoroutinesApi
    @After
    fun teardown() = runTest {
        Assert.assertTrue(RESTAPI(URLs.testServer_BASE_URL).clearServer())
    }

    @ExperimentalCoroutinesApi
    /**
     * This tests if the global test works in the rdsAPI
     */
    @InternalCoroutinesApi
    @Test
    fun leaveProjectRDS() = runTest {
        var projectID: Long = -1

        // User N1 creates the online project
        Assert.assertTrue(
            rds.signInUser(
                TestUsers.eMail[0],
                TestUsers.password[0],
                SignInTypes.EMAIL
            ).success
        )
        projectID = rds.createNewOnlineProject("projectDetails")

        // User N2 joins the project
        Assert.assertTrue(
            rds.signInUser(
                TestUsers.eMail[1],
                TestUsers.password[1],
                SignInTypes.EMAIL
            ).success
        )
        Assert.assertEquals(rds.joinProject(projectID), "projectDetails")

        // Check if N2 is project member
        var projectParticipants = rds.getProjectParticipants(projectID) as MutableList
        Assert.assertTrue(projectParticipants.remove(TestUsers.userID[1]))

        // N2 leaves project
        Assert.assertTrue(rds.removeUser(TestUsers.userID[1], projectID))

        // Check that N2 is no project member
        Assert.assertTrue(
            rds.signInUser(
                TestUsers.eMail[0],
                TestUsers.password[0],
                SignInTypes.EMAIL
            ).success
        )
        projectParticipants = rds.getProjectParticipants(projectID) as MutableList
        Assert.assertFalse(projectParticipants.remove(TestUsers.userID[1]))
    }


    @Ignore("Not all for this test necessary features exists")
    @InternalCoroutinesApi
    @Test
    fun leaveProject() {
        // TODO("Implementiere die Funktionalität: Nutzende können sich anmelden")
        // Login user N0
        GlobalTestsHelpingMethods.loginUser(
            composeRule,
            TestUsers.eMail[0],
            TestUsers.password[0],
            false
        )

        // Create a project
        composeRule.onNodeWithTag(Routes.PROJECT).performClick()
        runBlocking { delay(1000) }
        composeRule.onAllNodesWithText(projectName).onFirst().performClick()
        runBlocking { delay(1000) }

        // Change offline to online project
        // TODO("Implementiere die Funktionalität: Offlineprojekt zu Onlinprojekt wechseln")
        composeRule.onNodeWithText("Create Online Project").performClick()
        runBlocking { delay(500) }
        composeRule.onNodeWithText("Create Link").performClick()
        runBlocking { delay(1000) }
        val clipboardManager =
            composeRule.activity.baseContext.getSystemService(Context.CLIPBOARD_SERVICE) as androidx.compose.ui.platform.ClipboardManager

        // Login user N1 (The user who will leave the project)
        GlobalTestsHelpingMethods.loginUser(
            composeRule,
            TestUsers.eMail[1],
            TestUsers.password[1],
            true
        )

        // Close the app and start it with the join project link
        // TODO("Finde eine Möglichkeit die App zu schließen und sie mit dem Link im clipboard zu öffnen")

        // Join the project
        composeRule.onNodeWithText("Join Project").performClick()
        runBlocking { delay(1000) }

        // Open the project
        composeRule.onNodeWithTag(Routes.PROJECT).performClick()
        runBlocking { delay(1000) }
        composeRule.onAllNodesWithText(projectName).onFirst().performClick()
        runBlocking { delay(1000) }
        composeRule.onNodeWithText("Settings").performClick()
        runBlocking { delay(500) }

        // Check if the project is now an online project
        // TODO("Implementiere die Funktionalität: Prüfe, ob man sich in einem Onlineproject befindet")
        composeRule.onNodeWithTag("ProjectType").assertTextEquals("Online project")

        // User N1 leaves the project
        composeRule.onNodeWithTag(Routes.PROJECT).performClick()
        runBlocking { delay(1000) }
        composeRule.onNodeWithText(projectName).performClick()
        runBlocking { delay(1000) }
        composeRule.onNodeWithText("Settings").performClick()
        runBlocking { delay(1000) }
        composeRule.onNodeWithText(TestUsers.eMail[1]).performClick().onParent()
            .onChildAt(1) // Remove User2
        runBlocking { delay(500) }

        // Open the project
        composeRule.onNodeWithTag(Routes.PROJECT).performClick()
        runBlocking { delay(1000) }
        composeRule.onAllNodesWithText(projectName).onFirst().performClick()
        runBlocking { delay(1000) }
        composeRule.onNodeWithText("Settings").performClick()
        runBlocking { delay(500) }

        // Check if the project is now an offline project
        // TODO("Implementiere die Funktionalität: Prüfe, ob man sich in einem Onlineproject befindet")
        composeRule.onNodeWithTag("ProjectType").assertTextEquals("Offline project")
    }
}