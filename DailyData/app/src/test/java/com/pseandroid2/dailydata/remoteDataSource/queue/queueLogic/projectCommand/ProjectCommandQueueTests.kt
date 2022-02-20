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
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ProjectCommandQueueTests {/*

    private var projectCommandQueue = ProjectCommandQueue()

    private var projectCommand1 = ProjectCommandInfo(, projectCommand = "Project Command: 1",)

    @Before
    fun setup() {
        Assert.assertEquals(0, projectCommandQueue.getQueueLength())
    }

    @After
    fun cleanUp() {
        while (projectCommandQueue.getQueueLength() > 0) {
            projectCommandQueue.getProjectCommand()
        }
        Assert.assertEquals(0, projectCommandQueue.getQueueLength())
    }

    @Test
    fun saveOneProjectCommand() {
        projectCommandQueue.addProjectCommand(projectCommand1)
        Assert.assertEquals(1, projectCommandQueue.getQueueLength())
        Assert.assertEquals(projectCommand1, projectCommandQueue.getProjectCommand())

        Assert.assertEquals(0, projectCommandQueue.getQueueLength())
    }

    @Test
    fun saveTenProjectCommands() {
        val projectCommands: MutableList<ProjectCommandInfo> = mutableListOf()

        for (idx in 1..10) {
            val projectCommand1 = ProjectCommandInfo(, projectCommand = "Project Command: $idx",)
            projectCommands.add(projectCommand1)
            projectCommandQueue.addProjectCommand(projectCommand1)
        }

        Assert.assertEquals(10, projectCommandQueue.getQueueLength())

        projectCommands.forEach {
            Assert.assertEquals(projectCommandQueue.getProjectCommand(), it)
        }

        Assert.assertEquals(0, projectCommandQueue.getQueueLength())
    }

    @Test
    fun getFromEmptyProjectCommandQueue() {
        Assert.assertEquals(null, projectCommandQueue.getProjectCommand())

        Assert.assertEquals(0, projectCommandQueue.getQueueLength())
    }*/
}