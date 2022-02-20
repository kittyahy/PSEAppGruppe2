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