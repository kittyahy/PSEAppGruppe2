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
import kotlinx.coroutines.flow.Flow

class PersistentTable(
    private val table: Table,
    private val r: RepositoryViewModelAPI,
    scope: CoroutineScope,
    private val p: Int
) : Table {

    override val isIllegalOperation: Map<Operation, Flow<Boolean>>
        get() = table.isIllegalOperation
    override val layout: TableLayout =
        PersistentLayout(table.layout, r, p)

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
        s(AddRow(p, row, r))
    }

    override suspend fun setRow(row: Row) {
        s(SetRow(p, row, r))
    }

    override suspend fun deleteRow(row: Row) {
        s(DeleteRow(p, row, r))
    }

    override fun getColumn(col: Int): List<Any> {
        return table.getColumn(col)
    }

    override suspend fun addColumn(specs: ColumnData, default: Any): Int {
        TODO("Not yet implemented")
    }

    override suspend fun deleteColumn(col: Int) {
        TODO("Not yet implemented")
    }

    override fun iterator(): Iterator<Row> {
        return table.iterator()
    }

    private suspend fun s(projectCommand: ProjectCommand) {
        r.projectHandler.executeQueue.add(projectCommand)
    }

}
