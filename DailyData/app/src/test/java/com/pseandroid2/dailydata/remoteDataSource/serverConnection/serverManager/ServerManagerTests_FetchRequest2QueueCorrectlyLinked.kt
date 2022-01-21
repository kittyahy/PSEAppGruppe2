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

import com.pseandroid2.dailydata.remoteDataSource.queue.ProjectCommandInfo
import com.pseandroid2.dailydata.remoteDataSource.queue.observerLogic.UpdatedByObserver_ForTesting
import com.pseandroid2.dailydata.remoteDataSource.queue.observerLogic.fetchRequest.FetchRequestQueueObserver_ForTesting
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.RESTAPI
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.ServerManager
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverReturns.Delta
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverReturns.FetchRequest
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert
import org.junit.Test

internal class ServerManagerTests_FetchRequest2QueueCorrectlyLinked {

    @Test
    fun fetchRequestQueueLinked() {
        // Create mocked restAPI
        val fetchRequestList: Collection<FetchRequest> = listOf(FetchRequest(project = 1), FetchRequest(project = 2), FetchRequest(project = 3))

        var restAPI: RESTAPI = mockk<RESTAPI>()
        every { restAPI.getFetchRequests(1, "") } returns fetchRequestList



        // Create TestQueues
        var toUpdate = UpdatedByObserver_ForTesting()
        var fetchRequestObserver = FetchRequestQueueObserver_ForTesting(toUpdate)

        // Create ServerManager with mocked RestAPI
        val serverManager = ServerManager(restAPI)

        Assert.assertEquals(0, toUpdate.getUpdated())

        // Add Observer to queues
        serverManager.addObserverToProjectCommandQueue(fetchRequestObserver)

        // Fill Queues
        serverManager.getFetchRequests(1, "") // Fills ProjectCommandQueue with projectCommandInfo Objects
        Assert.assertEquals(fetchRequestList.size, toUpdate.getUpdated())

        Assert.assertEquals(fetchRequestList.size, serverManager.getFetchRequestQueueLength())


        // Convert fetchRequests into Strings
        val fetchRequestStrings: MutableList<String> = mutableListOf()
        for (i in fetchRequestList.indices) {
            val delta: Delta = fetchRequestList.elementAt(i).requestInfo
            val projectCommand = ProjectCommandInfo(delta.addedToServer, delta.user, delta.isAdmin, delta.projectCommand)
            projectCommands.add(projectCommand)
        }

        // Check if Queue is correctly linked

        for (i in fetchRequestList.indices) {
            val fetchRequestFromQueue: String = serverManager.getFetchRequestFromQueue()
            Assert.assertTrue(fetchRequestStrings.remove(fetchRequestFromQueue))
            Assert.assertEquals(fetchRequestList.size - i - 1, serverManager.getFetchRequestQueueLength())
        }

        // Unregister Observer
        serverManager.unregisterObserverFromFetchRequestQueue(fetchRequestObserver)

        // Fill Queues
        serverManager.getDeltasFromServer(1, "") // Fills ProjectCommandQueue with projectCommandList

        Assert.assertEquals(1, toUpdate.getUpdated()) // Should not update because no observer is linked
    }
}