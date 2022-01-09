package com.pseandroid2.dailydata.remoteDataSource.queue.observerLogic.projectCommand

import com.pseandroid2.dailydata.remoteDataSource.queue.ProjectCommandQueue
import com.pseandroid2.dailydata.remoteDataSource.queue.observerLogic.UpdatedByObserver_ForTesting
import org.junit.Assert
import org.junit.Test

class ProjectCommandObserverTests_SaveEmptyProjectCommand {
    // No Update expected:
    @Test
    fun saveEmptyProjectCommand() {
        // Erstelle Observer
        var toUpdate = UpdatedByObserver_ForTesting()
        var observer = ProjectCommandQueueObserver_ForTesting(toUpdate)

        // Füge Observer hinzu
        var projectCommandQueue = ProjectCommandQueue()
        projectCommandQueue.registerObserver(observer)

        // Prüfe ob update() bei Observer ausgeführt wird
        Assert.assertEquals(toUpdate.isUpdated(), false)

        Assert.assertEquals(projectCommandQueue.getQueueLength(), 0)
        var projectCommand = ""
        projectCommandQueue.addProjectCommand(projectCommand)

        Assert.assertEquals(toUpdate.isUpdated(), false)
    }
}