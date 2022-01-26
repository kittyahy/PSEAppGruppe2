package com.pseandroid2.dailydata.repository.commandCenter.commands

import com.pseandroid2.dailydata.remoteDataSource.RemoteDataSourceAPI
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.time.LocalDateTime
import java.time.temporal.TemporalAmount

abstract class CommandQueue
    (val remoteDataSourceAPI: RemoteDataSourceAPI) {
    private var commandQueue: MutableList<ProjectCommand> = ArrayList<ProjectCommand>()
    private val mutex = Mutex()
    init {
        GlobalScope.launch {
            //Todo lösung finden was mit commands passieren soll, die vor beendigen der App nicht ausgeführt werden können
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
    abstract suspend fun commandActionSucceedsProbably() : Boolean
    abstract suspend fun beforeCommandActionAttempt(command: ProjectCommand)
    abstract suspend fun commandFailedAction(command: ProjectCommand)

    suspend fun add(projectCommand: ProjectCommand) {
        mutex.withLock {
            commandQueue.add(projectCommand)
        }
    }


}