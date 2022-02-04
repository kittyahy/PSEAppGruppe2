package com.pseandroid2.dailydata.remoteDataSource.serverConnection.restAPI.deltaController

import com.pseandroid2.dailydata.remoteDataSource.serverConnection.RESTAPI
import com.pseandroid2.dailydata.remoteDataSource.userManager.FirebaseManager
import com.pseandroid2.dailydata.remoteDataSource.userManager.FirebaseReturnOptions
import org.junit.AfterClass
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime

/*
class ProvideOldDataTests {
    private var restAPI: RESTAPI = RESTAPI()
    private lateinit var authToken: String
    private lateinit var authToken2: String

    private val fm: FirebaseManager = FirebaseManager(null)
    private var email = "test@student.kit.edu"
    private var password = "PSEistsuper"
    private val userID = "4hpJh32YaAWrAYoVvo047q7Ey183"
    private val email2 = "pseFan@student.kit.edu"
    private val password2 = "mehrSpaßAlsBeiPSEGibtsNicht"
    private val userID2 = "a5sYrdnd1EX2TtkJAEGGMHqebUq2"
    private var projectID: Long = -1

    @Before
    fun setup() {
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
        Assert.assertEquals("project details", restAPI.addUser(projectID, authToken2))

        Assert.assertTrue(restAPI.demandOldData(projectID, "request information", authToken))

        setTeardown(restAPI, projectID, authToken, userID, userID2)
    }

    companion object Teardown {
        private var restAPI: RESTAPI? = null
        private var projectID: Long = -1
        private var authToken: String = ""
        private var userToRemove1: String = ""
        private var userToRemove2: String = ""

        fun setTeardown(
            restapi: RESTAPI,
            projectID: Long,
            authToken: String,
            userToRemove1: String,
            userToRemove2: String
        ) {
            restAPI = restapi
            Teardown.projectID = projectID
            Teardown.authToken = authToken
            Teardown.userToRemove1 = userToRemove1
            Teardown.userToRemove2 = userToRemove2
        }

        @AfterClass
        @JvmStatic
        fun teardown() {
            // Remove all users from project so that the project gets removed
            restAPI?.removeUser(userToRemove2, projectID, authToken)
            restAPI?.removeUser(userToRemove1, projectID, authToken)
        }
    }

    @Test
    fun provideOldData() {
        Assert.assertTrue(
            restAPI.provideOldData(
                projectCommand = "project Command",
                forUser = userID,
                initialAdded = LocalDateTime.now(),
                initialAddedBy = userID,
                projectID = projectID,
                wasAdmin = false,
                authToken = authToken2
            )
        )
    }
}*/
