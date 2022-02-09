package com.pseandroid2.dailydata.remoteDataSource.rdsAPI

import com.pseandroid2.dailydata.remoteDataSource.serverConnection.RESTAPI
import io.github.infeez.kotlinmockserver.dsl.http.mock
import io.github.infeez.kotlinmockserver.server.ServerConfiguration
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ServerMethodsCorrectlyLinkedTests {

    /* TODO: Unit tests for server
    private val mockServer = okHttpMockServer(ServerConfiguration.custom {
        host = "localhost"
        port = 8888
        //mock("/greet") { body("Hello") }
    })

    private val greetMock = mock("http://localhost:8888/greet") {
        body("Hello")
    }

    @Before
    fun setup() {
        mockServer.add(greetMock)
    }

    @After
    fun tearDown() {
        //mockServer.close()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun greet() = runTest {
        val restAPI = RESTAPI("http://localhost:8888")

        Assert.assertTrue(restAPI.greet())
    }
    */
}

