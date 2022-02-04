package com.pseandroid2.dailydata.model.table

import com.google.gson.Gson
import com.pseandroid2.dailydata.model.uielements.UIElement
import com.pseandroid2.dailydata.util.Quadruple
import com.pseandroid2.dailydata.util.fromJson
import com.pseandroid2.dailydata.util.getSerializableClassName
import kotlin.reflect.KClass

class ArrayListLayout(input: String = "") : TableLayout {
    private var layout: MutableList<Quadruple<String, String, String, MutableList<UIElement>>> =
        if (input == "") {
            mutableListOf()
        } else {
            Gson().fromJson(input)
        }

    constructor(layoutList: ArrayList<ColumnData>) : this("") {
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

    override fun getSize() = layout.size

    override fun getColumnType(col: Int) = Class.forName(layout[col].first).kotlin

    override fun getUIElements(col: Int): List<UIElement> = layout[col].fourth.toList()

    override fun addUIElements(col: Int, vararg elements: UIElement) {
        layout[col].fourth.addAll(elements)
    }

    override fun getName(col: Int) = layout[col].second

    override fun getUnit(col: Int) = layout[col].third

    override fun get(col: Int) = ColumnData(
        col,
        layout[col].first,
        layout[col].second,
        layout[col].third,
        layout[col].fourth.toList()
    )

    override fun addColumn(typeString: String, name: String, unit: String) {
        layout.add(Quadruple(typeString, name, unit, mutableListOf()))
    }

    override fun deleteColumn(col: Int) {
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

class ArrayListLayoutIterator(private val layoutList: List<Quadruple<String, String, String, List<UIElement>>>) :
    Iterator<ColumnData> {
    var index = 0

    override fun hasNext() = (index + 1) in layoutList.indices

    override fun next(): ColumnData {
        val next = layoutList[++index]
        return ColumnData(index, next.first, next.second, next.third, next.fourth.toList())
    }

}