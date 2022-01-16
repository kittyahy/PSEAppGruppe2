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

class FetchRequestQueue {
    private val fetchRequests: MutableList<String> = mutableListOf<String>() // Speichert JSONs von FetchRequests
    private val observers: MutableList<FetchRequestQueueObserver> = mutableListOf<FetchRequestQueueObserver>()

    // Queue Logic
    /**
     * @return String: Gibt eine FetchRequest aus der Queue aus (und entfernt diese aus der Queue)
     *                              Ist keine FetchRequest mehr in der Queue enthalten wird "" ausgegeben
     */
    fun getFetchRequest(): String {
        if (fetchRequests.isNotEmpty()) {
            return fetchRequests.removeAt(0)
        }
        return ""
    }

    /**
     * @param fetchRequest: Die FetchRequest, die in die Queue hinzugefügt werden soll als
     */
    fun addFetchRequest(fetchRequest: String) {
        if (fetchRequest != "") {
            // TODO: Teste, ob der String schon in der Queue vorhanden ist (find element) und füge es gegebenfalls nicht hinzu
            if (!fetchRequests.contains(fetchRequest)) {
                fetchRequests.add(fetchRequest)
                notifyObservers()
            }
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
     * Benachrichtige alle registrierte Observer der FetchRequest Queue
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