package com.pseandroid2.dailydata.remoteDataSource.queue.observerLogic.projectCommand

import com.pseandroid2.dailydata.remoteDataSource.queue.ProjectCommandQueue
import com.pseandroid2.dailydata.remoteDataSource.queue.observerLogic.UpdatedByObserver_ForTesting
import org.junit.Assert
import org.junit.Test

class ProjectCommandObserverTests_AddDuplicateObserver {
    @Test
    fun addDuplicateObserver() {
        // Erstelle Observer
        var toUpdate = UpdatedByObserver_ForTesting()
        var observer = ProjectCommandQueueObserver_ForTesting(toUpdate)

        // Füge Observer hinzu
        var projectCommandQueue = ProjectCommandQueue()
        // Add Observer twice
        projectCommandQueue.registerObserver(observer)
        projectCommandQueue.registerObserver(observer)

        // Prüfe ob update() bei Observer ausgeführt wird
        Assert.assertEquals(toUpdate.isUpdated(), false)
        Assert.assertEquals(projectCommandQueue.getQueueLength(), 0)
        var projectCommand = " Project Command: 1"
        projectCommandQueue.addProjectCommand(projectCommand)
        Assert.assertEquals(projectCommandQueue.getQueueLength(), 1)
        Assert.assertEquals(toUpdate.getUpdated(), 1)


        // Dies sollte nun den Controller entfernen: -> Update sollte nicht nochmal ausgeführt werden
        projectCommandQueue.unregisterObserver(observer)
        var projectCommand2 = "Project Command: 2"
        projectCommandQueue.addProjectCommand(projectCommand2)
        Assert.assertEquals(1, toUpdate.getUpdated())
    }
}