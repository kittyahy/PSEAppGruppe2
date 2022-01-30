package com.pseandroid2.dailydata.model.table

import com.google.gson.Gson
import com.pseandroid2.dailydata.model.uielements.UIElement
import com.pseandroid2.dailydata.util.fromJson
import com.pseandroid2.dailydata.util.getSerializableClassName
import kotlin.reflect.KClass

class ArrayListLayout(input: String = "") : TableLayout {
    private var layout: MutableList<Pair<String, MutableList<UIElement>>> =
        if (input == "") {
            mutableListOf()
        } else {
            Gson().fromJson(input)
        }

    constructor(layoutList: ArrayList<Pair<String, MutableList<UIElement>>>) : this("") {
        layout = layoutList
    }

    override fun getSize() = layout.size

    override fun getColumnType(col: Int) = Class.forName(layout[col].first).kotlin

    override fun getUIElements(col: Int): List<UIElement> = layout[col].second.toList()

    override fun addUIElements(col: Int, vararg elements: UIElement) {
        layout[col].second.addAll(elements)
    }

    override fun get(col: Int): Pair<KClass<out Any>, List<UIElement>> {
        val type: KClass<out Any> = Class.forName(layout[col].first).kotlin
        return Pair(type, layout[col].second)
    }

    override fun addColumn(typeString: String) {
        layout.add(Pair(typeString, mutableListOf<UIElement>()))
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
    Iterator<Pair<KClass<out Any>, MutableList<UIElement>>> {
    @Suppress("Deprecation")
    val iterator = layout.getList().iterator()

    override fun hasNext() = iterator.hasNext()

    override fun next(): Pair<KClass<out Any>, MutableList<UIElement>> {
        val next = iterator.next()
        return Pair(Class.forName(next.first).kotlin, next.second)
    }

}