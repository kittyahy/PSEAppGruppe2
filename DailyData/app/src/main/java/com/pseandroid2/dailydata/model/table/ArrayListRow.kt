package com.pseandroid2.dailydata.model.table

import com.google.gson.Gson
import com.pseandroid2.dailydata.model.Row
import com.pseandroid2.dailydata.model.RowMetaData
import com.pseandroid2.dailydata.model.database.entities.RowEntity

class ArrayListRow(
    private val values: List<Any>,
    var rowMetaData: RowMetaData
) : Row {

    companion object {
        fun createFromEntity(entity: RowEntity): ArrayListRow {
            val metaData: RowMetaData =
                RowMetaData(entity.createdOn, entity.publishedOnServer, entity.createdBy)
            val values = getValuesFromJSON(entity.values)
            return ArrayListRow(values, metaData)
        }

        private fun getValuesFromJSON(json: String): List<Any> {
            val list = Gson().fromJson(json, ArrayList::class.java)
            //TODO make sure Integers actually become Integers and stuff
            return list ?: listOf()
        }
    }

    override fun getAll(): List<Any> {
        return values
    }

    override fun getCell(col: Int): Any {
        return values[col]
    }

    override fun getMetaData(): RowMetaData {
        return rowMetaData
    }

    override fun getSize(): Int {
        return values.size
    }
}