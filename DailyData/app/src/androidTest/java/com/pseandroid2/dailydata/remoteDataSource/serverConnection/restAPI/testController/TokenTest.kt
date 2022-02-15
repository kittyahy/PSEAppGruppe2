package com.pseandroid2.dailydata.remoteDataSource.serverConnection.restAPI.testController

import com.pseandroid2.dailydata.remoteDataSource.serverConnection.RESTAPI
import com.pseandroid2.dailydata.remoteDataSource.userManager.FirebaseManager
import com.pseandroid2.dailydata.remoteDataSource.userManager.FirebaseReturnOptions
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class TokenTest {
    private val restAPI: RESTAPI = RESTAPI()
    private val fm = FirebaseManager(null)

    private val email = "test@student.kit.edu"
    private val password = "PSEistsuper"
    private val userID = "4hpJh32YaAWrAYoVvo047q7Ey183"
    private var authToken: String = ""

    @Before
    fun setup() {
        // Generate valid firebase authentication token
        Assert.assertEquals(
            FirebaseReturnOptions.SINGED_IN,
            fm.signInWithEmailAndPassword(email, password)
        )
        authToken = fm.getToken()
        Assert.assertNotEquals("", authToken)
    }

    @Test
    fun tokenTransfer()
    {
        Assert.assertEquals(authToken, restAPI.getToken(authToken))
    }

    @Test
    fun transferEmptyToken()
    {
        Assert.assertEquals("", restAPI.getToken(""))
    }

    @Test
    fun getUserIDFromToken()
    {
        Assert.assertEquals(userID, restAPI.getUserIDFromToken(authToken))
    }

    @Test
    fun getUserIDFromInvalidToken()
    {
        Assert.assertEquals("", restAPI.getUserIDFromToken(""))
        Assert.assertEquals("", restAPI.getUserIDFromToken("Im invalid :("))
    }
}