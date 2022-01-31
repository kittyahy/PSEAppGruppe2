package com.pseandroid2.dailydata.remoteDataSource.serverConnection.restAPI.fetchRequestController

import com.pseandroid2.dailydata.remoteDataSource.serverConnection.RESTAPI
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.ServerManager
import com.pseandroid2.dailydata.remoteDataSource.userManager.FirebaseManager
import com.pseandroid2.dailydata.remoteDataSource.userManager.FirebaseReturnOptions
import org.junit.AfterClass
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class FetchRequestControllerTests {
    private var restAPI: RESTAPI = RESTAPI()
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
    fun setup() {
        // Generate valid firebase authentication tokens
        Assert.assertEquals(FirebaseReturnOptions.SINGED_IN, fm.signInWithEmailAndPassword(email3, password3))
        authToken3 = fm.getToken()
        Assert.assertEquals(FirebaseReturnOptions.SINGED_IN, fm.signInWithEmailAndPassword(email2, password2))
        authToken2 = fm.getToken()
        Assert.assertEquals(FirebaseReturnOptions.SINGED_IN, fm.signInWithEmailAndPassword(email, password))
        authToken = fm.getToken()

        // Create new project
        projectID = restAPI.addProject(authToken, "project details")
        Assert.assertTrue(projectID > 0)
        // Adds third user to the project
        Assert.assertEquals("project details", restAPI.addUser(projectID, authToken3))

        // Save a delta to the server
        Assert.assertEquals(listOf(projectCommandToSend), serverManager.sendCommandsToServer(projectID, listOf(projectCommandToSend), authToken))
        setTeardown(restAPI, projectID, authToken, userID, userID2, userID3)
    }

    companion object Teardown{
        private var restAPI: RESTAPI? = null
        private var projectID: Long = -1
        private var authToken: String = ""
        private var userToRemove1: String = ""
        private var userToRemove2: String = ""
        private var userToRemove3: String = ""

        fun setTeardown(restapi: RESTAPI, projectID: Long, authToken: String, userToRemove1: String, userToRemove2: String, userToRemove3: String) {
            restAPI = restapi
            Teardown.projectID = projectID
            Teardown.authToken = authToken
            Teardown.userToRemove1 = userToRemove1
            Teardown.userToRemove2 = userToRemove2
            Teardown.userToRemove3 = userToRemove3
        }

        @AfterClass
        @JvmStatic fun teardown() {
            // Remove all users from project so that the project gets removed
            restAPI?.removeUser(userToRemove3, projectID, authToken)
            restAPI?.removeUser(userToRemove2, projectID, authToken)
            restAPI?.removeUser(userToRemove1, projectID, authToken)
        }
    }

    @Test
    fun demandOldData() {
        Assert.assertTrue(restAPI.demandOldData(projectID, "request information", authToken))
    }

    @Test
    fun getFetchRequests() {
        var requestsToSend = mutableListOf<String>("request information 1", "request information 2")
        Assert.assertTrue(restAPI.demandOldData(projectID, requestsToSend.elementAt(0), authToken))
        Assert.assertTrue(restAPI.demandOldData(projectID, requestsToSend.elementAt(1), authToken))

        val downloadedFetchRequests = restAPI.getFetchRequests(projectID, authToken2) // User 2 wants to receive the uploaded fetch requests
        Assert.assertNotEquals(0, downloadedFetchRequests.size)

        downloadedFetchRequests.forEach {
            requestsToSend.remove(it.requestInfo)
        }
        Assert.assertEquals(0, requestsToSend) // The send fetch requests from user1 were received by user2
    }

    // TODO: Test Ideas: 1. getFetchrequests from the same account who send them, 2.
    
}