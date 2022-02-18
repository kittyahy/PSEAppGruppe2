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
    private val repositoryViewModelAPI: RepositoryViewModelAPI,
    scope: CoroutineScope,
    private val projectID: Int
) : Table {

    override val isIllegalOperation: Map<Operation, Flow<Boolean>>
        get() = table.isIllegalOperation
    override val layout: TableLayout =
        PersistentLayout(table.layout, repositoryViewModelAPI, projectID)

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
        saveToDB(AddRow(projectID, row, repositoryViewModelAPI))
    }

    override suspend fun setRow(row: Row) {
        saveToDB(SetRow(projectID, row, repositoryViewModelAPI))
    }

    override suspend fun deleteRow(row: Row) {
        saveToDB(DeleteRow(projectID, row, repositoryViewModelAPI))
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

    private suspend fun saveToDB(projectCommand: ProjectCommand) {
        repositoryViewModelAPI.projectHandler.executeQueue.add(projectCommand)
    }

}
