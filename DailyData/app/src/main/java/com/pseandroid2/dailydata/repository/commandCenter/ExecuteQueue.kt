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

import com.pseandroid2.dailydata.remoteDataSource.queue.ProjectCommandQueueObserver
import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.repository.commandCenter.commands.CommandWrapper
import com.pseandroid2.dailydata.repository.commandCenter.commands.ProjectCommand
import kotlinx.coroutines.launch

class ExecuteQueue(
    override val repositoryViewModelAPI: RepositoryViewModelAPI,
    val publishQueue: PublishQueue
) :
    CommandQueue(repositoryViewModelAPI), ProjectCommandQueueObserver {
    override suspend fun performCommandAction(command: ProjectCommand) {
        command.execute()
    }

    //No contingency plan required because execution will always succeed
    override suspend fun commandActionSucceedsProbably(): Boolean {
        return true
    }

    //No contingency plan required because execution will always succeed
    override suspend fun beforeCommandActionAttempt(command: ProjectCommand) {}

    //No contingency plan required because execution will always succeed
    override suspend fun commandFailedAction(command: ProjectCommand) {}

    override fun update() {
        while (
            @Suppress("DEPRECATION")
            repositoryViewModelAPI.remoteDataSourceAPI.getProjectCommandQueueLength() > 0
        ) {
            val projectCommandInfo =
                @Suppress("DEPRECATION")
                repositoryViewModelAPI.remoteDataSourceAPI.getProjectCommandFromQueue()!!
            var command = CommandWrapper.fromJson(projectCommandInfo.projectCommand)
            command =
                CommandUtility.setServerInfo(command, projectCommandInfo, repositoryViewModelAPI)
            scope.launch {
                add(command)
            }
        }
    }
}