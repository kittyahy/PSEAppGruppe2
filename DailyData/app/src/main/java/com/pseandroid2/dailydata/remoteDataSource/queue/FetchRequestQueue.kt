package com.pseandroid2.dailydata.remoteDataSource.queue

class FetchRequestQueue {
    private val fetchRequests: MutableList<String> = mutableListOf<String>() // Speichert JSONs von FetchRequests
    private val observers: MutableList<FetchRequestQueueObserver> = mutableListOf<FetchRequestQueueObserver>()

    // Queue Logic
    fun getFetchRequest(): String {
        if (fetchRequests.isNotEmpty()) {
            return fetchRequests.removeAt(0)
        }
        return ""
    }

    fun addFetchRequest(fetchRequest: String) {
        if (fetchRequest != "") {
            // TODO: Teste, ob der String schon in der Queue vorhanden ist (find element) und füge es gegebenfalls nicht hinzu
            if (!fetchRequests.contains(fetchRequest)) {
                fetchRequests.add(fetchRequest)
            }
        }
    }

    fun getQueueLength(): Int {
        return fetchRequests.size
    }

    // Observer Logic
    fun registerObserver(observer: FetchRequestQueueObserver) {
        if (!observers.contains(observer)) {
            observers.add(observer)
        }
    }

    fun unregisterObserver(observer: FetchRequestQueueObserver) {
        while (observers.contains(observer)) {
            observers.remove(observer)
        }
    }
}