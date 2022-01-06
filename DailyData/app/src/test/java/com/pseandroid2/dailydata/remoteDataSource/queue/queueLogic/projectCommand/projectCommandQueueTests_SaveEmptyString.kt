package com.pseandroid2.dailydata.remoteDataSource.queue.queueLogic.projectCommand

import com.pseandroid2.dailydata.remoteDataSource.queue.ProjectCommandQueue
import org.junit.Assert
import org.junit.Test

class projectCommandQueueTests_SaveEmptyString {
    @Test
    fun saveOneProjectCommand() {
        val projectCommandQueue = ProjectCommandQueue()

        val projectCommand = ""

        Assert.assertEquals(projectCommandQueue.getQueueLength(), 0)

        projectCommandQueue.addProjectCommand(projectCommand)

        Assert.assertEquals(projectCommandQueue.getQueueLength(), 0)

        val returnedProjectCommand = projectCommandQueue.getProjectCommand()

        Assert.assertEquals(projectCommand, returnedProjectCommand)
        Assert.assertEquals(projectCommandQueue.getQueueLength(), 0)
    }
}