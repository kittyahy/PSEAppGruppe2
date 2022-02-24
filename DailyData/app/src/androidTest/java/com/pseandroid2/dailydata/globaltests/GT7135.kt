package com.pseandroid2.dailydata.globaltests

import android.content.Context
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
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
import java.util.regex.Matcher

class GT7135 {
    val rds: RemoteDataSourceAPI = RemoteDataSourceAPI(
        UserAccount(FirebaseManager(null)), ServerManager(RESTAPI(URLs.testServer_BASE_URL))
    )

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    private val projectName: String = "GT7135";


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

    @After
    fun teardown() = runTest {
        Assert.assertTrue(RESTAPI(URLs.testServer_BASE_URL).clearServer())
    }

    @ExperimentalCoroutinesApi
    /**
     * tests: "Projektadministrator, Projektmitglied", 7.1.35
     * This tests if the global test works in the rdsAPI
     */
    @InternalCoroutinesApi
    @Test
    fun joinProjectRDS() = runTest {
        var projectID: Long = -1

        // User N1 creates the online project
        Assert.assertTrue(rds.signInUser(TestUsers.eMail[0], TestUsers.password[0], SignInTypes.EMAIL).success)
        projectID = rds.createNewOnlineProject("projectDetails")

        // User N2 joins a project
        Assert.assertTrue(rds.signInUser(TestUsers.eMail[1], TestUsers.password[1], SignInTypes.EMAIL).success)
        Assert.assertEquals(rds.joinProject(projectID), "projectDetails")

        // User N1 and N2 are project member
        val projectParticipants: MutableList<String> = rds.getProjectParticipants(projectID) as MutableList<String>
        Assert.assertEquals(2, projectParticipants.size)
        Assert.assertTrue(projectParticipants.remove(TestUsers.userID[0]))
        Assert.assertTrue(projectParticipants.remove(TestUsers.userID[1]))
        Assert.assertEquals(0, projectParticipants.size) // Which means that both project participants were N1 and N2

        // User N1 is project admin
        Assert.assertEquals(TestUsers.userID[0], rds.getProjectAdmin(projectID))
    }

    @Ignore("Not all for this test necessary features exists")
    /**
     * tests: "Projektadministrator, Projektmitglied", 7.1.35
     */
    @InternalCoroutinesApi
    @Test
    fun joinProject() {
        TODO("Implementiere die Funktionalität: Melde dich in der App mit einem Account an")

        // Login User 1
        composeRule.onNodeWithText(projectName).performClick()
        composeRule.onNodeWithTag(Routes.SERVER).performClick()
        runBlocking { delay(1000) }
        composeRule.onNodeWithText("Email").performTextInput(TestUsers.eMail[0])
        composeRule.onNodeWithText("Password").performTextInput(TestUsers.password[0])
        composeRule.onNodeWithText("Login").performClick()
        runBlocking { delay(2000) }

        // Create Project
        composeRule.onNodeWithTag(Routes.PROJECT).performClick()
        runBlocking { delay(1000) }
        TODO("Implementiere die Funktionalität: Offlineprojekt zu Onlinprojekt wechseln")
        composeRule.onNodeWithText("Create Online Project").performClick()
        runBlocking { delay(500) }
        composeRule.onNodeWithText("Create Link").performClick()
        runBlocking { delay(1000) }
        val clipboardManager = composeRule.activity.baseContext.getSystemService(Context.CLIPBOARD_SERVICE) as androidx.compose.ui.platform.ClipboardManager

        // Login User 2
        composeRule.onNodeWithText(projectName).performClick()
        composeRule.onNodeWithTag(Routes.SERVER).performClick()
        runBlocking { delay(1000) }
        composeRule.onNodeWithText("Email").performTextInput(TestUsers.eMail[1])
        composeRule.onNodeWithText("Password").performTextInput(TestUsers.password[1])
        composeRule.onNodeWithText("Login").performClick()
        runBlocking { delay(2000) }

        // Close app
        TODO("Finde eine Möglichkeit die App zu schließen und den Link in dem clipboard zu öffnen")

        // Join the Project
        composeRule.onNodeWithText("Join Project").performClick()

        // Open the project
        composeRule.onNodeWithTag(Routes.SERVER).performClick()
        runBlocking { delay(1000) }
        composeRule.onNodeWithText(projectName).performClick()
        runBlocking { delay(1000) }
        composeRule.onNodeWithText("Input").assertExists() // Means that the switch to the project screen succeeded

        // Check who the project admin is
        TODO("Implementiere die Funktionalität: Projektadministrator anzeigen")
    }
}