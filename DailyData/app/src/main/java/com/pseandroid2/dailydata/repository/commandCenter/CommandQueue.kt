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

package com.pseandroid2.dailydata.repository.commandCenter

import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.repository.commandCenter.commands.ProjectCommand
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

abstract class CommandQueue(open val repositoryViewModelAPI: RepositoryViewModelAPI) {
    private var commandQueue: MutableList<ProjectCommand> = ArrayList<ProjectCommand>()
    private val mutex = Mutex()
    protected val scope = CoroutineScope(Dispatchers.IO)

    init {
        scope.launch {
            while (true) {
                if (commandQueue.isNotEmpty()) {
                    //Blocking only during queue action not during the actual execution
                    val first = mutex.withLock {
                        val first = commandQueue[0]
                        commandQueue.remove(first)
                        first
                    }
                    beforeCommandActionAttempt(first)
                    if (commandActionSucceedsProbably())
                        try {
                            performCommandAction(first)
                        } catch (e: ServerUnreachableException) {
                            commandFailedAction(first)
                            //add(first)
                            //lastServerConnection = LocalDateTime.MIN
                        }
                }
            }
        }
    }

    abstract suspend fun performCommandAction(command: ProjectCommand)
    abstract suspend fun commandActionSucceedsProbably(): Boolean
    abstract suspend fun beforeCommandActionAttempt(command: ProjectCommand)
    abstract suspend fun commandFailedAction(command: ProjectCommand)

    suspend fun add(projectCommand: ProjectCommand) {
        mutex.withLock {
            commandQueue.add(projectCommand)
        }
    }


}