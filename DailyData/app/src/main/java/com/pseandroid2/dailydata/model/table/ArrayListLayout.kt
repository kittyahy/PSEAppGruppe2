package com.pseandroid2.dailydata.model.table

import com.google.gson.Gson
import com.pseandroid2.dailydata.model.uielements.UIElement
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.DataType
import com.pseandroid2.dailydata.util.Quadruple
import com.pseandroid2.dailydata.util.fromJson

/**
 * This is a implementation of a tableLayout with array lists.
 */
class ArrayListLayout(input: String = "") : TableLayout {
    override val size: Int
        get() = layout.size

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

    override fun getColumnType(col: Int) =
        Class.forName(layout[col].first.serializableClassName).kotlin

    override fun getUIElements(col: Int): List<UIElement> = layout[col].fourth.toList()

    override suspend fun addUIElement(col: Int, element: UIElement): Int {
        layout[col].fourth.add(element)
        return layout[col].fourth.size - 1
    }

    override suspend fun removeUIElement(col: Int, id: Int) {
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

    override suspend fun addColumn(type: ColumnData, name: String, unit: String): Int {
        layout.add(Quadruple(type, name, unit, mutableListOf()))
        return size - 1
    }

    override suspend fun deleteColumn(col: Int) {
        layout.removeAt(col)
    }

    override fun toJSON(): String {
        return Gson().toJson(layout)
    }

    override fun iterator() = ArrayListLayoutIterator(layout)

    @Deprecated(
        "Shouldn't be used outside of ArrayListLayoutIterator, iterate over the layout instead",
        level = DeprecationLevel.ERROR
    )
    fun getList() = layout
}

class ArrayListLayoutIterator(private val layoutList: List<Quadruple<DataType, String, String, List<UIElement>>>) :
    Iterator<ColumnData> {
    var index = 0

    override fun hasNext() = (index + 1) in layoutList.indices

    override fun next(): ColumnData {
        val next = layoutList[++index]
        return ColumnData(index, next.first, next.second, next.third, next.fourth.toMutableList())
    }

}