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

/**
 * A queue for project commands which are received from the server
 */
class ProjectCommandQueue {
    private val projectCommands: MutableList<ProjectCommandInfo> = mutableListOf()
    private val observers: MutableList<ProjectCommandQueueObserver> = mutableListOf()

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
            it.update()
        }
    }
}