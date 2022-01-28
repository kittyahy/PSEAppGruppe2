package com.pseandroid2.dailydata.remoteDataSource.queue.observerLogic.fetchRequest

import com.pseandroid2.dailydata.remoteDataSource.queue.FetchRequestQueue
import com.pseandroid2.dailydata.remoteDataSource.queue.observerLogic.UpdatedByObserver_ForTesting
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverReturns.FetchRequest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class FetchRequestObserverTests {
    var fetchRequestQueue = FetchRequestQueue()

    var toUpdate = UpdatedByObserver_ForTesting()
    var toUpdateObjects = mutableListOf<UpdatedByObserver_ForTesting>() // for testing multiple observer

    // Create observer
    var observer = FetchRequestQueueObserver_ForTesting(toUpdate)
    val observers: MutableList<FetchRequestQueueObserver_ForTesting> = mutableListOf() // for testing multiple observer


    @Before
    fun setup() {
        for (idx in 1..10) {
            val toUpdate = UpdatedByObserver_ForTesting()
            observers.add(FetchRequestQueueObserver_ForTesting(toUpdate))
            toUpdateObjects.add(toUpdate)
        }
    }

    @After
    fun resetToUpdate() {
        toUpdate.resetUpdate()
        toUpdateObjects.forEach {
            it.resetUpdate()
        }

        // Remove Observer
        fetchRequestQueue.unregisterObserver(observer)
    }

    @Test
    fun addOneObserver() {
        fetchRequestQueue.registerObserver(observer)

        //Test if update() is called in the observer
        Assert.assertEquals(false, toUpdate.isUpdated())
        Assert.assertEquals(0, fetchRequestQueue.getQueueLength())
        fetchRequestQueue.addFetchRequest(FetchRequest(requestInfo = "Fetch Request: 1"))
        Assert.assertEquals(1, fetchRequestQueue.getQueueLength())
        Assert.assertEquals(true, toUpdate.isUpdated())
    }

    @Test
    fun addDuplicateObserver() {
        // Add observer twice
        fetchRequestQueue.registerObserver(observer)
        fetchRequestQueue.registerObserver(observer)

        //Test if update() is called in the observer
        Assert.assertEquals(false, toUpdate.isUpdated() )
        Assert.assertEquals(0, fetchRequestQueue.getQueueLength())
        fetchRequestQueue.addFetchRequest(FetchRequest(requestInfo = "Fetch Request: 1"))
        Assert.assertEquals(1, fetchRequestQueue.getQueueLength())
        Assert.assertEquals(1, toUpdate.getUpdated())


        // Removes the observer -> Update should not be called again
        fetchRequestQueue.unregisterObserver(observer)
        fetchRequestQueue.addFetchRequest(FetchRequest(requestInfo = "Fetch Request: 2"))
        Assert.assertEquals(1, toUpdate.getUpdated())
    }

    @Test
    fun addTenObserver() {
        // Add observers to queue
        observers.forEach() {
            fetchRequestQueue.registerObserver(it)
        }
        toUpdateObjects.forEach {
            Assert.assertEquals(false, it.isUpdated())
        }

        // Updates Observer
        Assert.assertEquals(fetchRequestQueue.getQueueLength(), 0)
        fetchRequestQueue.addFetchRequest(FetchRequest(requestInfo = "Fetch Request: 1")) // updates observer
        Assert.assertEquals(fetchRequestQueue.getQueueLength(), 1)

        // Test if all observer got updated
        toUpdateObjects.forEach {
            Assert.assertEquals(true, it.isUpdated())
        }
    }

    @Test
    fun removeObserver() {
        fetchRequestQueue.registerObserver(observer)

        // Check if the observer gets updated
        Assert.assertEquals(false, toUpdate.isUpdated())
        Assert.assertEquals(0, fetchRequestQueue.getQueueLength())
        fetchRequestQueue.addFetchRequest(FetchRequest(requestInfo = "Fetch Request: 1"))
        Assert.assertEquals(1, fetchRequestQueue.getQueueLength())
        Assert.assertEquals(true, toUpdate.isUpdated())

        // Remove observer and check if it still gets updated
        fetchRequestQueue.unregisterObserver(observer)
        fetchRequestQueue.addFetchRequest(FetchRequest(requestInfo = "Fetch Request: 2"))

        Assert.assertEquals(toUpdate.getUpdated(), 1)
    }
}