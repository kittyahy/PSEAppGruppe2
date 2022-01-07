package com.pseandroid2.dailydata.remoteDataSource.queue.observerLogic

class UpdatedByObserver_ForTesting {
    private var updated: Int = 0

    fun update() {
        ++updated;
    }

    fun isUpdated(): Boolean {
        if (updated == 0) {
            return  false
        }
        return true
    }

    fun getUpdated(): Int {
        return updated
    }
}