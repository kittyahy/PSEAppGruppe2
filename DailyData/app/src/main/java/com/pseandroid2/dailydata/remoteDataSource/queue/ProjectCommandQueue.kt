package com.pseandroid2.dailydata.remoteDataSource.queue

class ProjectCommandQueue {
    private val projectCommands: MutableList<String> = mutableListOf<String>() // Speichert JSONs von FetchRequests
    private val observers: MutableList<ProjectCommandQueueObserver> = mutableListOf<ProjectCommandQueueObserver>()

    // Queue Logic
    fun getProjectCommand(): String {
        if (projectCommands.isNotEmpty()) {
            return projectCommands.removeAt(0)
        }
        return ""
    }

    fun addProjectCommand(projectCommand: String) {
        if (projectCommand != "") {
                // TODO: Teste, ob der String schon in der Queue vorhanden ist (find element) und füge es gegebenfalls nicht hinzu
                /* //TODO Wenn Projectcommands eindeutig per ID unterscheidbar sind, kann man zuerst den Vergleich durchführen
                if (!projectCommands.contains(projectCommand)) {
                projectCommands.add(projectCommand)
                 */

            projectCommands.add(projectCommand)
        }
    }

    fun getQueueLength(): Int {
        return projectCommands.size
    }

    // Observer Logic
    fun registerObserver(observer: ProjectCommandQueueObserver) {
        if (!observers.contains(observer)) {
            observers.add(observer)
        }
    }

    fun unregisterObserver(observer: ProjectCommandQueueObserver) {
        while (observers.contains(observer)) {
            observers.remove(observer)
        }
    }
}