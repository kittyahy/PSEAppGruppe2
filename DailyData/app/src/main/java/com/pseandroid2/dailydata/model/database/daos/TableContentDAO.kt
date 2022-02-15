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

package com.pseandroid2.dailydata.model.database.daos

import android.util.Log
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.pseandroid2.dailydata.model.database.entities.RowEntity
import com.pseandroid2.dailydata.model.table.ArrayListRow
import com.pseandroid2.dailydata.model.table.Row
import com.pseandroid2.dailydata.model.table.toRowEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * This class provides Methods and Queries to saves and change and remove Rows to their given tables.
 */
@Dao
abstract class TableContentDAO {

    /**
     * It provides the by their id recommended rows.
     */
    fun getRowsById(id: Int): Flow<List<Row>> {
        @Suppress("Deprecation")
        return getRowEntitiesById(id).map {
            val list: MutableList<Row> = ArrayList()
            for (entity: RowEntity in it) {
                list.add(ArrayListRow.createFromEntity(entity))
            }
            list.toList()
        }
    }

    /**
     * It adds the given row to the specified project.
     */
    suspend fun insertRow(row: Row, projectId: Int) {
        @Suppress("Deprecation")
        insertRowEntity(row.toRowEntity(projectId))
    }

    /**
     * It deletes the given rows from the specified project.
     */
    suspend fun deleteRows(projectId: Int, vararg rows: Row) {
        for (row: Row in rows) {
            @Suppress("Deprecation")
            deleteRowEntities(row.toRowEntity(projectId))
        }
    }

    /**
     * It changes the given rows in the specified project.
     */
    suspend fun changeRows(projectId: Int, vararg rows: Row) {
        for (row: Row in rows) {
            @Suppress("Deprecation")
            changeRowEntities(row.toRowEntity(projectId))
        }
    }

    /*========================SHOULD ONLY BE CALLED FROM INSIDE THE MODEL=========================*/
    /**
     * It inserts the specified RowEntity to the table.
     */
    @Deprecated(
        "Shouldn't be used outside the Model, use insertRow instead",
        ReplaceWith("insertRow()")
    )
    @Insert
    abstract suspend fun insertRowEntity(row: RowEntity)

    /**
     * It provides the recommended RowEntities from a specified project.
     */
    @Deprecated(
        "Shouldn't be used outside the Model, use getRowsById instead",
        ReplaceWith("getRowsById()")
    )
    @Query("SELECT * FROM `row` WHERE projectId = :id")
    abstract fun getRowEntitiesById(id: Int): Flow<List<RowEntity>>

    /**
     * It deletes all given RowEntities.
     */
    @Deprecated(
        "Shouldn't be used outside the Model, use deleteRows instead",
        ReplaceWith("deleteRows()")
    )
    @Delete
    abstract suspend fun deleteRowEntities(vararg rows: RowEntity)

    /**
     * It changes all specified RowEntities to the given RowEntites.
     */
    @Deprecated(
        "Shouldn't be used outside the Model, use changeRows instead",
        ReplaceWith("changeRows()")
    )
    @Update
    abstract suspend fun changeRowEntities(vararg rows: RowEntity)
}