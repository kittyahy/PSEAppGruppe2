package com.pseandroid2.dailydata.remoteDataSource.serverConnection.restAPI.ProjectParticipantsController

import com.pseandroid2.dailydata.remoteDataSource.serverConnection.RESTAPI
import com.pseandroid2.dailydata.remoteDataSource.userManager.FirebaseManager
import com.pseandroid2.dailydata.remoteDataSource.userManager.FirebaseReturnOptions
import org.junit.Assert
import org.junit.Before

class UserTests {
    private var restAPI: RESTAPI = RESTAPI()
    private lateinit var authToken: String

    @Before
    fun setup() {
        // Generate valid firebase authentication token
        val fm = FirebaseManager(null)
        var email = "test@student.kit.edu"
        var password = "PSEistsuper"

        Assert.assertEquals(FirebaseReturnOptions.SINGED_IN, fm.signInWithEmailAndPassword(email, password))
        authToken = fm.getToken()
    }
}