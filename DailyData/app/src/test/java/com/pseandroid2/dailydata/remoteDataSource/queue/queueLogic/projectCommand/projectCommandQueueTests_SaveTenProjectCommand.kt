package com.pseandroid2.dailydata.remoteDataSource.queue.queueLogic.projectCommand

import com.pseandroid2.dailydata.remoteDataSource.queue.ProjectCommandQueue
import org.junit.Assert
import org.junit.Test

class projectCommandQueueTests_SaveTenProjectCommand {
    @Test
    fun saveOneProjectCommand() {
        val projectCommandQueue = ProjectCommandQueue()

        val projectCommands: MutableList<String> = mutableListOf<String>()
        for (idx in 1..10) {
            projectCommands.add("projectCommand$idx")
            projectCommandQueue.addProjectCommand("projectCommand$idx")
        }

        Assert.assertEquals(projectCommandQueue.getQueueLength(), 10)

        projectCommands.forEach {
            Assert.assertEquals(projectCommandQueue.getProjectCommand(), it)
        }

        Assert.assertEquals(projectCommandQueue.getQueueLength(), 0)
    }
}