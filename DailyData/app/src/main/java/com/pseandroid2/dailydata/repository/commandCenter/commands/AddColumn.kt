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
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.DataType

class AddColumn(projectID: Int, private val specs: ColumnData, api: RepositoryViewModelAPI) :
    ProjectCommand(projectID = projectID, repositoryViewModelAPI = api) {
    companion object {
        fun isIllegal(layout: TableLayout): Boolean {
            return addableTypes(layout).isEmpty()
        }

        const val isAdminOperation: Boolean = true

        const val publishable: Boolean = true

        fun addableTypes(tableLayout: TableLayout): Collection<DataType> {
            val list: MutableList<DataType> = ArrayList()
            var total = DataType.storageSizeBaseline
            for (col in tableLayout) {
                total += col.type.storageSize
            }
            for (dataType in DataType.values()) {
                if (total + dataType.storageSize <= DataType.maxStorageSize) {
                    list.add(dataType)
                }
            }
            return list
        }
    }

    override suspend fun execute() {
        @Suppress("Deprecation")
        repositoryViewModelAPI.appDataBase.layoutDAO().addColumn(projectID, specs)
    }

    override suspend fun publish(): Boolean {
        return super.publish() && publishable
    }

}
