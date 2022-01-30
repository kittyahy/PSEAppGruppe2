package com.pseandroid2.dailydata.remoteDataSource.serverConnection.restAPI.deltaController

import android.util.Log
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.RESTAPI
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.ServerManager
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverReturns.Delta
import com.pseandroid2.dailydata.remoteDataSource.userManager.FirebaseManager
import com.pseandroid2.dailydata.remoteDataSource.userManager.FirebaseReturnOptions
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import kotlin.collections.remove as remove

class DeltaControllerGetDeltaTests {
    private var restAPI: RESTAPI = RESTAPI()
    private lateinit var authToken: String
    private lateinit var authToken2: String
    private lateinit var authToken3: String

    private val fm: FirebaseManager = FirebaseManager(null)
    private var email = "test@student.kit.edu"
    private var password = "PSEistsuper"
    private val email2 = "pseFan@student.kit.edu"
    private val password2 = "mehrSpaßAlsBeiPSEGibtsNicht"
    private var email3 = "unermüdlicherStudent@student.kit.edu"
    private var password3 = "ohneKaffeeKeinPSE"

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
    }

    @Test
    fun getDeltaFromServer() {
        var downloadedDeltas = restAPI.getDelta(projectID, authToken) as MutableList<Delta>
        Assert.assertNotEquals(0, downloadedDeltas.size)

        var deltaFound = false
        downloadedDeltas.forEach {
            val command: String = it.projectCommand

            if (command == "projectCommand") {
                deltaFound = true
            }
        }

        Assert.assertTrue(deltaFound)
    }

    @Test
    fun getDeltaWhenNoProjectMember() {
        var downloadedDeltas = restAPI.getDelta(projectID, authToken2) as MutableList
        Assert.assertEquals(0, downloadedDeltas.size)
    }

    @Test
    fun getSameDeltaTwice() {
        // User 3 should try to download the same delta twice
        // Download the deltas for the first time
        var downloadedDeltas = restAPI.getDelta(projectID, authToken3) as MutableList
        Assert.assertNotEquals(0, downloadedDeltas.size)

        var deltaFound = false
        downloadedDeltas.forEach {
            if (it.projectCommand == "\"projectCommand\"") {
                deltaFound = true
            }
        }
        Assert.assertTrue(deltaFound)

        // Download deltas again
        downloadedDeltas = restAPI.getDelta(projectID, authToken3) as MutableList
        deltaFound = false
        downloadedDeltas.forEach {
            if (it.projectCommand == "\"projectCommand\"") {
                deltaFound = true
            }
        }
        Assert.assertTrue(deltaFound)
    }

    //TODO Tests auskommentieren
}