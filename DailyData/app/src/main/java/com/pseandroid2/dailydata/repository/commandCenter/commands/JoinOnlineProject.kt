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

package com.pseandroid2.dailydata.repository.commandCenter.commands

import com.pseandroid2.dailydata.model.project.InMemoryProject
import com.pseandroid2.dailydata.model.project.Project
import com.pseandroid2.dailydata.model.users.SimpleUser
import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.first
import com.pseandroid2.dailydata.repository.viewModelAPI.ProjectHandler


class JoinOnlineProject(
    private val onlineID: Long,
    api: RepositoryViewModelAPI
) : ProjectCommand(-1, repositoryViewModelAPI = api) {
    companion object {
        fun isIllegal(project: ProjectHandler): Boolean {
            return isIllegal()
        }

        const val isAdminOperation: Boolean = false

        const val publishable: Boolean = false
    }

    override suspend fun execute() {
        @Suppress("DEPRECATION")
        repositoryViewModelAPI.remoteDataSourceAPI.joinProject(onlineID)
        val admin = SimpleUser(
            @Suppress("DEPRECATION")
            repositoryViewModelAPI.remoteDataSourceAPI.getProjectAdmin(onlineID),
            "ADMIN"
        )
        val onlineProject =
            InMemoryProject(admin = admin)
        val id: Int = onlineProject.writeToDBAsync(repositoryViewModelAPI.projectHandler).await()
        @Suppress("DEPRECATION")
        repositoryViewModelAPI.appDataBase.projectDataDAO().setOnlineID(id, onlineID)
        super.execute()
    }

    override suspend fun publish(): Boolean {
        return super.publish() && publishable
    }


}
