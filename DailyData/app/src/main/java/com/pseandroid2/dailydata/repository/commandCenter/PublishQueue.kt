package com.pseandroid2.dailydata.repository.commandCenter

import com.pseandroid2.dailydata.model.database.AppDataBase
import com.pseandroid2.dailydata.remoteDataSource.RemoteDataSourceAPI
import com.pseandroid2.dailydata.repository.commandCenter.commands.CommandWrapper
import com.pseandroid2.dailydata.repository.commandCenter.commands.IllegalOperationException
import com.pseandroid2.dailydata.repository.commandCenter.commands.ProjectCommand
import kotlinx.coroutines.delay
import java.time.Duration
import java.time.LocalDateTime
import java.time.temporal.TemporalAmount
import java.time.temporal.TemporalUnit

class PublishQueue(appDataBase: AppDataBase, remoteDataSourceAPI: RemoteDataSourceAPI) :
    CommandQueue(appDataBase, remoteDataSourceAPI) {

    private var lastServerConnection: LocalDateTime = LocalDateTime.MIN
    private var serverConnectionTolerance: TemporalAmount = Duration.ZERO.plusMinutes(5)
    private val untilRetrySleepForTime: Long = 1000 * 60 * 5
    override suspend fun performCommandAction(command: ProjectCommand) {
        val wrapper = CommandWrapper(command)
        val commandJson = wrapper.toJson()
        if (command.onlineProjectID == null) {
            throw IllegalOperationException()
        }
        remoteDataSourceAPI.sendCommandsToServer(command.onlineProjectID!!, listOf(commandJson))
    }


    override suspend fun commandActionSucceedsProbably(): Boolean {
        if (lastServerConnection.plus(serverConnectionTolerance) > LocalDateTime.now()) {
            return true
        }
        if (remoteDataSourceAPI.connectionToServerPossible()) {
            lastServerConnection = LocalDateTime.now()
            return true
        }
        return false
    }

    override suspend fun beforeCommandActionAttempt(command: ProjectCommand) {
        lastServerConnection = LocalDateTime.now()
    }

    override suspend fun commandFailedAction(command: ProjectCommand) {
        super.add(command)
        lastServerConnection = LocalDateTime.MIN
        delay(untilRetrySleepForTime)
    }
}