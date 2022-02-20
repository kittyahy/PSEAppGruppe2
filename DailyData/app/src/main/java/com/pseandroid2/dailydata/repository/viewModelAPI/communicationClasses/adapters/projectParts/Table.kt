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

package com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.adapters.projectParts

import com.pseandroid2.dailydata.model.table.ArrayListLayout
import com.pseandroid2.dailydata.model.table.ArrayListTable
import com.pseandroid2.dailydata.model.table.Table
import com.pseandroid2.dailydata.model.table.TableLayout
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Button
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Column
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Row

@Deprecated("Deprecated as of 2022-02-16")
class Table {
    companion object {
        fun extractFrom(
            columns: List<Column>,
            buttons: List<Button>,
            rows: List<Row> = ArrayList<Row>()
        ): Table {
            val tableLayout: TableLayout = ArrayListLayout()
            val table: Table = ArrayListTable(tableLayout)
            for (column in columns) {
                //tableLayout.addColumn(
                    //column.dataType.serializableClassName,
                    //column.name,
                    //column.unit
                //)
            }
            for (button in buttons) {
                //tableLayout.addUIElement(button.columnId, button)
            }
            for (row in rows) {
                //table.addRow(row.toDBEquivalent())
            }
            return table
        }
    }
}