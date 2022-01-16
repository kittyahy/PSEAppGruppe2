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
import java.time.LocalDateTime

class projectCommandQueueTests_SaveTenProjectCommand {
    @Test
    fun saveOneProjectCommand() {
        val projectCommandQueue = ProjectCommandQueue()

        val projectCommands: MutableList<ProjectCommandInfo> = mutableListOf<ProjectCommandInfo>()
        for (idx in 1..10) {
            val projectCommand: ProjectCommandInfo = ProjectCommandInfo(LocalDateTime.parse("0000-00-00 00:00"),
                LocalDateTime.parse("0000-00-00 00:00"), "", false, idx.toString())

            projectCommands.add(projectCommand)
            projectCommandQueue.addProjectCommand(projectCommand)
        }

        Assert.assertEquals(projectCommandQueue.getQueueLength(), 10)

        projectCommands.forEach {
            Assert.assertEquals(projectCommandQueue.getProjectCommand(), it)
        }

        Assert.assertEquals(projectCommandQueue.getQueueLength(), 0)
    }
}