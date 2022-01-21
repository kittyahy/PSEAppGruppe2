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

import com.pseandroid2.dailydata.remoteDataSource.queue.FetchRequestQueueObserver
import com.pseandroid2.dailydata.remoteDataSource.queue.ProjectCommandInfo
import com.pseandroid2.dailydata.remoteDataSource.queue.observerLogic.UpdatedByObserver_ForTesting
import com.pseandroid2.dailydata.remoteDataSource.queue.observerLogic.projectCommand.ProjectCommandQueueObserver_ForTesting
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.RESTAPI
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.ServerManager
import org.junit.Assert
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import java.time.LocalDateTime

internal class ServerManagerTests_ProjectCommandQueueCorrectlyLinked {




    @Test
    fun queuesLinked() {
        // Create mocked restAPI
        val projectCommand1 = ProjectCommandInfo(projectCommand = "1")

        var restAPI: RESTAPI = mock<RESTAPI>()
        every { restAPI.getDelta() } return projectCommand1


        // Create TestQueues
        var toUpdate = UpdatedByObserver_ForTesting()
        var projectCommandObserver = ProjectCommandQueueObserver_ForTesting(toUpdate)

        // Create ServerManager with mocked RestAPI
        val serverManager = ServerManager(restAPI)

        Assert.assertEquals(0, toUpdate.getUpdated())

        // Add Observer to queues
        serverManager.addObserverToProjectCommandQueue(projectCommandObserver)
        Assert.assertEquals(0, toUpdate.getUpdated())

        // Fill Queues
        serverManager.getDeltasFromServer(1, "") // Fills ProjectCommandQueue with projectCommandList

        // Check if ProjectCommandQueue is correctly linked
        Assert.assertEquals(1, serverManager.getProjectCommandQueueLength())
        Assert.assertEquals(projectCommand1, serverManager.getProjectCommandFromQueue())
        Assert.assertEquals(0, serverManager.getProjectCommandQueueLength())

        Assert.assertEquals(1, toUpdate.getUpdated()) // Should be updated 2 times from the projectCommandObserver

        // Unregister Observer
        serverManager.unregisterObserverFromProjectCommandQueue(projectCommandObserver)

        // Fill Queues
        serverManager.getDeltasFromServer(1, "") // Fills ProjectCommandQueue with projectCommandList

        Assert.assertEquals(1, toUpdate.getUpdated()) // Should not update because no observer is linked
    }
}