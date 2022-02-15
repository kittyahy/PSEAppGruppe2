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
import com.pseandroid2.dailydata.remoteDataSource.queue.observerLogic.UpdatedByObserverForTesting
import com.pseandroid2.dailydata.remoteDataSource.queue.observerLogic.projectCommand.ProjectCommandQueueObserverForTesting
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.RESTAPI
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.ServerManager
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverReturns.Delta
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

internal class ServerManagerIsProjectCommandQueueCorrectlyLinkedTests {/*
    private val deltaList: Collection<Delta> =
        listOf(Delta(project = 1), Delta(project = 2), Delta(project = 3))
    private lateinit var restAPI: RESTAPI

    // Create TestQueues
    private val toUpdate = UpdatedByObserverForTesting()
    private val projectCommandObserver = ProjectCommandQueueObserverForTesting(toUpdate)

    // Create ServerManager with mocked RestAPI
    private lateinit var serverManager: ServerManager

    @Before
    fun setup() {
        // Create mocked restAPI
        restAPI = mockk()
        coEvery { restAPI.getDelta(1, "") } returns deltaList

        serverManager = ServerManager(restAPI)

        Assert.assertEquals(0, toUpdate.getUpdated())
    }

    @ExperimentalCoroutinesApi
    @Test
    fun projectCommandQueueLinked() = runTest {
        // Add Observer to queues
        serverManager.addObserverToProjectCommandQueue(projectCommandObserver)

        // Fills ProjectCommandQueue with projectCommandInfo Objects
        serverManager.getProjectCommandsFromServer(
            1,
            ""
        )
        Assert.assertEquals(deltaList.size, toUpdate.getUpdated())

        Assert.assertEquals(deltaList.size, serverManager.getProjectCommandQueueLength())

        val projectCommands: MutableList<ProjectCommandInfo> = mutableListOf()

        // Convert deltas into ProjectCommandObjects
        for (i in deltaList.indices) {
            val delta: Delta = deltaList.elementAt(i)
            val projectCommand = ProjectCommandInfo(
                , delta.user,
                delta.admin,
                delta.projectCommand,
                delta.addedToServerS,
            )
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
        serverManager.getProjectCommandsFromServer(
            1,
            ""
        ) // Fills ProjectCommandQueue with projectCommandList

        Assert.assertEquals(
            deltaListSize,
            toUpdate.getUpdated()
        ) // Should not update because no observer is linked
    }*/
}