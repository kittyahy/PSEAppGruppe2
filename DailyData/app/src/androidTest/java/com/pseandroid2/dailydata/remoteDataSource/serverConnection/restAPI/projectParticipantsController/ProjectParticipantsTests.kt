package com.pseandroid2.dailydata.remoteDataSource.serverConnection.restAPI.projectParticipantsController

import com.pseandroid2.dailydata.remoteDataSource.serverConnection.RESTAPI
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.ServerManager
import com.pseandroid2.dailydata.remoteDataSource.userManager.FirebaseManager
import com.pseandroid2.dailydata.remoteDataSource.userManager.FirebaseReturnOptions
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.http.GET

/*
class ProjectParticipantsTests {

    private val restAPI: RESTAPI = RESTAPI()
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
    fun setup() {
        // Generate valid firebase authentication token
        Assert.assertEquals(FirebaseReturnOptions.SINGED_IN, fm.signInWithEmailAndPassword(email3, password3))
        authToken3 = fm.getToken()
        Assert.assertEquals(FirebaseReturnOptions.SINGED_IN, fm.signInWithEmailAndPassword(email2, password2))
        authToken2 = fm.getToken()
        Assert.assertEquals(FirebaseReturnOptions.SINGED_IN, fm.signInWithEmailAndPassword(email, password))
        authToken = fm.getToken()

        // Create new project
        projectID = restAPI.addProject(authToken, "project details")
        Assert.assertTrue(projectID > 0)
    }

    @After
    fun clearUp() {
        restAPI.removeUser(userID2, projectID, authToken)
        restAPI.removeUser(userID3, projectID, authToken)
    }

    @Test
    fun addsAndRemovesUser() {
        Assert.assertEquals("project details", restAPI.addUser(projectID, authToken2)) // Adds the second user to the project

        Assert.assertTrue(restAPI.removeUser(userID2, projectID, authToken)) // First user deletes second user
    }

    @Test
    fun getParticipants() {
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

    @Test
    fun isProjectParticipant() {
        Assert.assertTrue(serverManager.isProjectParticipant(authToken, projectID, userID))
    }

    @Test
    fun isNoProjectParticipant() {
        Assert.assertFalse(serverManager.isProjectParticipant(authToken, projectID, userID2))
    }

    */
/* TODO: Implement this in quality control phase
    @Test
    fun getParticipantsFromNotExistingProject() {
        restAPI.getProjectParticipants(authToken, -1)
        Assert.assertNotEquals(mutableListOf<List<String>>(), restAPI.getProjectParticipants(authToken, -1))
    }
    *//*


    @Test
    fun getProjectAdmin() {
        Assert.assertEquals(userID, restAPI.getProjectAdmin(authToken, projectID))
    }

    @Test
    fun changeProjectAdmin() {
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

    @Test
    fun changeProjectAdminToUserWhoIsSingedInTheLongest() {
        Assert.assertEquals("project details", restAPI.addUser(projectID, authToken2))
        Assert.assertEquals("project details", restAPI.addUser(projectID, authToken3))
        Assert.assertTrue(restAPI.removeUser(userID, projectID, authToken))
        Assert.assertEquals(userID2, restAPI.getProjectAdmin(authToken2, projectID))

        Assert.assertEquals("project details", restAPI.addUser(projectID, authToken))
    }
    @Test
    fun leaveProject() {
        Assert.assertEquals("project details", restAPI.addUser(projectID, authToken2))
        Assert.assertTrue(restAPI.removeUser(userID2, projectID, authToken2))
    }

    @Test
    fun deleteLastMemberOfProject() {
        // Create new project
        val projectID2 = restAPI.addProject(authToken, "project details")
        Assert.assertTrue(projectID2 > 0)

        // Remove the last project member (the admin removes himself)
        Assert.assertTrue(restAPI.removeUser(userID, projectID2, authToken))
    }

    */
/* TODO: Implement this in quality control phase
    @Test
    fun userIsNoProjectMember() {
        Assert.assertNotEquals(mutableListOf<List<String>>(), restAPI.getProjectParticipants(authToken, projectID))
        Assert.assertNotEquals("", restAPI.getProjectAdmin(authToken, projectID))
    }
    *//*


    @Test
    fun removeUserWhileNotBeingAdmin() {
        Assert.assertEquals("project details", restAPI.addUser(projectID, authToken2))
        Assert.assertEquals("project details", restAPI.addUser(projectID, authToken3))

        Assert.assertFalse(restAPI.removeUser(authToken2, projectID, authToken3))
    }
}*/
