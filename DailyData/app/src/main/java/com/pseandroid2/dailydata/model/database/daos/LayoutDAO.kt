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
import com.pseandroid2.dailydata.model.database.entities.ColumnEntity
import com.pseandroid2.dailydata.model.table.ColumnData
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.DataType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Dao
abstract class LayoutDAO {
    suspend fun addColumn(projectId: Int, column: ColumnData) {
        @Suppress("Deprecation")
        insertColumn(ColumnEntity(projectId, column.id, column.type.serializableClassName, column.name, column.unit))
    }

    suspend fun removeColumn(projectId: Int, column: ColumnData) {
        @Suppress("Deprecation")
        deleteColumn(ColumnEntity(projectId, column.id, column.type.serializableClassName, column.name, column.unit))
    }

    @Query("DELETE FROM layout WHERE projectId = :projectId AND id = :columnId")
    abstract suspend fun removeColumnById(projectId: Int, columnId: Int)

    @Deprecated("Should only be used by ProjectCDManager. If you want to delete the project, use ProjectCDManager.deleteProject()")
    @Query("DELETE FROM layout WHERE projectId = :projectId")
    abstract suspend fun removeAllColumns(projectId: Int)

    fun getColumns(projectId: Int): Flow<List<ColumnData>> {
        @Suppress("Deprecation")
        return getColumnEntities(projectId).map { entities ->
            val data = mutableListOf<ColumnData>()
            for (entity in entities) {
                data.add(
                    ColumnData(
                        entity.id,
                        DataType.fromSerializableClassName(entity.type),
                        entity.name,
                        entity.unit,
                        mutableListOf()
                    )
                )
            }
            data.toList()
        }
    }

    suspend fun getCurrentColumns(projectId: Int) =
        @Suppress("Deprecation")
        getCurrentColumnEntities(projectId).map { entity ->
            ColumnData(entity.id, DataType.fromSerializableClassName(entity.type), entity.name, entity.unit, mutableListOf())
        }

    @Deprecated(
        "Shouldn't be used outside LayoutDAO. Use getColumns() instead",
        ReplaceWith("getColumns(i)")
    )
    @Query("SELECT * FROM layout WHERE projectId = :projectId")
    abstract fun getColumnEntities(projectId: Int): Flow<List<ColumnEntity>>

    @Deprecated(
        "Shouldn't be used outside LayoutDAO. Use getCurrentColumns() instead",
        ReplaceWith("getCurrentColumns(i)")
    )
    @Query("SELECT * FROM layout WHERE projectId = :projectId")
    abstract suspend fun getCurrentColumnEntities(projectId: Int): List<ColumnEntity>

    @Deprecated("Shouldn't be used outside LayoutDAO. Use addColumn() instead")
    @Insert
    abstract suspend fun insertColumn(column: ColumnEntity)

    @Deprecated("Shouldn't be used outside LayoutDAO. Use removeColumn() instead")
    @Delete
    abstract suspend fun deleteColumn(column: ColumnEntity)
}