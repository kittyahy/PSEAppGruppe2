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

import com.pseandroid2.dailydata.model.project.ProjectBuilder
import com.pseandroid2.dailydata.model.table.ArrayListRow
import com.pseandroid2.dailydata.model.table.Row
import com.pseandroid2.dailydata.model.table.RowMetaData
import com.pseandroid2.dailydata.model.users.SimpleUser
import com.pseandroid2.dailydata.repository.commandCenter.ExecuteQueue
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import com.pseandroid2.dailydata.model.project.Project as ModelProject

class Row(
    override var id: Int,
    val elements: List<String>
) : Identifiable, Convertible<Row> {
    override lateinit var executeQueue: ExecuteQueue
    override lateinit var project: Project

    constructor(row: Row) : this(
        row.getMetaData().createdOn.hashCode(), //TODO Richtige ID
        listConversion(row.getAll())
    )

    companion object {
        private fun listConversion(all: List<Any>): List<String> {
            val list = ArrayList<String>()
            all.forEach { element -> list.add(element.toString()) }
            return list
        }
    }

    override fun deleteIsPossible(): Flow<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun delete() {
        TODO("Not yet implemented")
    }

    override fun toDBEquivalent(): Row {
        val rowMetaData = RowMetaData(
            LocalDateTime.now(),
            LocalDateTime.MAX,
            SimpleUser("0", "No One")
        ) //Todo werte fixen
        val elementList: MutableList<Any> = ArrayList<Any>()
        elementList.addAll(elements)
        return ArrayListRow(elementList, rowMetaData)
    }

    override fun addYourself(builder: ProjectBuilder<out ModelProject>) {
        TODO("Not yet implemented")
    }
}
