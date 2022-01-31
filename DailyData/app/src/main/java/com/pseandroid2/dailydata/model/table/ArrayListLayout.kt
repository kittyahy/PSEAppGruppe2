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

    constructor(layoutList: ArrayList<Quadruple<String, String, String, MutableList<UIElement>>>) : this("") {
        layout = layoutList
    }

    override fun getSize() = layout.size

    override fun getColumnType(col: Int) = Class.forName(layout[col].first).kotlin

    override fun getUIElements(col: Int): List<UIElement> = layout[col].fourth.toList()

    override fun addUIElements(col: Int, vararg elements: UIElement) {
        layout[col].fourth.addAll(elements)
    }

    override fun getName(col: Int) = layout[col].second

    override fun getUnit(col: Int) = layout[col].third

    override fun get(col: Int): Quadruple<KClass<out Any>, String, String, List<UIElement>> {
        val type: KClass<out Any> = Class.forName(layout[col].first).kotlin
        return Quadruple(type, layout[col].second, layout[col].third, layout[col].fourth)
    }

    override fun addColumn(typeString: String, name: String, unit: String) {
        layout.add(Quadruple(typeString, name, unit, mutableListOf<UIElement>()))
    }

    override fun deleteColumn(col: Int) {
        layout.removeAt(col)
    }

    override fun toJSON(): String {
        return Gson().toJson(layout)
    }

    override fun iterator() = ArrayListLayoutIterator(this)

    @Deprecated("Shouldn't be used outside of ArrayListLayoutIterator, iterate over the layout instead")
    fun getList() = layout
}

class ArrayListLayoutIterator(layout: ArrayListLayout) :
    Iterator<Quadruple<KClass<out Any>, String, String, MutableList<UIElement>>> {
    @Suppress("Deprecation")
    val iterator = layout.getList().iterator()

    override fun hasNext() = iterator.hasNext()

    override fun next(): Quadruple<KClass<out Any>, String, String, MutableList<UIElement>> {
        val next = iterator.next()
        return Quadruple(Class.forName(next.first).kotlin, next.second, next.third, next.fourth)
    }

}