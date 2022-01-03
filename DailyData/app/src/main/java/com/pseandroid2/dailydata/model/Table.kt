package com.pseandroid2.dailydata.model

import java.time.LocalDateTime

interface Table {

    public fun getCell(row: Int, col: Int): Any

    public fun getLayout(): TableLayout

    public fun getSize(): Int

    public fun getRow(row: Int): Row

    public fun addRow(row: Row)

    public fun deleteRow(row: Int)

    public fun getColumn(col: Int): List<Any>

    public fun addColumn(type: Class<Any>)

    public fun deleteColumn(col: Int)

}

interface TableLayout {

    public fun getSize(): Int

    public fun getColumnType(col: Int): Class<Any>

    public fun getUIElement(col: Int): UIElementType

    public fun toJSON(): String

    public fun fromJSON(json: String): TableLayout

}

interface Row {

    public fun getAll(): List<Any>

    public fun getCell(col: Int): Any

    public fun getMetaData(): RowMetaData

    public fun getSize(): Int

}

data class RowMetaData(
    val createdOn: LocalDateTime,
    var publishedOn: LocalDateTime,
    val createdBy: User
) {

}