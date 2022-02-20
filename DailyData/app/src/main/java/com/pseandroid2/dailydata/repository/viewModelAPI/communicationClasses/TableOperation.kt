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

import com.pseandroid2.dailydata.model.table.Table
import com.pseandroid2.dailydata.repository.commandCenter.commands.AddRow
import com.pseandroid2.dailydata.repository.commandCenter.commands.DeleteRow
import com.pseandroid2.dailydata.repository.commandCenter.commands.SetRow

enum class TableOperation(val adminOp: Boolean, val id: String) {
    ADD_ROW(false, "ADD_ROW") {
        override fun isIllegalByData(table: Table): Boolean {
            return AddRow.isIllegal(table)
        }
    },
    CHANGE_ROW(false, "CHANGE_ROW") {
        override fun isIllegalByData(table: Table): Boolean {
            return SetRow.isIllegal(table)
        }
    },
    DELETE_ROW(false, "DELETE_ROW") {
        override fun isIllegalByData(table: Table): Boolean {
            return DeleteRow.isIllegal(table)
        }
    };

    open fun isIllegalByData(table: Table): Boolean = false
}