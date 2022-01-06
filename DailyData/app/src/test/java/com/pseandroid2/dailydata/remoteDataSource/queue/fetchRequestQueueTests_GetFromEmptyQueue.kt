package com.pseandroid2.dailydata.remoteDataSource.queue

import org.junit.Assert
import org.junit.Test

class fetchRequestQueueTests_GetFromEmptyQueue {
    @Test
    fun saveOneFetchRequest() {
        val fetchRequestQueue = FetchRequestQueue()

        Assert.assertEquals(fetchRequestQueue.getQueueLength(), 0)

        val returnedFetchRequest = fetchRequestQueue.getFetchRequest()

        Assert.assertEquals(returnedFetchRequest,"")

        Assert.assertEquals(fetchRequestQueue.getQueueLength(), 0)
    }
}