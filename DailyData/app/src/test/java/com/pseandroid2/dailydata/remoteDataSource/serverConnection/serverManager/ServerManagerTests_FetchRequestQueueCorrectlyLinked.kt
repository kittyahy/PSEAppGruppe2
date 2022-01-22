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

package com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverManager

import com.pseandroid2.dailydata.remoteDataSource.queue.observerLogic.UpdatedByObserver_ForTesting
import com.pseandroid2.dailydata.remoteDataSource.queue.observerLogic.fetchRequest.FetchRequestQueueObserver_ForTesting
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.RESTAPI
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.ServerManager
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverReturns.FetchRequest
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert
import org.junit.Before
import org.junit.Test

internal class ServerManagerTests_FetchRequestQueueCorrectlyLinked {

    private var fetchRequestList: MutableList<FetchRequest> = mutableListOf(FetchRequest(project = 1), FetchRequest(project = 2), FetchRequest(project = 3))
    private lateinit var restAPI: RESTAPI
    private lateinit var serverManager: ServerManager

    // Create Observer
    private val toUpdate = UpdatedByObserver_ForTesting()
    private val fetchRequestObserver = FetchRequestQueueObserver_ForTesting(toUpdate)

    @Before
    fun setup() {
        restAPI = mockk<RESTAPI>()
        every { restAPI.getFetchRequests(1, "") } returns fetchRequestList
        // Create ServerManager with mocked RestAPI
        serverManager = ServerManager(restAPI)
    }

    @Test
    fun fetchRequestQueueLinked() {
        // Create mocked restAPI
        Assert.assertTrue(false)


        Assert.assertEquals(0, toUpdate.getUpdated())

        // Add Observer to queues
        serverManager.addObserverToFetchRequestQueue(fetchRequestObserver)

        // Fill Queues
        serverManager.getFetchRequests(1, "") // Fills ProjectCommandQueue with projectCommandInfo Objects
        val fetchRequestListSize = fetchRequestList.size
        Assert.assertEquals(fetchRequestListSize, toUpdate.getUpdated())

        Assert.assertEquals(fetchRequestListSize, serverManager.getFetchRequestQueueLength())


        // Check if Queue is correctly linked
        for (i in 0 until fetchRequestListSize) {
            val fetchRequestFromQueue: FetchRequest? = serverManager.getFetchRequestFromQueue()
            Assert.assertTrue(fetchRequestList.remove(fetchRequestFromQueue))
            Assert.assertEquals(fetchRequestList.size - i - 1, serverManager.getFetchRequestQueueLength())
        }

        // Unregister Observer
        serverManager.unregisterObserverFromFetchRequestQueue(fetchRequestObserver)

        // Fill Queues
        serverManager.getFetchRequests(1, "")  // Fills ProjectCommandQueue with projectCommandList

        Assert.assertEquals(fetchRequestListSize, toUpdate.getUpdated()) // Should not update because no observer is linked
    }
}