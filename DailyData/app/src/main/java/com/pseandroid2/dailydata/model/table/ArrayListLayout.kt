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
import com.pseandroid2.dailydata.model.uielements.UIElement
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.DataType
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.LayoutOperation
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.TableOperation
import com.pseandroid2.dailydata.util.Quadruple
import com.pseandroid2.dailydata.util.fromJson
import com.pseandroid2.dailydata.util.getSerializableClassName
import com.pseandroid2.dailydata.util.hashOf
import kotlin.reflect.KClass
import kotlinx.coroutines.flow.MutableSharedFlow

/**
 * This is a implementation of a tableLayout with array lists.
 */
class ArrayListLayout(input: String = "") : TableLayout {
    override val size: Int
        get() = layout.size

    override val columnDataList: List<ColumnData>
        get() {
            val columns = mutableListOf<ColumnData>()
            for (i in layout.indices) {
                columns.add(
                    ColumnData(
                        i,
                        layout[i].first,
                        layout[i].second,
                        layout[i].third,
                        layout[i].fourth
                    )
                )
            }
            return columns.toList()
        }

    private var layout: MutableList<Quadruple<DataType, String, String, MutableList<UIElement>>> =
        if (input == "") {
            mutableListOf()
        } else {
            Gson().fromJson(input)
        }

    constructor(layoutList: List<ColumnData>) : this("") {
        for (col in layoutList) {
            layout.add(
                Quadruple(
                    col.type,
                    col.name,
                    col.unit,
                    col.uiElements.toMutableList()
                )
            )
        }
    }

    @Deprecated("Internal function, should not be used outside the RepositoryViewModelAPI")
    @Suppress("DEPRECATION")
    override val mutableIllegalOperation: MutableMap<String, MutableSharedFlow<Boolean>> =
        mutableMapOf()

    init {
        for (operation in LayoutOperation.values()) {
            @Suppress("DEPRECATION")
            mutableIllegalOperation[operation.id] = MutableSharedFlow(1)
        }
    }

    constructor(layout: ArrayListLayout) : this(
        layout.columnDataList
    )

    override fun getColumnType(col: Int) =
        Class.forName(layout[col].first.serializableClassName).kotlin

    override fun getUIElements(col: Int): List<UIElement> = layout[col].fourth.toList()

    override suspend fun addUIElement(col: Int, element: UIElement): Int {
        @Suppress("DEPRECATION")
        mutableIllegalOperation[LayoutOperation.ADD_UIELEMENT.id]!!.emit(false)
        layout[col].fourth.add(element)
        return layout[col].fourth.size - 1
    }

    override suspend fun setUIElement(col: Int, element: UIElement) {
        @Suppress("DEPRECATION")
        mutableIllegalOperation[LayoutOperation.CHANGE_UIELEMENT.id]!!.emit(false)
        layout[col].fourth[element.id] = element
    }

    override suspend fun removeUIElement(col: Int, id: Int) {
        @Suppress("DEPRECATION")
        mutableIllegalOperation[LayoutOperation.DELETE_UIELEMENT.id]!!.emit(false)
        layout[col].fourth.removeAll { it.id == id }
    }

    override fun getName(col: Int) = layout[col].second

    override fun getUnit(col: Int) = layout[col].third

    override fun get(col: Int) = ColumnData(
        col,
        layout[col].first,
        layout[col].second,
        layout[col].third,
        layout[col].fourth
    )

    override suspend fun addColumn(specs: ColumnData): Int {
        @Suppress("DEPRECATION")
        mutableIllegalOperation[LayoutOperation.ADD_COLUMN.id]!!.emit(false)
        layout.add(Quadruple(specs.type, specs.name, specs.unit, mutableListOf()))
        return size - 1
    }

    override suspend fun deleteColumn(col: Int) {
        @Suppress("DEPRECATION")
        mutableIllegalOperation[LayoutOperation.DELETE_COLUMN.id]!!.emit(false)
        layout.removeAt(col)
    }

    override fun toJSON(): String {
        return Gson().toJson(layout)
    }

    override suspend fun setColumn(specs: ColumnData) {
        @Suppress("DEPRECATION")
        mutableIllegalOperation[LayoutOperation.CHANGE_COLUMN.id]!!.emit(false)
        layout[specs.id] = Quadruple(specs.type, specs.name, specs.unit, mutableListOf())
    }

    override fun iterator() = ArrayListLayoutIterator(layout)

    @Deprecated(
        "Shouldn't be used outside of ArrayListLayoutIterator, iterate over the layout instead",
        level = DeprecationLevel.ERROR
    )
    fun getList() = layout

    override fun equals(other: Any?): Boolean {
        if (other != null && other is ArrayListLayout) {
            return other.layout == layout
        }
        return false
    }

    override fun hashCode(): Int {
        return hashOf(layout)
    }
}

class ArrayListLayoutIterator(private val layoutList: List<Quadruple<DataType, String, String, List<UIElement>>>) :
    Iterator<ColumnData> {
    var index = -1

    override fun hasNext() = (index + 1) in layoutList.indices

    override fun next(): ColumnData {
        val next = layoutList[++index]
        return ColumnData(index, next.first, next.second, next.third, next.fourth.toMutableList())
    }

}