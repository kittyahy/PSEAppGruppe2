package com.pseandroid2.dailydata.remoteDataSource.serverConnection.restAPI

import com.pseandroid2.dailydata.remoteDataSource.serverConnection.RESTAPI
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Test
import kotlinx.coroutines.test.runTest

class GreetTest {

    @ExperimentalCoroutinesApi
    @Test
    fun greet() = runTest {
        val restAPI = RESTAPI()

        
        Assert.assertTrue(restAPI.greet())
    }

}

