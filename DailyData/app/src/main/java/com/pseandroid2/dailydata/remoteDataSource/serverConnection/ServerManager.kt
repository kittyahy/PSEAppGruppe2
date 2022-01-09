package com.pseandroid2.dailydata.remoteDataSource.serverConnection

import com.pseandroid2.dailydata.remoteDataSource.queue.FetchRequestQueue
import com.pseandroid2.dailydata.remoteDataSource.queue.FetchRequestQueueObserver
import com.pseandroid2.dailydata.remoteDataSource.queue.ProjectCommandQueue
import com.pseandroid2.dailydata.remoteDataSource.queue.ProjectCommandQueueObserver

class ServerManager {
    private val restAPI: RESTAPI

    private val fetchRequestQueue: FetchRequestQueue
    private val projectCommandQueue: ProjectCommandQueue

    init {
        restAPI = RESTAPI()

        fetchRequestQueue = FetchRequestQueue()
        projectCommandQueue = ProjectCommandQueue()
    }


    // -----------------------------ObserverLogic-------------------------------
    fun addObserverToFetchRequestQueue(observer: FetchRequestQueueObserver) {
        fetchRequestQueue.registerObserver(observer)
    }
    fun unregisterObserverFromFetchRequestQueue(observer: FetchRequestQueueObserver) {
        fetchRequestQueue.unregisterObserver(observer)
    }

    fun getFetchRequestQueueLength(): Int {
        return fetchRequestQueue.getQueueLength()
    }

    fun addObserverToProjectCommandQueue(observer: ProjectCommandQueueObserver) {
        projectCommandQueue.registerObserver(observer)
    }
    fun unregisterObserverFromProjectCommandQueue(observer: ProjectCommandQueueObserver) {
        projectCommandQueue.unregisterObserver(observer)
    }

    fun getProjectCommandQueueLength(): Int {
        return projectCommandQueue.getQueueLength()
    }
}