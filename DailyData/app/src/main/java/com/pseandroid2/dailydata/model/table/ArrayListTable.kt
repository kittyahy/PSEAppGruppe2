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

/**
 *
 */
class ArrayListTable(private var layout: TableLayout = ArrayListLayout()) : Table {
    private val table: MutableList<ArrayListRow> = mutableListOf()
    override fun getCell(row: Int, col: Int) = table[row][col]

    override fun getLayout() = layout

    override fun getSize() = table.size * layout.getSize()

    override fun getRow(row: Int) = table[row]

    override fun addRow(row: Row) {
        if (assertLayout(row)) {
            table.add(row.toArrayListRow())
        } else {
            throw IllegalArgumentException(
                "Tried to add a row to a table but the layouts did not match"
            )
        }
    }

    override fun deleteRow(row: Int) {
        table.removeAt(row)
    }

    override fun getColumn(col: Int): List<Any> {
        val result = mutableListOf<Any>()
        for (row in table) {
            result.add(row[col])
        }
        return result
    }

    override fun addColumn(typeString: String, name: String, unit: String, default: Any) {
        layout.addColumn(typeString, name, unit)
        for (row in table) {
            row.createCell(default)
        }
    }

    override fun deleteColumn(col: Int) {
        layout.deleteColumn(col)
        for (row in table) {
            row.deleteCell(col)
        }
    }

    private fun assertLayout(row: Row): Boolean {
        for (i in 0 until layout.getSize()) {
            if (row.getCell(i).javaClass == layout.getColumnType(i)) {
                return false
            }
        }
        return true
    }

    @Deprecated("Shouldn't be used from outside the model. Iterate over the Table instead")
    fun getAllRows() = table.toList()

    override fun iterator() = ArrayListTableIterator(this)
}

class ArrayListTableIterator(table: ArrayListTable) :
    Iterator<Row> {
    @Suppress("Deprecation")
    val iterator = table.getAllRows().iterator()

    override fun hasNext() = iterator.hasNext()

    override fun next(): Row {
        return iterator.next()
    }

}