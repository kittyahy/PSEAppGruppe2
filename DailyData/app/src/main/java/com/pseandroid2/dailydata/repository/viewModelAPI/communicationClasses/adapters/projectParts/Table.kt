package com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.adapters.projectParts

import com.pseandroid2.dailydata.model.table.ArrayListLayout
import com.pseandroid2.dailydata.model.table.ArrayListTable
import com.pseandroid2.dailydata.model.table.Table
import com.pseandroid2.dailydata.model.table.TableLayout
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Column
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Row

class Table {
    companion object {
        fun extractFrom(columns: List<Column>, rows: List<Row> = ArrayList<Row>()) : Table{
            val tableLayout: TableLayout = ArrayListLayout()
            for (column in columns) {
                tableLayout.addColumn(column.dataType.representation)
            }
            val table: Table = ArrayListTable(tableLayout)
            for (row in rows) {
                table.addRow(row.toDBEquivalent())
            }
            return table
        }
    }
}