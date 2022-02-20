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

import com.pseandroid2.dailydata.model.table.ColumnData
import com.pseandroid2.dailydata.model.table.TableLayout
import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI

class SetColumn(projectID: Int, private val specs: ColumnData, api: RepositoryViewModelAPI) :
    ProjectCommand(projectID = projectID, repositoryViewModelAPI = api) {
    companion object {
        fun isIllegal(layout: TableLayout): Boolean {
            return layout.iterator().hasNext()
        }

        const val isAdminOperation: Boolean = true

        const val publishable: Boolean = true

    }

    override suspend fun execute() {
        @Suppress("Deprecation")
        TODO()//repositoryViewModelAPI.appDataBase.layoutDAO().
    }

    override suspend fun publish(): Boolean {
        return super.publish() && publishable
    }

}
