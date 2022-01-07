package com.pseandroid2.dailydata.remoteDataSource.queue.observerLogic.fetchRequest

import com.pseandroid2.dailydata.remoteDataSource.queue.FetchRequestQueue
import com.pseandroid2.dailydata.remoteDataSource.queue.observerLogic.UpdatedByObserver_ForTesting
import org.junit.Assert
import org.junit.Test

class FetchRequestObserverTests_RemoveObserver {
    @Test
    fun removeObserver() {
        // Erstelle Observer
        var toUpdate = UpdatedByObserver_ForTesting()
        var observer = FetchRequestQueueObserver_ForTesting(toUpdate)

        // Füge Observer hinzu
        var fetchRequestQueue = FetchRequestQueue()
        fetchRequestQueue.registerObserver(observer)

        // Prüfe ob update() bei Observer ausgeführt wird
        Assert.assertEquals(toUpdate.isUpdated(), false)
        Assert.assertEquals(fetchRequestQueue.getQueueLength(), 0)
        var fetchRequest = "Fetch Request: 1"
        fetchRequestQueue.addFetchRequest(fetchRequest)
        Assert.assertEquals(fetchRequestQueue.getQueueLength(), 1)
        Assert.assertEquals(toUpdate.isUpdated(), true)


        fetchRequestQueue.unregisterObserver(observer)
        var fetchRequest2 = "Fetch Request: 2"
        fetchRequestQueue.addFetchRequest(fetchRequest2)

        Assert.assertEquals(toUpdate.getUpdated(), 1)
    }
}