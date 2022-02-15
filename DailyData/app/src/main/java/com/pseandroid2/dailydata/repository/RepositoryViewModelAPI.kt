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

package com.pseandroid2.dailydata.repository

import com.pseandroid2.dailydata.model.database.AppDataBase
import com.pseandroid2.dailydata.remoteDataSource.RemoteDataSourceAPI
import com.pseandroid2.dailydata.repository.commandCenter.ExecuteQueue
import com.pseandroid2.dailydata.repository.commandCenter.PublishQueue
import com.pseandroid2.dailydata.repository.viewModelAPI.ProjectHandler
import com.pseandroid2.dailydata.repository.viewModelAPI.ServerHandler
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.adapters.flows.ProjectPreviewFlow
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.adapters.flows.ProjectTemplateFlow
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Inject


class RepositoryViewModelAPI @Inject constructor(
    @Deprecated("Internal function, should not be used outside the RepositoryViewModelAPI")
    val appDataBase: AppDataBase,
    @Deprecated("Internal function, should not be used outside the RepositoryViewModelAPI")
    val remoteDataSourceAPI: RemoteDataSourceAPI
) {
    @Suppress("DEPRECATION")
    val serverHandler = ServerHandler(appDataBase, remoteDataSourceAPI)

    @Suppress("DEPRECATION")
    val projectHandler = ProjectHandler(this)
}