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

package com.pseandroid2.dailydata.model.table

import com.google.gson.Gson
import com.pseandroid2.dailydata.model.database.entities.RowEntity
import com.pseandroid2.dailydata.model.uielements.UIElement
import com.pseandroid2.dailydata.model.users.NullUser
import com.pseandroid2.dailydata.model.users.User
import com.pseandroid2.dailydata.repository.commandCenter.commands.AddColumn
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.DataType
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Operation
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import kotlin.reflect.KClass

/**
 * The interface, which specifies what a table should be able to do.
 */
interface Table : Iterable<Row> {

    val addableTypes: Collection<DataType>
        get() = layout.addableTypes

    val isIllegalOperation: Map<Operation, Flow<Boolean>>

    val layout: TableLayout

    fun getCell(row: Int, col: Int): Any

    fun getSize(): Int

    fun getRow(row: Int): Row

    suspend fun addRow(row: Row)

    suspend fun deleteRow(row: Row)

    fun getColumn(col: Int): List<Any>

    /**
     * specs.id and specs.uiElements will be ignored by this function.
     * Callers should make sure to update their Column Id to the id that is returned and have to
     * add UI-Elements separately
     *
     * @return the id of the column after it has been added to the Table
     */
    suspend fun addColumn(specs: ColumnData, default: Any = specs.type.initialValue): Int

    suspend fun deleteColumn(col: Int)

    suspend fun addUIElement(col: Int, uiElement: UIElement) =
        layout.addUIElement(col, uiElement)

    suspend fun removeUIElement(col: Int, id: Int) = layout.removeUIElement(col, id)

    suspend fun setRow(row: Row)
}

/**
 * This interface specifies what a table should do and have.
 */
interface TableLayout : Iterable<ColumnData> {

    companion object {
        @JvmStatic
        fun fromJSON(json: String): TableLayout {
            return ArrayListLayout(json)
        }
    }

    val addableTypes: Collection<DataType>
        get() = AddColumn.addableTypes(this)

    val size: Int

    fun getColumnType(col: Int): KClass<out Any>

    fun getUIElements(col: Int): List<UIElement>
    suspend fun addUIElement(col: Int, element: UIElement): Int
    suspend fun removeUIElement(col: Int, id: Int)

    fun getName(col: Int): String
    fun getUnit(col: Int): String

    operator fun get(col: Int): ColumnData

    suspend fun addColumn(specs: ColumnData): Int

    suspend fun deleteColumn(col: Int)

    fun toJSON(): String

    suspend fun setColumn(specs: ColumnData)
    suspend fun setUIElement(col: Int, element: UIElement)
}

/**
 *  This interface specifies what a row can.
 */
interface Row {

    fun getAll(): List<Any>

    fun getCell(col: Int): Any

    fun getMetaData(): RowMetaData

    fun getSize(): Int

    fun createCell(value: Any)

    fun deleteCell(col: Int)

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

/**
 * It saves all MataData form a row, which is not necessary to provide a row to the view.
 */
data class RowMetaData(
    val createdOn: LocalDateTime = LocalDateTime.now(),
    var publishedOn: LocalDateTime = LocalDateTime.now(),
    val createdBy: User = NullUser()
)

/**
 * @param type Serializable Name of a kotlin class (as obtained by KClass.getSerializableName())
 */
data class ColumnData(
    val id: Int = -1,
    val type: DataType,
    val name: String,
    val unit: String,
    val uiElements: MutableList<UIElement> = mutableListOf()
)
