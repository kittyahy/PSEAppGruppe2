package com.pseandroid2.dailydata.model.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Update
import androidx.room.Delete
import androidx.room.Query
import com.pseandroid2.dailydata.model.Row
import com.pseandroid2.dailydata.model.database.entities.RowEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Dao
abstract class TableContentDAO {
    fun getRowsByIds(vararg ids: Int): Flow<List<Row>> {
        return getRowEntitiesByIds(ids = ids).map {
            val list: MutableList<Row> = ArrayList()
            for (entity: RowEntity in it) {
                list.add()
            }
            list.toList()
        }
    }

    fun insertRow(row: Row, projectId: Int) {
        //TODO Convert Row to a RowEntity
    }

    fun deleteRows(projectId: Int, vararg rows: Row) {
        //TODO Convert Row to a RowEntity
    }

    fun changeRows(projectId: Int, vararg rows: Row) {
        //TODO Convert Row to a RowEntity
    }

    /*========================SHOULD ONLY BE CALLED FROM INSIDE THE MODEL=========================*/
    @Insert
    abstract fun insertRowEntity(row: RowEntity)

    @Query("SELECT * FROM `row` WHERE projectId IN (:ids)")
    abstract fun getRowEntitiesByIds(vararg ids: Int): Flow<List<RowEntity>>

    @Delete
    abstract fun deleteRowEntities(vararg rows: RowEntity)

    @Update
    abstract fun changeRowEntities(vararg rows: RowEntity)
}