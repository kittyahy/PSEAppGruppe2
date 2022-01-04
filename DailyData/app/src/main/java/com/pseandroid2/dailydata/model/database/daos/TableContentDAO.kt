package com.pseandroid2.dailydata.model.database.daos

import androidx.room.*
import com.pseandroid2.dailydata.model.Row
import com.pseandroid2.dailydata.model.database.entities.RowEntity
import com.pseandroid2.dailydata.model.table.ArrayListRow
import com.pseandroid2.dailydata.model.toRowEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Dao
abstract class TableContentDAO {
    fun getRowsById(id: Int): Flow<List<Row>> {
        return getRowEntitiesById(id).map {
            val list: MutableList<Row> = ArrayList()
            for (entity: RowEntity in it) {
                list.add(ArrayListRow.createFromEntity(entity))
            }
            list.toList()
        }
    }

    fun insertRow(row: Row, projectId: Int) {
        insertRowEntity(row.toRowEntity(projectId))
    }

    fun deleteRows(projectId: Int, vararg rows: Row) {
        for (row: Row in rows) {
            deleteRowEntities(row.toRowEntity(projectId))
        }
    }

    fun changeRows(projectId: Int, vararg rows: Row) {
        for (row: Row in rows) {
            changeRowEntities(row.toRowEntity(projectId))
        }
    }

    /*========================SHOULD ONLY BE CALLED FROM INSIDE THE MODEL=========================*/
    @Insert
    abstract fun insertRowEntity(row: RowEntity)

    @Query("SELECT * FROM `row` WHERE projectId = :id")
    abstract fun getRowEntitiesById(id: Int): Flow<List<RowEntity>>

    @Delete
    abstract fun deleteRowEntities(vararg rows: RowEntity)

    @Update
    abstract fun changeRowEntities(vararg rows: RowEntity)
}