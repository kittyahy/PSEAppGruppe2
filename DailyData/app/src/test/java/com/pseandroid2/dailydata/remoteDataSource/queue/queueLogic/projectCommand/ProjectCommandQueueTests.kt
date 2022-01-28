package com.pseandroid2.dailydata.remoteDataSource.queue.queueLogic.projectCommand

import com.pseandroid2.dailydata.remoteDataSource.queue.ProjectCommandInfo
import com.pseandroid2.dailydata.remoteDataSource.queue.ProjectCommandQueue
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ProjectCommandQueueTests {

    var projectCommandQueue = ProjectCommandQueue()

    var projectCommand1 = ProjectCommandInfo(projectCommand = "Project Command: 1")
    var projectCommand2 = ProjectCommandInfo(projectCommand = "Project Command: 2")

    @Before
    fun setup() {
        Assert.assertEquals(0, projectCommandQueue.getQueueLength())
    }

    @After
    fun cleanUp() {
        while(projectCommandQueue.getQueueLength() > 0) {
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
            var projectCommand1 = ProjectCommandInfo(projectCommand = "Project Command: $idx")
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
    fun getFromEmptyprojectCommandQueue() {
        Assert.assertEquals(null, projectCommandQueue.getProjectCommand())

        Assert.assertEquals(0, projectCommandQueue.getQueueLength())
    }
}