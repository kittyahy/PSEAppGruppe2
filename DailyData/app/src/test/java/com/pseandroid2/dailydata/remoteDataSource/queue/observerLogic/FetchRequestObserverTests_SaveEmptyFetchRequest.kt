package com.pseandroid2.dailydata.remoteDataSource.queue.observerLogic

import com.pseandroid2.dailydata.remoteDataSource.queue.FetchRequestQueue
import org.junit.Assert
import org.junit.Test

class FetchRequestObserverTests_SaveEmptyFetchRequest {
    // No Update expected:
    @Test
    fun saveEmptyFetchRequest() {
        // Erstelle Observer
        var toUpdate = UpdatedByObserver_ForTesting()
        var observer = FetchRequestQueueObserver_ForTesting(toUpdate)

        // Füge Observer hinzu
        var fetchRequestQueue = FetchRequestQueue()
        fetchRequestQueue.registerObserver(observer)

        // Prüfe ob update() bei Observer ausgeführt wird
        Assert.assertEquals(toUpdate.isUpdated(), false)

        Assert.assertEquals(fetchRequestQueue.getQueueLength(), 0)
        var fetchRequest = ""
        fetchRequestQueue.addFetchRequest(fetchRequest)

        Assert.assertEquals(toUpdate.isUpdated(), false)
    }
}