package com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.adapters.projectParts

import com.pseandroid2.dailydata.model.table.ArrayListLayout
import com.pseandroid2.dailydata.model.table.ArrayListTable
import com.pseandroid2.dailydata.model.table.Table
import com.pseandroid2.dailydata.model.table.TableLayout
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Button
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Column
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Row

class Table {
    companion object {
        fun extractFrom(columns: List<Column>, buttons: List<Button>, rows: List<Row> = ArrayList<Row>()) : Table{
            val tableLayout: TableLayout = ArrayListLayout()
            val table: Table = ArrayListTable(tableLayout)
            for (column in columns) {
                tableLayout.addColumn(column.dataType.representation, "", "") //TODO insert actual values here
            }
            for (button in buttons) {
                tableLayout.addUIElements(button.columnId, button.toDBEquivalent())
            }
            for (row in rows) {
                table.addRow(row.toDBEquivalent())
            }
            return table
        }
    }
}