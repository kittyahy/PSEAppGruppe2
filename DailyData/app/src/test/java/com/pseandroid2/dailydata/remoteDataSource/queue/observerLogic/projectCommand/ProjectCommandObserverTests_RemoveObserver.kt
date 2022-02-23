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
import com.pseandroid2.dailydata.remoteDataSource.queue.observerLogic.UpdatedByObserver_ForTesting
import org.junit.Assert
import org.junit.Test
import java.time.LocalDateTime

class ProjectCommandObserverTests_RemoveObserver {
    @Test
    fun removeOneObserver() {
        // Erstelle Observer
        var toUpdate = UpdatedByObserver_ForTesting()
        var observer = ProjectCommandQueueObserver_ForTesting(toUpdate)

        // Füge Observer hinzu
        var projectCommandQueue = ProjectCommandQueue()
        projectCommandQueue.registerObserver(observer)

        // Prüfe ob update() bei Observer ausgeführt wird
        Assert.assertEquals(toUpdate.isUpdated(), false)
        Assert.assertEquals(projectCommandQueue.getQueueLength(), 0)
        var projectCommand1: ProjectCommandInfo = ProjectCommandInfo(projectCommand = "1")
        projectCommandQueue.addProjectCommand(projectCommand1)
        Assert.assertEquals(projectCommandQueue.getQueueLength(), 1)
        Assert.assertEquals(toUpdate.getUpdated(), 1)


        projectCommandQueue.unregisterObserver(observer)
        var projectCommand2: ProjectCommandInfo = ProjectCommandInfo(projectCommand = "2")
        projectCommandQueue.addProjectCommand(projectCommand2)

        Assert.assertEquals(toUpdate.getUpdated(), 1)
    }
}