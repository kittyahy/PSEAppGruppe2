package com.pseandroid2.dailydata.remoteDataSource.queue

import org.junit.Assert
import org.junit.Test

class projectCommandQueueTests_GetFromEmptyQueue {
    @Test
    fun saveOneProjectCommand() {
        val projectCommandQueue = ProjectCommandQueue()

        Assert.assertEquals(projectCommandQueue.getQueueLength(), 0)

        val returnedProjectCommand = projectCommandQueue.getProjectCommand()

        Assert.assertEquals(returnedProjectCommand,"")

        Assert.assertEquals(projectCommandQueue.getQueueLength(), 0)
    }
}