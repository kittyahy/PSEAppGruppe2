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
import com.pseandroid2.dailydata.remoteDataSource.queue.observerLogic.projectCommand.ProjectCommandQueueObserver_ForTesting
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.RESTAPI
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.ServerManager
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverReturns.Delta
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert
import org.junit.Test

internal class ServerManagerTests_ProjectCommandQueueCorrectlyLinked {

    @Test
    fun projectCommandQueueLinked() {
        // Create mocked restAPI
        val deltaList: Collection<Delta> = listOf(Delta(project = 1), Delta(project = 2), Delta(project = 3))

        var restAPI: RESTAPI = mockk<RESTAPI>()
        every { restAPI.getDelta(1, "") } returns deltaList


        // Create TestQueues
        var toUpdate = UpdatedByObserver_ForTesting()
        var projectCommandObserver = ProjectCommandQueueObserver_ForTesting(toUpdate)

        // Create ServerManager with mocked RestAPI
        val serverManager = ServerManager(restAPI)

        Assert.assertEquals(0, toUpdate.getUpdated())

        // Add Observer to queues
        serverManager.addObserverToProjectCommandQueue(projectCommandObserver)

        // Fill Queues
        serverManager.getProjectCommandsFromServer(1, "") // Fills ProjectCommandQueue with projectCommandInfo Objects
        Assert.assertEquals(deltaList.size, toUpdate.getUpdated())

        Assert.assertEquals(deltaList.size, serverManager.getProjectCommandQueueLength())

        val projectCommands: MutableList<ProjectCommandInfo> = mutableListOf()

        // Convert deltas into ProjectCommandObjects
        for (i in deltaList.indices) {
            val delta: Delta = deltaList.elementAt(i)
            val projectCommand = ProjectCommandInfo(delta.addedToServer, delta.user, delta.isAdmin, delta.projectCommand)
            projectCommands.add(projectCommand)
        }

        // Check if Queue is correctly linked
        val deltaListSize = deltaList.size
        for (i in 1..deltaListSize) {
            val projectCommandFromQueue = serverManager.getProjectCommandFromQueue()
            Assert.assertTrue(projectCommands.remove(projectCommandFromQueue))
            Assert.assertEquals(deltaListSize - i, serverManager.getProjectCommandQueueLength())
        }

        // Unregister Observer
        serverManager.unregisterObserverFromProjectCommandQueue(projectCommandObserver)

        // Fill Queues
        serverManager.getProjectCommandsFromServer(1, "") // Fills ProjectCommandQueue with projectCommandList

        Assert.assertEquals(deltaListSize, toUpdate.getUpdated()) // Should not update because no observer is linked
    }
}