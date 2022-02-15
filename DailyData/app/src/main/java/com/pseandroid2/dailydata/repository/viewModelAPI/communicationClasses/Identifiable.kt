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

package com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses

import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.repository.commandCenter.ExecuteQueue
import kotlinx.coroutines.flow.Flow

interface Identifiable {
    var id: Int

    @Deprecated("Internal function, should not be used outside the RepositoryViewModelAPI")
    var executeQueue: ExecuteQueue

    @Deprecated("Internal function, should not be used outside the RepositoryViewModelAPI")
    var viewModelProject: ViewModelProject
    fun deleteIsPossible(): Flow<Boolean>

    //@throws IllegalOperationException
    suspend fun delete()

    @Deprecated("Internal function, should not be used outside the RepositoryViewModelAPI")
    fun connectToRepository(repositoryViewModelAPI: RepositoryViewModelAPI) {
        @Suppress("DEPRECATION")
        this.executeQueue = repositoryViewModelAPI.projectHandler.executeQueue
    }

    @Deprecated("Internal function, should not be used outside the RepositoryViewModelAPI")
    fun connectToProject(viewModelProject: ViewModelProject) {
        @Suppress("DEPRECATION")
        this.viewModelProject = viewModelProject
        @Suppress("DEPRECATION")
        this.connectToRepository(viewModelProject.repositoryViewModelAPI)
    }
}