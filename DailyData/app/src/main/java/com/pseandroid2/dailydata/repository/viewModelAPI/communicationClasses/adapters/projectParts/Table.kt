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
            val table: Table = ArrayListTable(tableLayout)
            for (column in columns) {
                table.addColumn(column.dataType.representation, "") //TODO Arne frage ob das Probleme machen könnte
            }
            for (row in rows) {
                table.addRow(row.toDBEquivalent())
            }
            return table
        }
    }
}