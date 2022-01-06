package com.pseandroid2.dailydata.remoteDataSource.queue.observerLogic

import com.pseandroid2.dailydata.remoteDataSource.queue.FetchRequestQueue
import org.junit.Assert
import org.junit.Test

class FetchRequestObserverTests_AddDuplicateObserver {
    @Test
    fun addOneObserver() {
        // Erstelle Observer
        var toUpdate = UpdatedByObserver_ForTesting()
        var observer = FetchRequestQueueObserver_ForTesting(toUpdate)

        // Füge Observer hinzu
        var fetchRequestQueue = FetchRequestQueue()
        // Add Observer twice
        fetchRequestQueue.registerObserver(observer)
        fetchRequestQueue.registerObserver(observer)

        // Prüfe ob update() bei Observer ausgeführt wird
        Assert.assertEquals(toUpdate.isUpdated(), false)
        Assert.assertEquals(fetchRequestQueue.getQueueLength(), 0)
        var fetchRequest = "Fetch Request: 1"
        fetchRequestQueue.addFetchRequest(fetchRequest)
        Assert.assertEquals(fetchRequestQueue.getQueueLength(), 1)
        Assert.assertEquals(toUpdate.getUpdated(), 1)


        // Dies sollte nun den Controller entfernen: -> Update sollte nicht nochmal ausgeführt werden
        fetchRequestQueue.unregisterObserver(observer)
        var fetchRequest2 = "Fetch Request: 2"
        fetchRequestQueue.addFetchRequest(fetchRequest2)
        Assert.assertEquals(1, toUpdate.getUpdated())
    }
}