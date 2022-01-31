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

import com.pseandroid2.dailydata.model.project.Project
import com.pseandroid2.dailydata.model.project.ProjectBuilder
import com.pseandroid2.dailydata.model.table.ArrayListRow
import com.pseandroid2.dailydata.model.table.Row
import com.pseandroid2.dailydata.model.table.RowMetaData
import com.pseandroid2.dailydata.model.users.SimpleUser
import java.time.LocalDateTime

class Row(
    override val id: Int,
    val elements: List<String>
): Identifiable(), Convertible<Row> {
    override fun deleteIsPossible(): Boolean {
        TODO("Not yet implemented")
    }
    override suspend fun delete() {
        TODO("Not yet implemented")
    }
    //@throws IllegalOperationException
    fun setCell(indexColumn: Int, content: String) {
        if (indexColumn >= 0 && indexColumn < elements.size) {
            TODO()
        }
        TODO()
    }

    override fun toDBEquivalent(): Row {
        val rowMetaData = RowMetaData(LocalDateTime.now(), LocalDateTime.MAX, SimpleUser("0", "No One")) //Todo werte fixen
        val elementList : MutableList<Any> = ArrayList<Any>()
        elementList.addAll(elements)
        return ArrayListRow(elementList, rowMetaData)
    }

    override fun addYourself(builder: ProjectBuilder<out Project>) {
        TODO("Not yet implemented")
    }
}
