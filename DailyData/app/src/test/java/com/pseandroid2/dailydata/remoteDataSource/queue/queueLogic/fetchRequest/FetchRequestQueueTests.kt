/*

    DailyData is an android app to easily create diagrams from data one has collected
    Copyright (C) 2022  Antonia Heiming, Anton Kadelbach, Arne Kuchenbecker, Merlin Opp, Robin Amman

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.

*/

package com.pseandroid2.dailydata.remoteDataSource.queue.queueLogic.fetchRequest

import com.pseandroid2.dailydata.remoteDataSource.queue.FetchRequestQueue
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverReturns.FetchRequest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class FetchRequestQueueTests {

    private var fetchRequestQueue = FetchRequestQueue()

    private var fetchRequest1 = FetchRequest(requestInfo = "Fetch Request: 1")
    private var fetchRequest2 = FetchRequest(requestInfo = "Fetch Request: 2")

    @Before
    fun setup() {
        Assert.assertEquals(0, fetchRequestQueue.getQueueLength())
    }

    @After
    fun cleanUp() {
        while (fetchRequestQueue.getQueueLength() > 0) {
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
            val fetchRequest1 = FetchRequest(requestInfo = "Fetch Request: $idx")
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