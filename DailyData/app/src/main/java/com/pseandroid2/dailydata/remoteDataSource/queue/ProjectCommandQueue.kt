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

class ProjectCommandQueue {
    private val projectCommands: MutableList<ProjectCommandInfo> = mutableListOf<ProjectCommandInfo>() // Speichert JSONs von FetchRequests
    private val observers: MutableList<ProjectCommandQueueObserver> = mutableListOf<ProjectCommandQueueObserver>()

    // Queue Logic
    /**
     * @return ProjectCommandInfo?: Gibt einen ProjectCommand aus der Queue aus (und entfernt diesen aus der Queue)
     *                              Ist kein ProjectCommand mehr in der Queue enthalten wird null ausgegeben
     */
    fun getProjectCommand(): ProjectCommandInfo? {
        if (projectCommands.isNotEmpty()) {
            return projectCommands.removeAt(0)
        }
        return null
    }

    /**
     * @param projectCommand: Der ProjectCommand, der in die Queue hinzugefügt werden soll
     */
    fun addProjectCommand(projectCommand: ProjectCommandInfo) {
                // TODO: Teste, ob der String schon in der Queue vorhanden ist (find element) und füge es gegebenfalls nicht hinzu
                /* //TODO Wenn Projectcommands eindeutig per ID unterscheidbar sind, kann man zuerst den Vergleich durchführen
                if (!projectCommands.contains(projectCommand)) {
                projectCommands.add(projectCommand)
                 */
        projectCommands.add(projectCommand)
        notifyObservers()
    }

    /**
     * @return INT: Die Länge der ProjectCommandQueue
     */
    fun getQueueLength(): Int {
        return projectCommands.size
    }

    // Observer Logic
    /**
     * @param observer: Der Observer, der zur ProjectCommandQueue hinzugefügt werden soll
     */
    fun registerObserver(observer: ProjectCommandQueueObserver) {
        if (!observers.contains(observer)) {
            observers.add(observer)
        }
    }

    /**
     * @param observer: Der Observer, der von der ProjectCommandQueue entfernt werden soll
     */
    fun unregisterObserver(observer: ProjectCommandQueueObserver) {
        while (observers.contains(observer)) {
            observers.remove(observer)
        }
    }

    /**
     * Notifies all registered observers of the ProjectCommandQueue
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