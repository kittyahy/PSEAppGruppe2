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
 * Tests: "Neuer Projektadministrator", 7.1.36
 */
class GT7136 {
    private val rds: RemoteDataSourceAPI = RemoteDataSourceAPI(
        UserAccount(FirebaseManager(null)), ServerManager(RESTAPI(URLs.testServer_BASE_URL))
    )

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    private val projectName: String = "GT7.1.36"


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
     * n = 1
     * This tests if the global test works in the rdsAPI
     */
    @ExperimentalCoroutinesApi
    @InternalCoroutinesApi
    @Test
    fun newProjectAdminRDS() = runTest {
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

        // User N1 is project admin
        Assert.assertEquals(TestUsers.userID[0], rds.getProjectAdmin(projectID))

        // Remove N1 from the project
        Assert.assertTrue(
            rds.signInUser(
                TestUsers.eMail[0],
                TestUsers.password[0],
                SignInTypes.EMAIL
            ).success
        )
        Assert.assertTrue(rds.removeUser(TestUsers.userID[0], projectID))

        // User N2 is project admin
        Assert.assertTrue(
            rds.signInUser(
                TestUsers.eMail[1],
                TestUsers.password[1],
                SignInTypes.EMAIL
            ).success
        )
        Assert.assertEquals(TestUsers.userID[1], rds.getProjectAdmin(projectID))
    }


    /**
     * n = 24
     * This tests if the global test works in the rdsAPI
     */
    @ExperimentalCoroutinesApi
    @InternalCoroutinesApi
    @Test
    fun newProjectAdminWithNEquals24RDS() = runTest {
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

        // Users N2-N25 join the project
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

        // User N1 is project admin
        Assert.assertEquals(TestUsers.userID[0], rds.getProjectAdmin(projectID))

        // Remove N1 from the project
        Assert.assertTrue(
            rds.signInUser(
                TestUsers.eMail[0],
                TestUsers.password[0],
                SignInTypes.EMAIL
            ).success
        )
        Assert.assertTrue(rds.removeUser(TestUsers.userID[0], projectID))

        // User N2 is project admin
        Assert.assertTrue(
            rds.signInUser(
                TestUsers.eMail[1],
                TestUsers.password[1],
                SignInTypes.EMAIL
            ).success
        )
        Assert.assertEquals(TestUsers.userID[1], rds.getProjectAdmin(projectID))
    }

    @Ignore("Not all for this test necessary features exists")
    @InternalCoroutinesApi
    @Test
    fun newProjectAdmin() {
        // TODO("Implementiere die Funktionalit??t: Nutzende k??nnen sich anmelden")
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
        // TODO("Implementiere die Funktionalit??t: Offlineprojekt zu Onlinprojekt wechseln")
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
        // TODO("Finde eine M??glichkeit die App zu schlie??en und den Link in dem clipboard zu ??ffnen")

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

        // Open the project
        composeRule.onNodeWithTag(Routes.PROJECT).performClick()
        runBlocking { delay(1000) }
        composeRule.onAllNodesWithText(projectName).onFirst().performClick()
        runBlocking { delay(1000) }
        composeRule.onNodeWithText("Settings").performClick()
        runBlocking { delay(1000) }

        // User N1 removes himself from the project
        composeRule.onNodeWithText(TestUsers.eMail[0]).performClick().onParent()
            .onChildAt(1) // Remove User1
        runBlocking { delay(500) }

        // Login user N2
        GlobalTestsHelpingMethods.loginUser(
            composeRule,
            TestUsers.eMail[1],
            TestUsers.password[1],
            true
        )

        // Open the project
        composeRule.onNodeWithTag(Routes.PROJECT).performClick()
        runBlocking { delay(1000) }
        composeRule.onAllNodesWithText(projectName).onFirst().performClick()
        runBlocking { delay(1000) }
        composeRule.onNodeWithText("Settings").performClick()
        runBlocking { delay(500) }

        // Check which member is the admin
        // TODO("Implementiere die Funktionalit??t: Projektadministrator anzeigen")
        composeRule.onNodeWithTag("Admin").assertTextEquals(TestUsers.eMail[1])
    }
}