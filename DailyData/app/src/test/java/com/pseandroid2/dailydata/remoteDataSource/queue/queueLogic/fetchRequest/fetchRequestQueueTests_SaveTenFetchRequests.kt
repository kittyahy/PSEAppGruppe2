package com.pseandroid2.dailydata.remoteDataSource.queue.queueLogic.fetchRequest

import com.pseandroid2.dailydata.remoteDataSource.queue.FetchRequestQueue
import org.junit.Assert
import org.junit.Test

class fetchRequestQueueTests_SaveTenFetchRequests {
    @Test
    fun saveOneFetchRequest() {
        val fetchRequestQueue = FetchRequestQueue()

        val fetchRequests: MutableList<String> = mutableListOf<String>()
        for (idx in 1..10) {
            fetchRequests.add("fetchRequest$idx")
            fetchRequestQueue.addFetchRequest("fetchRequest$idx")
        }

        Assert.assertEquals(fetchRequestQueue.getQueueLength(), 10)

        fetchRequests.forEach {
            Assert.assertEquals(fetchRequestQueue.getFetchRequest(), it)
        }

        Assert.assertEquals(fetchRequestQueue.getQueueLength(), 0)
    }
}