package com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverMananger

import android.util.Log
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.RESTAPI
import org.junit.Assert
import org.junit.Test
import com.pseandroid2.dailydata.remoteDataSource.userManager.FirebaseManager
import com.pseandroid2.dailydata.remoteDataSource.userManager.FirebaseReturnOptions

class RESTAPITests_Greet {
    @Test
    fun greet() {
        var restAPI: RESTAPI = RESTAPI()

        Assert.assertTrue(restAPI.greet())
    }
}