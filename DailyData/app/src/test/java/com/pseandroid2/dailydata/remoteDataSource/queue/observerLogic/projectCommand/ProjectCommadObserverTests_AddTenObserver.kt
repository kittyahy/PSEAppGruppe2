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

import com.pseandroid2.dailydata.remoteDataSource.queue.ProjectCommandQueue
import com.pseandroid2.dailydata.remoteDataSource.queue.observerLogic.UpdatedByObserver_ForTesting
import org.junit.Assert
import org.junit.Test

class ProjectCommadObserverTests_AddTenObserver {
    @Test
    fun addTenObserver() {
        var toUpdateObjects = mutableListOf<UpdatedByObserver_ForTesting>()

        // Erstelle Observer
        val observers: MutableList<ProjectCommandQueueObserver_ForTesting> = mutableListOf<ProjectCommandQueueObserver_ForTesting>()
        for (idx in 1..10) {
            val toUpdate = UpdatedByObserver_ForTesting()
            observers.add(ProjectCommandQueueObserver_ForTesting(toUpdate))
            toUpdateObjects.add(toUpdate)
        }

        // Füge Observer hinzu
        var projectCommandQueue = ProjectCommandQueue()
        observers.forEach {
            projectCommandQueue.registerObserver(it)
        }

        // Prüfe ob update() bei Observer ausgeführt wird:
        toUpdateObjects.forEach {
            Assert.assertEquals(it.isUpdated(), false)
        }

        Assert.assertEquals(projectCommandQueue.getQueueLength(), 0)
        var projectCommand = "Project Command: 1"
        projectCommandQueue.addProjectCommand(projectCommand)
        Assert.assertEquals(projectCommandQueue.getQueueLength(), 1)

        // Prüfe ob alle toUpdateObjects geupdated wurden
        toUpdateObjects.forEach {
            Assert.assertEquals(it.isUpdated(), true)
        }

    }
}


