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

package com.pseandroid2.dailydata.remoteDataSource.queue.observerLogic.projectCommand

import com.pseandroid2.dailydata.remoteDataSource.queue.ProjectCommandInfo
import com.pseandroid2.dailydata.remoteDataSource.queue.ProjectCommandQueue
import com.pseandroid2.dailydata.remoteDataSource.queue.observerLogic.UpdatedByObserverForTesting
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ProjectCommandObserverTests {/*
    private var projectCommandQueue = ProjectCommandQueue()

    private var toUpdate = UpdatedByObserverForTesting()
    private var toUpdateObjects =
        mutableListOf<UpdatedByObserverForTesting>() // for testing multiple observer

    // Create observer
    private var observer = ProjectCommandQueueObserverForTesting(toUpdate)
    private val observers: MutableList<ProjectCommandQueueObserverForTesting> =
        mutableListOf() // for testing multiple observer


    @Before
    fun setup() {
        for (idx in 1..10) {
            val toUpdate = UpdatedByObserverForTesting()
            observers.add(ProjectCommandQueueObserverForTesting(toUpdate))
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
        projectCommandQueue.unregisterObserver(observer)
    }

    @Test
    fun addOneObserver() {
        projectCommandQueue.registerObserver(observer)

        //Test if update() is called in the observer
        Assert.assertEquals(false, toUpdate.isUpdated())
        Assert.assertEquals(0, projectCommandQueue.getQueueLength())
        projectCommandQueue.addProjectCommand(ProjectCommandInfo(, projectCommand = "Project Command: 1",))
        Assert.assertEquals(1, projectCommandQueue.getQueueLength())
        Assert.assertEquals(true, toUpdate.isUpdated())
    }

    @Test
    fun addDuplicateObserver() {
        // Add observer twice
        projectCommandQueue.registerObserver(observer)
        projectCommandQueue.registerObserver(observer)

        //Test if update() is called in the observer
        Assert.assertEquals(false, toUpdate.isUpdated())
        Assert.assertEquals(0, projectCommandQueue.getQueueLength())
        projectCommandQueue.addProjectCommand(ProjectCommandInfo(, projectCommand = "Fetch Request: 1",))
        Assert.assertEquals(1, projectCommandQueue.getQueueLength())
        Assert.assertEquals(1, toUpdate.getUpdated())


        // Removes the observer -> Update should not be called again
        projectCommandQueue.unregisterObserver(observer)
        projectCommandQueue.addProjectCommand(ProjectCommandInfo(, projectCommand = "Project Command: 2",))
        Assert.assertEquals(1, toUpdate.getUpdated())
    }

    @Test
    fun addTenObserver() {
        // Add observers to queue
        observers.forEach {
            projectCommandQueue.registerObserver(it)
        }
        toUpdateObjects.forEach {
            Assert.assertEquals(false, it.isUpdated())
        }

        // Updates Observer
        Assert.assertEquals(projectCommandQueue.getQueueLength(), 0)
        projectCommandQueue.addProjectCommand(ProjectCommandInfo(, projectCommand = "Project Command: 1",)) // updates observer
        Assert.assertEquals(projectCommandQueue.getQueueLength(), 1)

        // Test if all observer got updated
        toUpdateObjects.forEach {
            Assert.assertEquals(true, it.isUpdated())
        }
    }

    @Test
    fun removeObserver() {
        projectCommandQueue.registerObserver(observer)

        // Check if the observer gets updated
        Assert.assertEquals(false, toUpdate.isUpdated())
        Assert.assertEquals(0, projectCommandQueue.getQueueLength())
        projectCommandQueue.addProjectCommand(ProjectCommandInfo(, projectCommand = "Project Command: 1",))
        Assert.assertEquals(1, projectCommandQueue.getQueueLength())
        Assert.assertEquals(true, toUpdate.isUpdated())

        // Remove observer and check if it still gets updated
        projectCommandQueue.unregisterObserver(observer)
        projectCommandQueue.addProjectCommand(ProjectCommandInfo(, projectCommand = "Project Command: 2",))

        Assert.assertEquals(toUpdate.getUpdated(), 1)
    }*/
}