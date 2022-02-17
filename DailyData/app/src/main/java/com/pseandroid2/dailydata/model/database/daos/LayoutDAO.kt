package com.pseandroid2.dailydata.model.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.pseandroid2.dailydata.model.database.entities.ColumnEntity
import com.pseandroid2.dailydata.model.table.ColumnData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Dao
abstract class LayoutDAO {
    suspend fun addColumn(projectId: Int, column: ColumnData) {
        @Suppress("Deprecation")
        insertColumn(ColumnEntity(projectId, column.id, column.type, column.name, column.unit))
    }

    suspend fun removeColumn(projectId: Int, column: ColumnData) {
        @Suppress("Deprecation")
        deleteColumn(ColumnEntity(projectId, column.id, column.type, column.name, column.unit))
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
                        entity.type,
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
            ColumnData(entity.id, entity.type, entity.name, entity.unit, mutableListOf())
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