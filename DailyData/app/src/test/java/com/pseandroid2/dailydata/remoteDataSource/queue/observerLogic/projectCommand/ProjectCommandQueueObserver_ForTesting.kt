package com.pseandroid2.dailydata.remoteDataSource.queue.observerLogic.projectCommand

import com.pseandroid2.dailydata.remoteDataSource.queue.ProjectCommandQueueObserver
import com.pseandroid2.dailydata.remoteDataSource.queue.observerLogic.UpdatedByObserver_ForTesting

class ProjectCommandQueueObserver_ForTesting(toUpdate: UpdatedByObserver_ForTesting): ProjectCommandQueueObserver  {
    var toUpdateObject = toUpdate
    @Override
    override fun update() {
        toUpdateObject.update()
    }
}