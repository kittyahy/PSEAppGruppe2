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

import com.pseandroid2.dailydata.remoteDataSource.serverConnection.forRepoReturns.TemplateDetailWithPicture
import com.pseandroid2.dailydata.repository.commandCenter.ExecuteQueue
import kotlinx.coroutines.flow.Flow

/**
 * Post class that handles its specific interaction with ViewModel.
 */
class Post : Identifiable {
    override var id: Int = -1
    lateinit var postEntries: List<PostEntry>

    constructor(id: Int, list: List<PostEntry>) {
        this.id = id
        this.postEntries = list
    }

    constructor(id: Int, templateDetails: Collection<TemplateDetailWithPicture>) {
        this.id = id
        val postEntries = ArrayList<PostEntry>()
        for (detail in templateDetails) {
            postEntries.add(PostEntry(detail))
        }
        this.postEntries = postEntries
    }

    fun downloadProjectTemplate() {
        TODO("downloadProjectTemplate")
    }

    fun downloadGraphTemplate(id: Int) {
        TODO("downloadGraphTemplate")
    }

    override lateinit var executeQueue: ExecuteQueue
    lateinit var viewModelProject: ViewModelProject

    override fun deleteIsPossible(): Flow<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun delete() {
        TODO("Not yet implemented")
    }
}