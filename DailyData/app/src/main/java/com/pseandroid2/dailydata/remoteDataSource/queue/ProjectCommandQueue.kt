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
     * Returns a project command from the queue and removes it from the queue
     *
     * @return ProjectCommandInfo?: The project command from the queue. If the queue is empty returns null
     */
    fun getProjectCommand(): ProjectCommandInfo? {
        if (projectCommands.isNotEmpty()) {
            return projectCommands.removeAt(0)
        }
        return null
    }

    /**
     * Adds a project command to the queue and notifies all observer
     *
     * @param projectCommand: The project command that should be added to the queue
     */
    fun addProjectCommand(projectCommand: ProjectCommandInfo) {
        projectCommands.add(projectCommand)
        notifyObservers()
    }

    /**
     * @return INT: How many elements there are in the queue
     */
    fun getQueueLength(): Int {
        return projectCommands.size
    }

    // Observer Logic
    /**
     * Registers a new observer to the queue. If the observer is already observing it won't be added again
     *
     * @param observer: The observer that should be added to the project command queue
     */
    fun registerObserver(observer: ProjectCommandQueueObserver) {
        if (!observers.contains(observer)) {
            observers.add(observer)
        }
    }

    /**
     * Unregisters an observer from the queue
     *
     * @param observer: The observer that should be removed from the queue
     */
    fun unregisterObserver(observer: ProjectCommandQueueObserver) {
        observers.remove(observer)
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