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
 * A queue for project commands which are received from the server
 */
class FetchRequestQueue {
    private val fetchRequests: MutableList<FetchRequest> = mutableListOf<FetchRequest>()
    private val observers: MutableList<FetchRequestQueueObserver> = mutableListOf<FetchRequestQueueObserver>()

    // Queue Logic
    /**
     * @return String: Gibt eine FetchRequest aus der Queue aus (und entfernt diese aus der Queue)
     *                              Ist keine FetchRequest mehr in der Queue enthalten wird null ausgegeben
     */
    fun getFetchRequest(): FetchRequest? {
        if (fetchRequests.isNotEmpty()) {
            return fetchRequests.removeAt(0)
        }
        return null
    }

    /**
     * @param fetchRequest: Die FetchRequest, die in die Queue hinzugefügt werden soll
     */
    fun addFetchRequest(fetchRequest: FetchRequest) {
        if (!fetchRequests.contains(fetchRequest)) {
            fetchRequests.add(fetchRequest)
            notifyObservers()
        }
    }

    /**
     * @return Int: Die Länge der FetchRequestQueue
     */
    fun getQueueLength(): Int {
        return fetchRequests.size
    }

    // Observer Logic
    /**
     * @param observer: Der Observer, der zur FetchRequestQueue hinzugefügt werden soll
     */
    fun registerObserver(observer: FetchRequestQueueObserver) {
        if (!observers.contains(observer)) {
            observers.add(observer)
        }
    }

    /**
     * @param observer: Der Observer, der von der FetchRequestQueue entfernt werden soll
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
            if (it == null) { // TODO: Prüfe, ob das nicht doch relevant ist (Wenn Observer gelöscht wird)
                observers.remove(it)
            } else {
                it.update()
            }
        }
    }
}