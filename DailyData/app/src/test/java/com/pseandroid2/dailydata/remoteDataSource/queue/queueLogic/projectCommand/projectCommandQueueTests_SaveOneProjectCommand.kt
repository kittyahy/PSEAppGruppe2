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

package com.pseandroid2.dailydata.remoteDataSource.queue.queueLogic.projectCommand

import com.pseandroid2.dailydata.remoteDataSource.queue.ProjectCommandInfo
import com.pseandroid2.dailydata.remoteDataSource.queue.ProjectCommandQueue
import org.junit.Assert
import org.junit.Test

class projectCommandQueueTests_SaveOneProjectCommand {
    @Test
    fun saveOneProjectCommand() {
        val projectCommandQueue = ProjectCommandQueue()

        val projectCommand: ProjectCommandInfo = ProjectCommandInfo()

        Assert.assertEquals(projectCommandQueue.getQueueLength(), 0)

        projectCommandQueue.addProjectCommand(projectCommand)

        Assert.assertEquals(projectCommandQueue.getQueueLength(), 1)

        val returnedProjectCommand = projectCommandQueue.getProjectCommand()

        Assert.assertEquals(projectCommand, returnedProjectCommand)
        Assert.assertEquals(projectCommandQueue.getQueueLength(), 0)
    }
}