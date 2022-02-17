package com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses

import com.pseandroid2.dailydata.model.table.ColumnData
import com.pseandroid2.dailydata.model.table.Row
import com.pseandroid2.dailydata.model.table.Table
import com.pseandroid2.dailydata.model.table.TableLayout
import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import kotlinx.coroutines.flow.Flow

class PersistentTable(
    private val table: Table,
    private val repositoryViewModelAPI: RepositoryViewModelAPI
) :
    Table {
    override val isIllegalOperation: Map<Operation, Flow<Boolean>>
        get() = table.isIllegalOperation
    override val layout: TableLayout
        get() = PersistentLayout(table.layout, repositoryViewModelAPI)

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
        TODO("Not yet implemented")
    }

    override suspend fun deleteRow(row: Int) {
        TODO("Not yet implemented")
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

}
