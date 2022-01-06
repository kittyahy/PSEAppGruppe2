package com.pseandroid2.dailydata.remoteDataSource.queue.queueLogic.fetchRequest

import com.pseandroid2.dailydata.remoteDataSource.queue.FetchRequestQueue
import org.junit.Assert
import org.junit.Test

class fetchRequestQueueTests_SaveEmptyString {
    @Test
    fun saveOneFetchRequest() {
        val fetchRequestQueue = FetchRequestQueue()

        val fetchRequest = ""

        Assert.assertEquals(fetchRequestQueue.getQueueLength(), 0)

        fetchRequestQueue.addFetchRequest(fetchRequest)

        Assert.assertEquals(fetchRequestQueue.getQueueLength(), 0)

        val returnedFetchRequest = fetchRequestQueue.getFetchRequest()

        Assert.assertEquals(fetchRequest, returnedFetchRequest)
        Assert.assertEquals(fetchRequestQueue.getQueueLength(), 0)
    }
}