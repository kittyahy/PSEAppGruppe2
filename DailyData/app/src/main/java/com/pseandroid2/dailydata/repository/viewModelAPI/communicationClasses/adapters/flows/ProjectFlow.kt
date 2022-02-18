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

package com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.adapters.flows

import android.util.Log
import com.google.gson.Gson
import com.pseandroid2.dailydata.model.database.AppDataBase
import com.pseandroid2.dailydata.model.notifications.TimeNotification
import com.pseandroid2.dailydata.model.project.Project
import com.pseandroid2.dailydata.model.table.ArrayListLayout
import com.pseandroid2.dailydata.model.users.NullUser
import com.pseandroid2.dailydata.model.users.SimpleUser
import com.pseandroid2.dailydata.remoteDataSource.RemoteDataSourceAPI
import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.repository.commandCenter.ExecuteQueue
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Button
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Graph
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Member
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Notification
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.ViewModelProject
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Row
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.toViewGraph
import com.pseandroid2.dailydata.util.Consts.LOG_TAG
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProjectFlow(
    private val repositoryViewModelAPI: RepositoryViewModelAPI,
    private val provider: ProjectFlowProvider
) {
    @Suppress("DEPRECATION")
    private val db: AppDataBase = repositoryViewModelAPI.appDataBase

    @Suppress("DEPRECATION")
    private val rds: RemoteDataSourceAPI = repositoryViewModelAPI.remoteDataSourceAPI
    private val eq: ExecuteQueue = repositoryViewModelAPI.projectHandler.executeQueue
    fun getProject(): Flow<Project> = provider.provideFlow.map { project ->
        val ret = if (project == null) {
            Log.d(LOG_TAG, "Got null Project from database")
            ViewModelProject(
                repo = repositoryViewModelAPI,
                admin = SimpleUser("", ""),
                scope = provider.scope
            )
        } else {
            project
        }
        return@map ret
    }
}

