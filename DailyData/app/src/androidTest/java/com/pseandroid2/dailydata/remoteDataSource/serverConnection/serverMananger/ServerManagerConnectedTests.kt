package com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverMananger

import com.pseandroid2.dailydata.remoteDataSource.serverConnection.RESTAPI
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.ServerManager
import com.pseandroid2.dailydata.remoteDataSource.userManager.FirebaseManager
import com.pseandroid2.dailydata.remoteDataSource.userManager.FirebaseReturnOptions
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ServerManagerConnectedTests {
    private val restAPI: RESTAPI = RESTAPI()
    private val serverManager: ServerManager = ServerManager(RESTAPI())
    private val fm = FirebaseManager(null)

    private val email = "test@student.kit.edu"
    private val password = "PSEistsuper"
    private val userID = "4hpJh32YaAWrAYoVvo047q7Ey183"
    private var authToken: String = ""

    @Before
    fun setup() {
        Assert.assertEquals(FirebaseReturnOptions.SINGED_IN, fm.signInWithEmailAndPassword(email, password))
        authToken = fm.getToken()
    }

    @Test
    fun getAllPostPreviews() {
        //TODO
        //serverManager.getAllPostPreview()
    }

    @Test
    fun getPostDetail() {
        //TODO
    }
}