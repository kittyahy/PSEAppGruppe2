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
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow

interface Identifiable {
    val id: Int
    var executeQueue: ExecuteQueue
    var project: Project
    fun deleteIsPossible(): Flow<Boolean>

    //@throws IllegalOperationException
    suspend fun delete()

    @OptIn(InternalCoroutinesApi::class) //Todo dringend FlowAdapter reparieren, dann fällt das weg
    fun connectToRepository(repositoryViewModelAPI: RepositoryViewModelAPI) {
        this.executeQueue = repositoryViewModelAPI.projectHandler.executeQueue
    }

    fun connectToProject(project: Project) {
        this.project = project
    }
}