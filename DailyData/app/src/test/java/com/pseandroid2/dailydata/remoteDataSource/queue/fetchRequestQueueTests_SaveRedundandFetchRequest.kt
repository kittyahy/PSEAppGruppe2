package com.pseandroid2.dailydata.remoteDataSource.queue

import org.junit.Assert
import org.junit.Test

class fetchRequestQueueTests_SaveRedundandFetchRequest {
    @Test
    fun saveOneFetchRequest() {
        val fetchRequestQueue = FetchRequestQueue()

        val fetchRequest1 = "fetchRequest: 1"
        val fetchRequest2 = "fetchRequest: 2"

        Assert.assertEquals(fetchRequestQueue.getQueueLength(), 0)

        fetchRequestQueue.addFetchRequest(fetchRequest1)
        fetchRequestQueue.addFetchRequest(fetchRequest2)
        fetchRequestQueue.addFetchRequest(fetchRequest1)
        fetchRequestQueue.addFetchRequest(fetchRequest2)

        Assert.assertEquals(fetchRequestQueue.getQueueLength(), 2)

        val returnedFetchRequest = fetchRequestQueue.getFetchRequest()
        Assert.assertEquals(fetchRequest1, returnedFetchRequest)

        val returnedFetchRequest2 = fetchRequestQueue.getFetchRequest()
        Assert.assertEquals(fetchRequest2, returnedFetchRequest2)

        Assert.assertEquals(fetchRequestQueue.getQueueLength(), 0)
    }
}