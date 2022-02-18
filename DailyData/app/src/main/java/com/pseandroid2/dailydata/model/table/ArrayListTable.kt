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

import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Operation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

/**
 *
 */
class ArrayListTable(override val layout: TableLayout = ArrayListLayout()) : Table {

    private val mutableIllegalOperation: Map<Operation, MutableSharedFlow<Boolean>>
    override val isIllegalOperation: Map<Operation, Flow<Boolean>>

    init {
        val operations = mutableMapOf<Operation, MutableSharedFlow<Boolean>>()
        for (operation in Operation.values()) {
            if (operation.type == Operation.OperationType.TABLE) {
                operations[operation] = MutableSharedFlow(1)
            }
        }
        mutableIllegalOperation = operations.toMap()
        val immutableOperation = mutableMapOf<Operation, Flow<Boolean>>()
        for (entry in mutableIllegalOperation) {
            immutableOperation[entry.key] = entry.value.asSharedFlow()
        }
        isIllegalOperation = immutableOperation.toMap()
    }

    private val table: MutableList<ArrayListRow> = mutableListOf()
    override fun getCell(row: Int, col: Int) = table[row][col]

    override fun getSize() = table.size * layout.size

    override fun getRow(row: Int) = table[row]

    override suspend fun addRow(row: Row) {
        if (assertLayout(row)) {
            table.add(row.toArrayListRow())
        } else {
            throw IllegalArgumentException(
                "Tried to add a row to a table but the layouts did not match"
            )
        }
    }

    override suspend fun deleteRow(row: Int) {
        table.removeAt(row)
    }

    override fun getColumn(col: Int): List<Any> {
        val result = mutableListOf<Any>()
        for (row in table) {
            result.add(row[col])
        }
        return result
    }

    override suspend fun addColumn(specs: ColumnData, default: Any): Int {
        val newId = layout.addColumn(specs)
        for (row in table) {
            row.createCell(default)
        }
        return newId
    }

    override suspend fun deleteColumn(col: Int) {
        layout.deleteColumn(col)
        for (row in table) {
            row.deleteCell(col)
        }
    }

    private fun assertLayout(row: Row): Boolean {
        for (i in 0 until layout.size) {
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