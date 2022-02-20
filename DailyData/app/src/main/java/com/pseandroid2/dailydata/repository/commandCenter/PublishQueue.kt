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
import com.pseandroid2.dailydata.repository.commandCenter.commands.CommandWrapper
import com.pseandroid2.dailydata.repository.commandCenter.commands.ProjectCommand
import kotlinx.coroutines.delay
import java.time.Duration
import java.time.LocalDateTime
import java.time.temporal.TemporalAmount

class PublishQueue(override val repositoryViewModelAPI: RepositoryViewModelAPI) :
    CommandQueue(repositoryViewModelAPI) {

    private var lastServerConnection: LocalDateTime = LocalDateTime.MIN
    private var serverConnectionTolerance: TemporalAmount = Duration.ZERO.plusMinutes(5)
    private val untilRetrySleepForTime: Long = 1000 * 60 * 5
    override suspend fun performCommandAction(command: ProjectCommand) {
        val wrapper = CommandWrapper(command)
        val commandJson = wrapper.toJson()
        @Suppress("DEPRECATION")
        repositoryViewModelAPI.remoteDataSourceAPI.sendCommandsToServer(
            repositoryViewModelAPI.appDataBase.projectDataDAO().getOnlineId(command.projectID!!),
            listOf(commandJson)
        )
    }


    override suspend fun commandActionSucceedsProbably(): Boolean {
        if (lastServerConnection.plus(serverConnectionTolerance) > LocalDateTime.now()) {
            return true
        }
        if (repositoryViewModelAPI.remoteDataSourceAPI.connectionToServerPossible()) {
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