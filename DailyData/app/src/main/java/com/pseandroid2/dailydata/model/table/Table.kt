package com.pseandroid2.dailydata.model

import com.google.gson.Gson
import com.pseandroid2.dailydata.model.database.entities.RowEntity
import com.pseandroid2.dailydata.model.uielements.UIElement
import java.time.LocalDateTime

interface Table {

    fun getCell(row: Int, col: Int): Any

    fun getLayout(): TableLayout

    fun getSize(): Int

    fun getRow(row: Int): Row

    fun addRow(row: Row)

    fun deleteRow(row: Int)

    fun getColumn(col: Int): List<Any>

    fun addColumn(type: Class<Any>)

    fun deleteColumn(col: Int)

}

interface TableLayout {

    fun getSize(): Int

    fun getColumnType(col: Int): Class<Any>

    fun getUIElements(col: Int): List<UIElement>

    fun toJSON(): String

    fun fromJSON(json: String): TableLayout

}

interface Row {

    fun getAll(): List<Any>

    fun getCell(col: Int): Any

    fun getMetaData(): RowMetaData

    fun getSize(): Int

}

fun Row.toRowEntity(projectId: Int): RowEntity {
    val meta = this.getMetaData()
    val values = Gson().toJson(this.getAll())
    return RowEntity(
        projectId,
        meta.createdOn,
        meta.createdBy,
        values,
        meta.publishedOn
    )
}

data class RowMetaData(
    val createdOn: LocalDateTime,
    var publishedOn: LocalDateTime,
    val createdBy: User
)
