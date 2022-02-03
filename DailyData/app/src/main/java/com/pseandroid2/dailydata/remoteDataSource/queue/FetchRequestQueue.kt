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

package com.pseandroid2.dailydata.remoteDataSource.queue

import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverReturns.FetchRequest

/**
 * A queue for fetch requests which are received from the server
 */
class FetchRequestQueue {
    private val fetchRequests: MutableList<FetchRequest> = mutableListOf()
    private val observers: MutableList<FetchRequestQueueObserver> = mutableListOf()

    // Queue Logic
    /**
     * Returns a fetch request from the queue and removes it from the queue
     *
     * @return FetchRequest: The fetch request from the queue. If the queue is empty returns null
     */
    fun getFetchRequest(): FetchRequest? {
        if (fetchRequests.isNotEmpty()) {
            return fetchRequests.removeAt(0)
        }
        return null
    }

    /**
     * Adds an fetch request the the queue and notifies the observer if it was a new fetch request which wasn't already in the queue
     *
     * @param fetchRequest: The fetch request that should be added to the queue
     */
    fun addFetchRequest(fetchRequest: FetchRequest) {
        if (!fetchRequests.contains(fetchRequest)) {
            fetchRequests.add(fetchRequest)
            notifyObservers()
        }
    }

    /**
     * @return Int: How many elements are in the queue
     */
    fun getQueueLength(): Int {
        return fetchRequests.size
    }

    // Observer Logic
    /**
     * Registers a new observer to the queue. If the observer is already observing it won't be added again
     *
     * @param observer: The observer that should be added to the fetch request queue
     */
    fun registerObserver(observer: FetchRequestQueueObserver) {
        if (!observers.contains(observer)) {
            observers.add(observer)
        }
    }

    /**
     * Unregisters an observer from the queue
     *
     * @param observer: The observer that should be removed from the queue
     */
    fun unregisterObserver(observer: FetchRequestQueueObserver) {
        while (observers.contains(observer)) {
            observers.remove(observer)
        }
    }

    /**
     * Notifies all registered observers of the FetchRequestQueue
     */
    private fun notifyObservers() {
        observers.forEach {
            it.update()
        }
    }
}