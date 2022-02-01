package com.pseandroid2.dailydata.remoteDataSource.serverConnection.restAPI.deltaController

import com.pseandroid2.dailydata.remoteDataSource.serverConnection.RESTAPI
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.ServerManager
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverReturns.FetchRequest
import com.pseandroid2.dailydata.remoteDataSource.userManager.FirebaseManager
import com.pseandroid2.dailydata.remoteDataSource.userManager.FirebaseReturnOptions
import org.junit.AfterClass
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime

class ProvideOldDataTests {
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
        Assert.assertEquals("project details", restAPI.addUser(projectID, authToken2))

        Assert.assertTrue(restAPI.demandOldData(projectID, "request information", authToken))

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
    fun provideOldData() {
        var downloadedFetchRequests = restAPI.getFetchRequests(projectID, authToken2) as MutableList<FetchRequest>

        var fetchRequestToAnswer: FetchRequest? = null
        downloadedFetchRequests.forEach {
            if (it.requestInfo == "request information") {
                fetchRequestToAnswer = it
            }
        }
        Assert.assertNotEquals(null, fetchRequestToAnswer)

        Assert.assertTrue(restAPI.provideOldData("project Command",
            fetchRequestToAnswer!!.user, LocalDateTime.of(0, 1, 1, 1, 1),
            userID, projectID, true, authToken2))
    }
}