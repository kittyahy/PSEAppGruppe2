package com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses

import com.pseandroid2.dailydata.model.table.TableLayout
import com.pseandroid2.dailydata.repository.commandCenter.commands.AddColumn
import com.pseandroid2.dailydata.repository.commandCenter.commands.AddUIElement
import com.pseandroid2.dailydata.repository.commandCenter.commands.DeleteColumn
import com.pseandroid2.dailydata.repository.commandCenter.commands.DeleteUIElement
import com.pseandroid2.dailydata.repository.commandCenter.commands.SetColumn
import com.pseandroid2.dailydata.repository.commandCenter.commands.SetUIElement

enum class LayoutOperation(val adminOp: Boolean, val id: String) {
    ADD_COLUMN(true, "ADD_COLUMN") {
        override fun isIllegalByData(layout: TableLayout): Boolean {
            return AddColumn.isIllegal(layout)
        }
    },
    CHANGE_COLUMN(true, "CHANGE_COLUMN") {
        override fun isIllegalByData(layout: TableLayout): Boolean {
            return SetColumn.isIllegal(layout)
        }
    },
    DELETE_COLUMN(true, "DELETE_COLUMN") {
        override fun isIllegalByData(layout: TableLayout): Boolean {
            return DeleteColumn.isIllegal(layout)
        }
    },
    ADD_UIELEMENT(true, "ADD_UIELEMENT") {
        override fun isIllegalByData(layout: TableLayout): Boolean {
            return AddUIElement.isIllegal(layout)
        }
    },
    CHANGE_UIELEMENT(true, "CHANGE_UIELEMENT") {
        override fun isIllegalByData(layout: TableLayout): Boolean {
            return SetUIElement.isIllegal(layout)
        }
    },
    DELETE_UIELEMENT(true, "DELETE_UIELEMENT") {
        override fun isIllegalByData(layout: TableLayout): Boolean {
            return DeleteUIElement.isIllegal(layout)
        }
    };

    open fun isIllegalByData(layout: TableLayout): Boolean = false
}