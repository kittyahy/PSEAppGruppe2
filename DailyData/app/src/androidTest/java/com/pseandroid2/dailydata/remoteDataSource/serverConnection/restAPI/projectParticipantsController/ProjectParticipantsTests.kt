package com.pseandroid2.dailydata.remoteDataSource.serverConnection.restAPI.projectParticipantsController

import com.pseandroid2.dailydata.remoteDataSource.serverConnection.RESTAPI
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.ServerManager
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.URLs
import com.pseandroid2.dailydata.remoteDataSource.userManager.FirebaseManager
import com.pseandroid2.dailydata.remoteDataSource.userManager.FirebaseReturnOptions
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.AfterClass
import org.junit.Assert
import org.junit.Before
import org.junit.Test


class ProjectParticipantsTests {
    private val restAPI: RESTAPI = RESTAPI(URLs.testServer_BASE_URL)
    private val serverManager: ServerManager = ServerManager(RESTAPI())
    private val fm = FirebaseManager(null)

    private val email = "test@student.kit.edu"
    private val password = "PSEistsuper"
    private val userID = "4hpJh32YaAWrAYoVvo047q7Ey183"
    private var authToken: String = ""

    private val email2 = "pseFan@student.kit.edu"
    private val password2 = "mehrSpaßAlsBeiPSEGibtsNicht"
    private val userID2 = "a5sYrdnd1EX2TtkJAEGGMHqebUq2"
    private var authToken2: String = ""

    private var email3 = "unermüdlicherStudent@student.kit.edu"
    private var password3 = "ohneKaffeeKeinPSE"
    private val userID3 = "CBVFbOwfHmTLAA2d7Q9wt6RXQyH2"
    private var authToken3: String = ""

    private var projectID: Long = -1

    @Before
    fun setup() = runBlocking {
        // Generate valid firebase authentication token
        Assert.assertEquals(
            FirebaseReturnOptions.SINGED_IN,
            fm.signInWithEmailAndPassword(email3, password3)
        )
        authToken3 = fm.getToken()
        Assert.assertEquals(
            FirebaseReturnOptions.SINGED_IN,
            fm.signInWithEmailAndPassword(email2, password2)
        )
        authToken2 = fm.getToken()
        Assert.assertEquals(
            FirebaseReturnOptions.SINGED_IN,
            fm.signInWithEmailAndPassword(email, password)
        )
        authToken = fm.getToken()

        // Create new project
        projectID = restAPI.addProject(authToken, "project details")
        Assert.assertTrue(projectID > 0)

        setTeardown(restAPI, projectID, authToken, userID)
    }

    companion object Teardown {
        private var restAPI: RESTAPI? = null
        private var projectID: Long = -1
        private var authToken: String = ""
        private var userToRemove1: String = ""

        fun setTeardown(
            restapi: RESTAPI,
            projectID: Long,
            authToken: String,
            userToRemove1: String,
        ) {
            restAPI = restapi
            Teardown.projectID = projectID
            Teardown.authToken = authToken
            Teardown.userToRemove1 = userToRemove1
        }

        @AfterClass
        @JvmStatic
        fun teardown() {
            runBlocking {
                // Remove all users from project so that the project gets removed
                restAPI?.removeUser(userToRemove1, projectID, authToken)
            }
        }
    }

    @ExperimentalCoroutinesApi
    @After
    fun clearUp() = runTest {
        restAPI.removeUser(userID2, projectID, authToken)
        restAPI.removeUser(userID3, projectID, authToken)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun addsAndRemovesUser() = runTest {
        Assert.assertEquals(
            "project details",
            restAPI.addUser(projectID, authToken2)
        ) // Adds the second user to the project

        Assert.assertTrue(
            restAPI.removeUser(
                userID2,
                projectID,
                authToken
            )
        ) // First user deletes second user
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getParticipants() = runTest {
        var participants = restAPI.getProjectParticipants(authToken, projectID) as MutableList
        Assert.assertEquals(1, participants.size)
        Assert.assertEquals(userID, participants.elementAt(0))

        // Add second user
        Assert.assertEquals("project details", restAPI.addUser(projectID, authToken2))
        participants = restAPI.getProjectParticipants(authToken, projectID) as MutableList
        Assert.assertEquals(2, participants.size)
        Assert.assertTrue(participants.remove(userID))
        Assert.assertTrue(participants.remove(userID2))

        // Add third user
        Assert.assertEquals("project details", restAPI.addUser(projectID, authToken3))
        participants = restAPI.getProjectParticipants(authToken, projectID) as MutableList
        Assert.assertEquals(3, participants.size)
        Assert.assertTrue(participants.remove(userID))
        Assert.assertTrue(participants.remove(userID2))
        Assert.assertTrue(participants.remove(userID3))

        // Remove user
        Assert.assertTrue(restAPI.removeUser(userID3, projectID, authToken))
        participants = restAPI.getProjectParticipants(authToken, projectID) as MutableList
        Assert.assertEquals(2, participants.size)
        Assert.assertFalse(participants.remove(userID3))

        // Remove user
        Assert.assertTrue(restAPI.removeUser(userID2, projectID, authToken))
        participants = restAPI.getProjectParticipants(authToken, projectID) as MutableList
        Assert.assertEquals(1, participants.size)
        Assert.assertTrue(participants.remove(userID))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun isProjectParticipant() = runTest {
        Assert.assertTrue(serverManager.isProjectParticipant(authToken, projectID, userID))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun isNoProjectParticipant() = runTest {
        Assert.assertFalse(serverManager.isProjectParticipant(authToken, projectID, userID2))
    }

    /* TODO: Implement this in quality control phase
    @ExperimentalCoroutinesApi
    @Test
    fun getParticipantsFromNotExistingProject() = runTest {
        restAPI.getProjectParticipants(authToken, -1)
        Assert.assertNotEquals(mutableListOf<List<String>>(), restAPI.getProjectParticipants(authToken, -1))
    }
    */

    @ExperimentalCoroutinesApi
    @Test
    fun getProjectAdmin() = runTest {
        Assert.assertEquals(userID, restAPI.getProjectAdmin(authToken, projectID))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun changeProjectAdmin() = runTest {
        Assert.assertEquals(userID, restAPI.getProjectAdmin(authToken, projectID))

        // Add new user and remove admin
        Assert.assertEquals("project details", restAPI.addUser(projectID, authToken2))
        Assert.assertTrue(restAPI.removeUser(userID, projectID, authToken))
        Assert.assertEquals(userID2, restAPI.getProjectAdmin(authToken2, projectID))

        // change admin back to userID
        Assert.assertEquals("project details", restAPI.addUser(projectID, authToken))
        Assert.assertTrue(restAPI.removeUser(userID2, projectID, authToken2))

        Assert.assertEquals(userID, restAPI.getProjectAdmin(authToken, projectID))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun changeProjectAdminToUserWhoIsSingedInTheLongest() = runTest {
        Assert.assertEquals("project details", restAPI.addUser(projectID, authToken2))
        Assert.assertEquals("project details", restAPI.addUser(projectID, authToken3))
        Assert.assertTrue(restAPI.removeUser(userID, projectID, authToken))
        Assert.assertEquals(userID2, restAPI.getProjectAdmin(authToken2, projectID))

        Assert.assertEquals("project details", restAPI.addUser(projectID, authToken))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun leaveProject() = runTest {
        Assert.assertEquals("project details", restAPI.addUser(projectID, authToken2))
        Assert.assertTrue(restAPI.removeUser(userID2, projectID, authToken2))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun deleteLastMemberOfProject() = runTest {
        // Create new project
        val projectID2 = restAPI.addProject(authToken, "project details")
        Assert.assertTrue(projectID2 > 0)

        // Remove the last project member (the admin removes himself)
        Assert.assertTrue(restAPI.removeUser(userID, projectID2, authToken))
    }

    /* TODO: Implement this in quality control phase
    @ExperimentalCoroutinesApi
    @Test
    fun userIsNoProjectMember() = runTest {
        Assert.assertNotEquals(mutableListOf<List<String>>(), restAPI.getProjectParticipants(authToken, projectID))
        Assert.assertNotEquals("", restAPI.getProjectAdmin(authToken, projectID))
    }
    */

    @ExperimentalCoroutinesApi
    @Test
    fun removeUserWhileNotBeingAdmin() = runTest {
        Assert.assertEquals("project details", restAPI.addUser(projectID, authToken2))
        Assert.assertEquals("project details", restAPI.addUser(projectID, authToken3))

        Assert.assertFalse(restAPI.removeUser(authToken2, projectID, authToken3))
    }
}
