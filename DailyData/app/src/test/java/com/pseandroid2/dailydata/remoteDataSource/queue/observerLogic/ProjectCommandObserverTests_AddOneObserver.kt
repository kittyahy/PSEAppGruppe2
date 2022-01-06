package com.pseandroid2.dailydata.remoteDataSource.queue.observerLogic

import com.pseandroid2.dailydata.remoteDataSource.queue.ProjectCommandQueue
import com.pseandroid2.dailydata.remoteDataSource.userManager.FirebaseManager
import org.junit.Assert
import org.junit.Test

class ProjectCommandObserverTests_AddOneObserver {
    @Test
    fun addOneObserver() {
        // Erstelle Observer
        var toUpdate = UpdatedByObserver_ForTesting()
        var observer = ProjectCommandQueueObserver_ForTesting(toUpdate)

        // Füge Observer hinzu
        var projectCommandQueue = ProjectCommandQueue()
        projectCommandQueue.registerObserver(observer)

        // Prüfe ob update() bei Observer ausgeführt wird
        Assert.assertEquals(toUpdate.isUpdated(), false)
        Assert.assertEquals(projectCommandQueue.getQueueLength(), 0)
        var projectCommand = "Project Command: 1"
        projectCommandQueue.addProjectCommand(projectCommand)
        Assert.assertEquals(projectCommandQueue.getQueueLength(), 1)
        Assert.assertEquals(toUpdate.isUpdated(), true)
    }
}
