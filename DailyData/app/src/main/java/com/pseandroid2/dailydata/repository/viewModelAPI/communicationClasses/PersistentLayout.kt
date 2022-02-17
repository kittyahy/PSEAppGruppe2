package com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses

import com.pseandroid2.dailydata.model.table.ColumnData
import com.pseandroid2.dailydata.model.table.TableLayout
import com.pseandroid2.dailydata.model.uielements.UIElement
import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.repository.commandCenter.commands.AddUIElement
import kotlin.reflect.KClass

class PersistentLayout(
    private val l: TableLayout,
    private val repositoryViewModelAPI: RepositoryViewModelAPI,
    private val projectID: Int
) :
    TableLayout {
    override val size: Int
        get() = l.size

    override fun getColumnType(col: Int): KClass<out Any> {
        return l.getColumnType(col)
    }

    override fun getUIElements(col: Int): List<UIElement> {
        return l.getUIElements(col)
    }

    override suspend fun addUIElement(col: Int, element: UIElement): Int {
        repositoryViewModelAPI.projectHandler.executeQueue.add(
            AddUIElement(
                projectID,
                element,
                col,
                repositoryViewModelAPI
            )
        )
        return 0
    }

    override fun removeUIElement(col: Int, id: Int) {
        return TODO()
    }

    override fun getName(col: Int): String {
        return l.getName(col)
    }

    override fun getUnit(col: Int): String {
        return l.getUnit(col)
    }

    override fun get(col: Int): ColumnData {
        return l.get(col)
    }

    override fun addColumn(type: DataType, name: String, unit: String): Int {
        return TODO()
    }

    override fun deleteColumn(col: Int) {
        return TODO()
    }

    override fun toJSON(): String {
        return l.toJSON()
    }

    override fun iterator(): Iterator<ColumnData> {
        return l.iterator()
    }

}
