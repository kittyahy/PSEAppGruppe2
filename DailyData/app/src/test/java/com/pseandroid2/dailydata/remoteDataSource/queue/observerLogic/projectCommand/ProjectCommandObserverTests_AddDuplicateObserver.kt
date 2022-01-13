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

class ProjectCommandObserverTests_AddDuplicateObserver {
    @Test
    fun addDuplicateObserver() {
        // Erstelle Observer
        var toUpdate = UpdatedByObserver_ForTesting()
        var observer = ProjectCommandQueueObserver_ForTesting(toUpdate)

        // Füge Observer hinzu
        var projectCommandQueue = ProjectCommandQueue()
        // Add Observer twice
        projectCommandQueue.registerObserver(observer)
        projectCommandQueue.registerObserver(observer)

        // Prüfe ob update() bei Observer ausgeführt wird
        Assert.assertEquals(toUpdate.isUpdated(), false)
        Assert.assertEquals(projectCommandQueue.getQueueLength(), 0)
        var projectCommand = " Project Command: 1"
        projectCommandQueue.addProjectCommand(projectCommand)
        Assert.assertEquals(projectCommandQueue.getQueueLength(), 1)
        Assert.assertEquals(toUpdate.getUpdated(), 1)


        // Dies sollte nun den Controller entfernen: -> Update sollte nicht nochmal ausgeführt werden
        projectCommandQueue.unregisterObserver(observer)
        var projectCommand2 = "Project Command: 2"
        projectCommandQueue.addProjectCommand(projectCommand2)
        Assert.assertEquals(1, toUpdate.getUpdated())
    }
}