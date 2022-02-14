package com.pseandroid2.dailydata.remoteDataSource.serverConnection.restAPI.deltaController

import com.pseandroid2.dailydata.remoteDataSource.serverConnection.RESTAPI
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.ServerManager
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.URLs
import com.pseandroid2.dailydata.remoteDataSource.userManager.FirebaseManager
import com.pseandroid2.dailydata.remoteDataSource.userManager.FirebaseReturnOptions
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.AfterClass
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class DeltaControllerGetDeltaTests {
    private var restAPI: RESTAPI = RESTAPI(URLs.testServer_BASE_URL)
    private lateinit var authToken: String
    private lateinit var authToken2: String
    private lateinit var authToken3: String

    private val fm: FirebaseManager = FirebaseManager(null)
    private var email = "test@student.kit.edu"
    private var password = "PSEistsuper"
    private val userID = "4hpJh32YaAWrAYoVvo047q7Ey183"
    private val email2 = "pseFan@student.kit.edu"
    private val password2 = "mehrSpaßAlsBeiPSEGibtsNicht"
    private val userID2 = "a5sYrdnd1EX2TtkJAEGGMHqebUq2"
    private var email3 = "unermüdlicherStudent@student.kit.edu"
    private var password3 = "ohneKaffeeKeinPSE"
    private val userID3 = "CBVFbOwfHmTLAA2d7Q9wt6RXQyH2"

    private var projectID: Long = -1

    private var serverManager = ServerManager(restAPI)

    private val projectCommandToSend: String = "projectCommand"

    @Before
    fun setup() = runBlocking {
        Assert.assertTrue(restAPI.clearServer())

        // Generate valid firebase authentication tokens
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
        // Adds third user to the project
        Assert.assertEquals("project details", restAPI.addUser(projectID, authToken3))

        // Save a delta to the server
        Assert.assertEquals(
            listOf(projectCommandToSend),
            serverManager.sendCommandsToServer(projectID, listOf(projectCommandToSend), authToken)
        )
        setTeardown(restAPI, projectID, authToken, userID, userID2, userID3)
    }

    companion object Teardown {
        private var restAPI: RESTAPI? = null
        private var projectID: Long = -1
        private var authToken: String = ""
        private var userToRemove1: String = ""
        private var userToRemove2: String = ""
        private var userToRemove3: String = ""

        fun setTeardown(
            restapi: RESTAPI,
            projectID: Long,
            authToken: String,
            userToRemove1: String,
            userToRemove2: String,
            userToRemove3: String
        ) {
            restAPI = restapi
            Teardown.projectID = projectID
            Teardown.authToken = authToken
            Teardown.userToRemove1 = userToRemove1
            Teardown.userToRemove2 = userToRemove2
            Teardown.userToRemove3 = userToRemove3
        }

        @AfterClass
        @JvmStatic
        fun teardown() {
            runBlocking {
                // Remove all users from project so that the project gets removed
                restAPI?.removeUser(userToRemove3, projectID, authToken)
                restAPI?.removeUser(userToRemove2, projectID, authToken)
                restAPI?.removeUser(userToRemove1, projectID, authToken)
            }
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getDeltaFromServer() = runTest {
        val downloadedDeltas = restAPI.getDelta(projectID, authToken) as MutableList
        Assert.assertNotEquals(0, downloadedDeltas.size)

        var deltaFound = false
        downloadedDeltas.forEach {
            if (it.projectCommand == "projectCommand") {
                deltaFound = true
            }
        }

        Assert.assertTrue(deltaFound)
    }

    /* TODO: Implement this in the quality control phase
    @ExperimentalCoroutinesApi
    @Test
    fun getDeltaWhenNoProjectMember() = runTest {
        val downloadedDeltas = restAPI.getDelta(projectID, authToken2) as MutableList
        Assert.assertEquals(0, downloadedDeltas.size)
    } */

    @ExperimentalCoroutinesApi
    @Test
    fun getSameDeltaTwice() = runTest {
        // User 3 should try to download the same delta twice (and it should work both times)
        // Download the deltas for the first time
        var downloadedDeltas = restAPI.getDelta(projectID, authToken3) as MutableList
        Assert.assertNotEquals(0, downloadedDeltas.size)

        var deltaFound = false
        downloadedDeltas.forEach {
            if (it.projectCommand == "projectCommand") {
                deltaFound = true
            }
        }
        Assert.assertTrue(deltaFound)

        // Download deltas again
        downloadedDeltas = restAPI.getDelta(projectID, authToken3) as MutableList
        deltaFound = false
        downloadedDeltas.forEach {
            if (it.projectCommand == "projectCommand") {
                deltaFound = true
            }
        }
        Assert.assertTrue(deltaFound)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getRemoveTime() = runTest {
        Assert.assertNotEquals(-1, restAPI.getRemoveTime(authToken))
    }
}
