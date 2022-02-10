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

import com.pseandroid2.dailydata.model.table.TableLayout
import com.pseandroid2.dailydata.repository.commandCenter.ExecuteQueue
import kotlinx.coroutines.flow.Flow
/**
 * Column class that handles its specific interaction with ViewModel.
 */
class Column(
    override var id: Int,
    val name: String,
    val unit: String,
    val dataType: DataType
) : Identifiable {
    override lateinit var executeQueue: ExecuteQueue
    override lateinit var project: Project
    override fun deleteIsPossible(): Flow<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun delete() {
        TODO("Not yet implemented")
    }
}

fun TableLayout.toColumnList(): List<Column> {
    val columns = mutableListOf<Column>()
    for (i in 0 until this.getSize()) {
        columns.add(
            Column(
                i,
                this[i].name,
                this[i].unit,
                DataType.fromSerializableClassName(this[i].type)
            )
        )
    }
    return columns.toList()
}
