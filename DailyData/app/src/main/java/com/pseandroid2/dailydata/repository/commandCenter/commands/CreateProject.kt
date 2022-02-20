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

import android.util.Log
import com.pseandroid2.dailydata.model.project.Project
import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.repository.commandCenter.PublishQueue
import com.pseandroid2.dailydata.util.Consts.LOG_TAG
import com.pseandroid2.dailydata.repository.viewModelAPI.ProjectHandler
import kotlinx.coroutines.flow.MutableSharedFlow

class CreateProject(
    private val project: Project,
    private val projectIDReturn: MutableSharedFlow<Int>? = null,
    api: RepositoryViewModelAPI
) : ProjectCommand(
    project.id,
    commandByUser = @Suppress("Deprecation") api.remoteDataSourceAPI.getUserID(),
    createdByAdmin = true,
    repositoryViewModelAPI = api
) {

    companion object {
        fun isIllegal(project: ProjectHandler): Boolean {
            return isIllegal()
        }

        const val isAdminOperation: Boolean = false

        const val publishable: Boolean = false
    }

    override suspend fun execute() {
        @Suppress("Deprecation")
        val project1 = repositoryViewModelAPI.appDataBase.projectCDManager().insertProject(project)
        projectID = project1.id
        projectIDReturn?.emit(projectID)
        super.execute()
    }

    override suspend fun publish(): Boolean {
        return super.publish() && publishable
    }
}