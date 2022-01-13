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