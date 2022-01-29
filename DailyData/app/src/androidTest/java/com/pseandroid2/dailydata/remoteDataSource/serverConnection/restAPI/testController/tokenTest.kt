package com.pseandroid2.dailydata.remoteDataSource.serverConnection.restAPI.testController

import com.pseandroid2.dailydata.remoteDataSource.serverConnection.RESTAPI
import com.pseandroid2.dailydata.remoteDataSource.userManager.FirebaseManager
import com.pseandroid2.dailydata.remoteDataSource.userManager.FirebaseReturnOptions
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class tokenTest {
    private val restAPI: RESTAPI = RESTAPI()
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

    @Before
    fun setup() {
        // Generate valid firebase authentication token
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
    }

    @Test
    fun tokenTransfer()
    {
        Assert.assertEquals(authToken, restAPI.tokenTransfer(authToken)) // TODO (implement method in RESTAPI)
    }

    @Test
    fun transferEmptyToken()
    {
        Assert.assertEquals("", restAPI.tokenTransfer("")) // TODO (implement method in RESTAPI)
    }

    @Test
    fun getUserIDFromToken()
    {
        Assert.assertEquals(userID, restAPI.getUserIDFromToken(authToken)) // TODO (implement method in RESTAPI)
    }

    @Test
    fun getUserIDFromInvalidToken()
    {
        Assert.assertEquals("", restAPI.getUserIDFromToken("")) // TODO (implement method in RESTAPI)
        Assert.assertEquals("", restAPI.getUserIDFromToken("Im invalid :("))
    }
}