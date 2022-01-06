package com.pseandroid2.dailydata.remoteDataSource.queue.observerLogic.projectCommand

import com.pseandroid2.dailydata.remoteDataSource.queue.ProjectCommandQueue
import com.pseandroid2.dailydata.remoteDataSource.queue.observerLogic.UpdatedByObserver_ForTesting
import org.junit.Assert
import org.junit.Test

class ProjectCommadObserverTests_AddTenObserver {
    @Test
    fun addTenObserver() {
        var toUpdateObjects = mutableListOf<UpdatedByObserver_ForTesting>()

        // Erstelle Observer
        val observers: MutableList<ProjectCommandQueueObserver_ForTesting> = mutableListOf<ProjectCommandQueueObserver_ForTesting>()
        for (idx in 1..10) {
            val toUpdate = UpdatedByObserver_ForTesting()
            observers.add(ProjectCommandQueueObserver_ForTesting(toUpdate))
            toUpdateObjects.add(toUpdate)
        }

        // Füge Observer hinzu
        var projectCommandQueue = ProjectCommandQueue()
        observers.forEach {
            projectCommandQueue.registerObserver(it)
        }

        // Prüfe ob update() bei Observer ausgeführt wird:
        toUpdateObjects.forEach {
            Assert.assertEquals(it.isUpdated(), false)
        }

        Assert.assertEquals(projectCommandQueue.getQueueLength(), 0)
        var projectCommand = "Project Command: 1"
        projectCommandQueue.addProjectCommand(projectCommand)
        Assert.assertEquals(projectCommandQueue.getQueueLength(), 1)

        // Prüfe ob alle toUpdateObjects geupdated wurden
        toUpdateObjects.forEach {
            Assert.assertEquals(it.isUpdated(), true)
        }

    }
}


