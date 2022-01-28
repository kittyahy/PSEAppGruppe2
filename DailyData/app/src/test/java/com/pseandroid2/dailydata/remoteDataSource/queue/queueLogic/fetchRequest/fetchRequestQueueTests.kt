package com.pseandroid2.dailydata.remoteDataSource.queue.queueLogic.fetchRequest

import com.pseandroid2.dailydata.remoteDataSource.queue.FetchRequestQueue
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverReturns.FetchRequest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class fetchRequestQueueTests {

    var fetchRequestQueue = FetchRequestQueue()

    var fetchRequest1 = FetchRequest(requestInfo = "Fetch Request: 1")
    var fetchRequest2 = FetchRequest(requestInfo = "Fetch Request: 2")

    @Before
    fun setup() {
        Assert.assertEquals(0, fetchRequestQueue.getQueueLength())
    }

    @After
    fun cleanUp() {
        while(fetchRequestQueue.getQueueLength() > 0) {
            fetchRequestQueue.getFetchRequest()
        }
        Assert.assertEquals(0, fetchRequestQueue.getQueueLength())
    }

    @Test
    fun saveOneFetchRequest() {
        fetchRequestQueue.addFetchRequest(fetchRequest1)
        Assert.assertEquals(1, fetchRequestQueue.getQueueLength())
        Assert.assertEquals(fetchRequest1, fetchRequestQueue.getFetchRequest())

        Assert.assertEquals(0, fetchRequestQueue.getQueueLength())
    }

    @Test
    fun saveTenFetchRequest() {
        val fetchRequests: MutableList<FetchRequest> = mutableListOf()

        for (idx in 1..10) {
            var fetchRequest1 = FetchRequest(requestInfo = "Fetch Request: $idx")
            fetchRequests.add(fetchRequest1)
            fetchRequestQueue.addFetchRequest(fetchRequest1)
        }

        Assert.assertEquals(10, fetchRequestQueue.getQueueLength())

        fetchRequests.forEach {
            Assert.assertEquals(fetchRequestQueue.getFetchRequest(), it)
        }

        Assert.assertEquals(0, fetchRequestQueue.getQueueLength())
    }

    @Test
    fun saveRedundantFetchRequest() {
        fetchRequestQueue.addFetchRequest(fetchRequest1)
        fetchRequestQueue.addFetchRequest(fetchRequest2)
        fetchRequestQueue.addFetchRequest(fetchRequest1)
        fetchRequestQueue.addFetchRequest(fetchRequest2)

        Assert.assertEquals(2, fetchRequestQueue.getQueueLength())

        Assert.assertEquals(fetchRequest1, fetchRequestQueue.getFetchRequest())
        Assert.assertEquals(fetchRequest2, fetchRequestQueue.getFetchRequest())

        Assert.assertEquals(0, fetchRequestQueue.getQueueLength())
    }

    @Test
    fun getFromEmptyFetchRequestQueue() {
        Assert.assertEquals(null, fetchRequestQueue.getFetchRequest())

        Assert.assertEquals(0, fetchRequestQueue.getQueueLength())
    }
}