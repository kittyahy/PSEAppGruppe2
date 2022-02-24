package com.pseandroid2.dailydata.remoteDataSource.serverConnection.restAPI.deltaController

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


class DeltaControllerSaveDeltasTests {
    private var restAPI: RESTAPI = RESTAPI(URLs.testServer_BASE_URL)
    private lateinit var authToken: String

    private var projectID: Long = -1

    private var serverManager = ServerManager(restAPI)

    private val commandLimit = 128 // number of commands a user can upload for a project in one day

    @Before
    fun setup() = runBlocking {
        Assert.assertTrue(restAPI.clearServer())
        // Generate valid firebase authentication token
        val fm = FirebaseManager(null)
        val email = "test@student.kit.edu"
        val password = "PSEistsuper"
        val uId = "4hpJh32YaAWrAYoVvo047q7Ey183"

        Assert.assertEquals(
            FirebaseReturnOptions.SINGED_IN,
            fm.signInWithEmailAndPassword(email, password)
        )
        authToken = fm.getToken()

        // Create new project
        projectID = restAPI.addProject(authToken, "project details")
        Assert.assertTrue(projectID > 0)

        setTeardown(restAPI, projectID, authToken, uId)
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
            userToRemove1: String
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
                restAPI?.clearServer()
            }
        }
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() = runTest {
        Teardown.restAPI?.clearServer()
        // Create new project
        projectID = restAPI.addProject(authToken, "project details")
        Assert.assertTrue(projectID > 0)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun saveDelta() = runTest {
        // Save a delta to the server
        Assert.assertEquals(
            listOf("projectCommand"),
            serverManager.sendCommandsToServer(projectID, listOf("projectCommand"), authToken)
        )
    }


    @ExperimentalCoroutinesApi
    @Test
    fun saveTenDeltas() = runTest {
        val projectCommands: MutableList<String> = mutableListOf()
        for (i in 1..10) {
            projectCommands.add("command$i")
        }
        val successfullySendCommands =
            serverManager.sendCommandsToServer(projectID, projectCommands, authToken) as MutableList
        projectCommands.forEach {
            Assert.assertTrue(successfullySendCommands.remove(it))
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun exceedCommandUploadLimit() = runTest {
        val projectCommands: MutableList<String> = mutableListOf()
        for (i in 1..commandLimit) {
            projectCommands.add("command$i")
        }
        val successfullySendCommands = serverManager.sendCommandsToServer(
            projectID,
            projectCommands,
            authToken
        ) as MutableList
        projectCommands.forEach {
            Assert.assertTrue(successfullySendCommands.remove(it))
        }

        val projectCommandExceedsLimit = listOf("exceeds the upload limit")
        Assert.assertEquals(
            0, serverManager.sendCommandsToServer(
                projectID,
                projectCommandExceedsLimit,
                authToken
            ).size
        )
    }
}
