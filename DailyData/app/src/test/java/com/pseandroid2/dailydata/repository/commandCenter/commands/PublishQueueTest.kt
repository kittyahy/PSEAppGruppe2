package com.pseandroid2.dailydata.repository.commandCenter.commands

import com.pseandroid2.dailydata.remoteDataSource.RemoteDataSourceAPI
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class PublishQueueTest : TestCase() {

    @Before
    public override fun setUp() {
        super.setUp()
        val remoteDataSourceAPI = mockk<RemoteDataSourceAPI>()
        every {}
        val publishQueue = PublishQueue()
    }

    public override fun tearDown() {}
    @ExperimentalCoroutinesApi
    @Test
    public fun publishQueueTest() = runTest() {

    }
}