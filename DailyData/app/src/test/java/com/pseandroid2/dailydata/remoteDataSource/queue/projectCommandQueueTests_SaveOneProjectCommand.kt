package com.pseandroid2.dailydata.remoteDataSource.queue

import org.junit.Assert
import org.junit.Test

class projectCommandQueueTests_SaveOneProjectCommand {
    @Test
    fun saveOneProjectCommand() {
        val projectCommandQueue = ProjectCommandQueue()

        val projectCommand = "projectCommand: 1"

        Assert.assertEquals(projectCommandQueue.getQueueLength(), 0)

        projectCommandQueue.addProjectCommand(projectCommand)

        Assert.assertEquals(projectCommandQueue.getQueueLength(), 1)

        val returnedProjectCommand = projectCommandQueue.getProjectCommand()

        Assert.assertEquals(projectCommand, returnedProjectCommand)
        Assert.assertEquals(projectCommandQueue.getQueueLength(), 0)
    }
}