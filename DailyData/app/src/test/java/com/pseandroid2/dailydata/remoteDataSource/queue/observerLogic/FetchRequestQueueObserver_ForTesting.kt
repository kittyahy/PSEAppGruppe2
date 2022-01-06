package com.pseandroid2.dailydata.remoteDataSource.queue.observerLogic

import com.pseandroid2.dailydata.remoteDataSource.queue.FetchRequestQueueObserver
import com.pseandroid2.dailydata.remoteDataSource.queue.observerLogic.UpdatedByObserver_ForTesting

class FetchRequestQueueObserver_ForTesting(toUpdate: UpdatedByObserver_ForTesting): FetchRequestQueueObserver  {
    var toUpdateObject = toUpdate
    @Override
    override fun update() {
        toUpdateObject.update()
    }
}