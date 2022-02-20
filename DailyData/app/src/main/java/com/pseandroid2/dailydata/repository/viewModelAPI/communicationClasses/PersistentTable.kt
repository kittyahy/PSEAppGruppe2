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

import com.pseandroid2.dailydata.model.table.ColumnData
import com.pseandroid2.dailydata.model.table.Row
import com.pseandroid2.dailydata.model.table.Table
import com.pseandroid2.dailydata.model.table.TableLayout
import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.repository.commandCenter.commands.AddRow
import com.pseandroid2.dailydata.repository.commandCenter.commands.DeleteRow
import com.pseandroid2.dailydata.repository.commandCenter.commands.ProjectCommand
import com.pseandroid2.dailydata.repository.commandCenter.commands.SetRow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow

class PersistentTable(
    private val table: Table,
    private val repositoryViewModelAPI: RepositoryViewModelAPI,
    scope: CoroutineScope,
    private val projectID: Int
) : Table {

    @Deprecated("Internal function, should not be used outside the RepositoryViewModelAPI")
    @Suppress("DEPRECATION")
    override val mutableIllegalOperation: MutableMap<String, MutableSharedFlow<Boolean>> =
        mutableMapOf()

    override val layout: TableLayout =
        PersistentLayout(table.layout, repositoryViewModelAPI, projectID)

    init {
        for (operation in TableOperation.values()) {
            @Suppress("DEPRECATION")
            mutableIllegalOperation[operation.id] = MutableSharedFlow(1)
        }
        addFlows(layout)
    }

    override fun getCell(row: Int, col: Int): Any {
        return table.getCell(row, col)
    }

    override fun getSize(): Int {
        return table.getSize()
    }

    override fun getRow(row: Int): Row {
        return table.getRow(row)
    }

    override suspend fun addRow(row: Row) {
        saveToDB(AddRow(projectID, row, repositoryViewModelAPI), TableOperation.ADD_ROW.id)
    }

    override suspend fun setRow(row: Row) {
        saveToDB(SetRow(projectID, row, repositoryViewModelAPI), TableOperation.CHANGE_ROW.id)
    }

    override suspend fun deleteRow(row: Row) {
        saveToDB(DeleteRow(projectID, row, repositoryViewModelAPI), TableOperation.DELETE_ROW.id)
    }

    override fun getColumn(col: Int): List<Any> {
        return table.getColumn(col)
    }

    override suspend fun addColumn(specs: ColumnData, default: Any): Int {
        return layout.addColumn(specs)
    }

    override suspend fun setColumn(specs: ColumnData, default: Any) {
        layout.setColumn(specs)
    }


    override suspend fun deleteColumn(col: Int) {
        layout.deleteColumn(col)
    }

    override fun iterator(): Iterator<Row> {
        return table.iterator()
    }

    private suspend fun saveToDB(projectCommand: ProjectCommand, vararg operationIDs: String) {
        for (id in operationIDs) {
            @Suppress("DEPRECATION")
            mutableIllegalOperation[id]!!.emit(false)
        }
        repositoryViewModelAPI.projectHandler.executeQueue.add(projectCommand)
    }

}
