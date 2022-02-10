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