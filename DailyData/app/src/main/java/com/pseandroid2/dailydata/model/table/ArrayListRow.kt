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

package com.pseandroid2.dailydata.model.table

import com.google.gson.Gson
import com.pseandroid2.dailydata.model.database.entities.RowEntity

class ArrayListRow(
    private val values: MutableList<Any>,
    var rowMetaData: RowMetaData
) : Row {

    companion object {
        fun createFromEntity(entity: RowEntity): ArrayListRow {
            val metaData: RowMetaData =
                RowMetaData(entity.createdOn, entity.publishedOnServer, entity.createdBy)
            val values = getValuesFromJSON(entity.values)
            return ArrayListRow(values.toMutableList(), metaData)
        }

        private fun getValuesFromJSON(json: String): List<Any> {
            val list = Gson().fromJson(json, ArrayList::class.java)
            //TODO make sure Integers actually become Integers and stuff
            return list ?: listOf()
        }
    }

    override fun getAll(): List<Any> {
        return values.toList()
    }

    @Deprecated("Use get operator for better Readability")
    override fun getCell(col: Int): Any {
        return values[col]
    }

    operator fun get(col: Int): Any {
        return values[col]
    }

    override fun getMetaData(): RowMetaData {
        return rowMetaData
    }

    override fun getSize(): Int {
        return values.size
    }

    override fun createCell(value: Any) {
        values.add(value)
    }

    override fun deleteCell(col: Int) {
        values.removeAt(col)
    }
}

fun Row.toArrayListRow(): ArrayListRow {
    return ArrayListRow(this.getAll().toMutableList(), this.getMetaData())
}