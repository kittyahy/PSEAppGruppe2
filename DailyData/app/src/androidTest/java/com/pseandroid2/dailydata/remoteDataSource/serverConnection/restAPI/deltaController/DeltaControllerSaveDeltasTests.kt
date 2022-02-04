package com.pseandroid2.dailydata.remoteDataSource.serverConnection.restAPI.deltaController

import com.pseandroid2.dailydata.remoteDataSource.serverConnection.RESTAPI
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.ServerManager
import com.pseandroid2.dailydata.remoteDataSource.userManager.FirebaseManager
import com.pseandroid2.dailydata.remoteDataSource.userManager.FirebaseReturnOptions
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class DeltaControllerSaveDeltasTests {
    private var restAPI: RESTAPI = RESTAPI()
    private lateinit var authToken: String

    private var projectID: Long = -1

    private var serverManager = ServerManager(restAPI)

    private val commandLimit = 128 // number of commands a user can upload for a project in one day

    @Before
    fun setup() {
        // Generate valid firebase authentication token
        val fm = FirebaseManager(null)
        val email = "test@student.kit.edu"
        val password = "PSEistsuper"

        Assert.assertEquals(
            FirebaseReturnOptions.SINGED_IN,
            fm.signInWithEmailAndPassword(email, password)
        )
        authToken = fm.getToken()

        // Create new project
        projectID = restAPI.addProject(authToken, "project details")
        Assert.assertTrue(projectID > 0)
    }

    @Test
    fun saveDelta() {
        // Save a delta to the server
        Assert.assertEquals(
            listOf("projectCommand"),
            serverManager.sendCommandsToServer(projectID, listOf("projectCommand"), authToken)
        )
    }

    //TODO: We have currently a problem that the server can't handle too many uploaded deltas at a time.
    /*
    @Test
    fun saveTenDeltas() {
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

    @Test
    fun exceedCommandUploadLimit() {
        val projectID2 = restAPI.addProject(authToken, "new project details")
        Assert.assertTrue(projectID2 > 0)

        val projectCommands: MutableList<String> = mutableListOf()
        for (i in 1..commandLimit) {
            projectCommands.add("command$i")
        }
        val successfullySendCommands = serverManager.sendCommandsToServer(
            projectID2,
            projectCommands,
            authToken
        ) as MutableList
        projectCommands.forEach {
            Assert.assertTrue(successfullySendCommands.remove(it))
        }

        val projectCommand = listOf("exceeds the upload limit")
        Assert.assertEquals(projectCommand, projectCommand)
    }
    */
    //TODO Tests auskommentieren
}