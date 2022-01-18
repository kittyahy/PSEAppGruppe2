/*

    DailyData is an android app to easily create diagrams from data one has collected
    Copyright (C) 2022  Antonia Heiming, Anton Kadelbach, Arne Kuchenbecker, Merlin Opp, Robin Amman

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.

*/

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
    /**
     * @param observer: The observer that should be added to the FetchRequestQueue
     */
    fun addObserverToFetchRequestQueue(observer: FetchRequestQueueObserver) {
        fetchRequestQueue.registerObserver(observer)
    }

    /**
     * @param observer: The observer that should be removed from the FetchRequestQueue
     */
    fun unregisterObserverFromFetchRequestQueue(observer: FetchRequestQueueObserver) {
        fetchRequestQueue.unregisterObserver(observer)
    }

    /**
     * @return INT: The length of the FetchRequestQueue
     */
    fun getFetchRequestQueueLength(): Int {
        return fetchRequestQueue.getQueueLength()
    }

    /**
     * @param observer: The observer that should be added to the ProjectCommandQueue
     */
    fun addObserverToProjectCommandQueue(observer: ProjectCommandQueueObserver) {
        projectCommandQueue.registerObserver(observer)
    }

    /**
     * @param observer: The observer that should be removed from the ProjectCommandQueue
     */
    fun unregisterObserverFromProjectCommandQueue(observer: ProjectCommandQueueObserver) {
        projectCommandQueue.unregisterObserver(observer)
    }

    /**
     * @return INT: The length of the ProjectCommandQueue
     */
    fun getProjectCommandQueueLength(): Int {
        return projectCommandQueue.getQueueLength()
    }


    // ------------------------------ServerLogic--------------------------------
    /**
     * @return Boolean: If a server connection possible return true, else return false
     */
    fun greet(): Boolean {
        return restAPI.greet()
    }
}