package com.pseandroid2.dailydata.globaltests

import android.content.Context
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
 * tests: " Mitglieder entfernen", 7.1.38
 */
class GT7138 {
    private val rds: RemoteDataSourceAPI = RemoteDataSourceAPI(
        UserAccount(FirebaseManager(null)), ServerManager(RESTAPI(URLs.testServer_BASE_URL))
    )

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    private val projectName: String = "GT7.1.38";


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


    /**
     * This tests if the global test works in the rdsAPI.
     */
    @ExperimentalCoroutinesApi
    @InternalCoroutinesApi
    @Test
    fun removeOneUserRDS() = runTest {
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

        // N1 removes N2
        Assert.assertTrue(
            rds.signInUser(
                TestUsers.eMail[0],
                TestUsers.password[0],
                SignInTypes.EMAIL
            ).success
        )
        Assert.assertTrue(rds.removeUser(TestUsers.userID[1], projectID))

        // Check that N2 is no project member
        projectParticipants = rds.getProjectParticipants(projectID) as MutableList
        Assert.assertFalse(projectParticipants.remove(TestUsers.userID[1]))
    }

    @ExperimentalCoroutinesApi
    /**
     * This tests if the global test works in the rdsAPI.
     * Tests if the test still works even when removing multiple users.
     */
    @InternalCoroutinesApi
    @Test
    fun removeAllUsersRDS() = runTest {
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

        // User Ni join the project
        for (i in 1..23) {
            Assert.assertTrue(
                rds.signInUser(
                    TestUsers.eMail[i],
                    TestUsers.password[i],
                    SignInTypes.EMAIL
                ).success
            )
            Assert.assertEquals(rds.joinProject(projectID), "projectDetails")
        }

        // Login User N1 (project admin)
        Assert.assertTrue(
            rds.signInUser(
                TestUsers.eMail[0],
                TestUsers.password[0],
                SignInTypes.EMAIL
            ).success
        )

        // Check if Ni is project member
        var projectParticipants = rds.getProjectParticipants(projectID) as MutableList
        for (i in 1..23) {
            Assert.assertTrue(projectParticipants.remove(TestUsers.userID[i]))
        }
        Assert.assertEquals(mutableListOf(TestUsers.userID[0]), projectParticipants)

        // N1 removes all Ni
        for (i in 1..23) {
            Assert.assertTrue(rds.removeUser(TestUsers.userID[i], projectID))
        }

        // Check if N1 is only project member
        projectParticipants = rds.getProjectParticipants(projectID) as MutableList
        Assert.assertEquals(listOf(TestUsers.userID[0]), projectParticipants)
    }

    @Ignore("Not all for this test necessary features exists")
    @InternalCoroutinesApi
    @Test
    fun removeUser() {
        // TODO("Implementiere die Funktionalität: Nutzende können sich anmelden")
        // Login user N1
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

        // Login user N2
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

        // Login user N1
        GlobalTestsHelpingMethods.loginUser(
            composeRule,
            TestUsers.eMail[0],
            TestUsers.password[0],
            true
        )

        // Open project
        composeRule.onNodeWithTag(Routes.PROJECT).performClick()
        runBlocking { delay(1000) }
        composeRule.onAllNodesWithText(projectName).onFirst().performClick()
        runBlocking { delay(1000) }
        composeRule.onNodeWithText("Settings").performClick()
        runBlocking { delay(500) }

        // Checks if user N2 is project member
        composeRule.onNodeWithText(TestUsers.eMail[1]).assertExists()
        runBlocking { delay(500) }

        // Remove user N2
        composeRule.onNodeWithText(TestUsers.eMail[1]).performClick().onParent()
            .onChildAt(1)
        composeRule.onNodeWithText(TestUsers.eMail[1]).assertDoesNotExist()
    }
}