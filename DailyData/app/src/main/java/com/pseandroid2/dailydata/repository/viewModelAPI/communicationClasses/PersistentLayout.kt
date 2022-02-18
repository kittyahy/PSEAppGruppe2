package com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses

import com.pseandroid2.dailydata.model.table.ColumnData
import com.pseandroid2.dailydata.model.table.TableLayout
import com.pseandroid2.dailydata.model.uielements.UIElement
import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.repository.commandCenter.commands.AddColumn
import com.pseandroid2.dailydata.repository.commandCenter.commands.AddUIElement
import com.pseandroid2.dailydata.repository.commandCenter.commands.DeleteUIElement
import com.pseandroid2.dailydata.repository.commandCenter.commands.ProjectCommand
import kotlin.reflect.KClass

class PersistentLayout(
    private val tableLayout: TableLayout,
    private val repositoryViewModelAPI: RepositoryViewModelAPI,
    private val projectID: Int
) :
    TableLayout {
    override val size: Int
        get() = tableLayout.size

    override fun getColumnType(col: Int): KClass<out Any> {
        return tableLayout.getColumnType(col)
    }

    override fun getUIElements(col: Int): List<UIElement> {
        return tableLayout.getUIElements(col)
    }

    override suspend fun addUIElement(col: Int, element: UIElement): Int {
        s(
            AddUIElement(
                projectID,
                element,
                col,
                repositoryViewModelAPI
            )
        )

        return 0
    }

    override suspend fun removeUIElement(col: Int, id: Int) {
        s(DeleteUIElement(projectID, id, repositoryViewModelAPI))
    }

    override fun getName(col: Int): String {
        return tableLayout.getName(col)
    }

    override fun getUnit(col: Int): String {
        return tableLayout.getUnit(col)
    }

    override fun get(col: Int): ColumnData {
        return tableLayout[col]
    }

    override suspend fun addColumn(type: DataType, name: String, unit: String): Int {
        s(
            AddColumn(
                projectID,
                ColumnData(type = type, name = name, unit = unit),
                repositoryViewModelAPI
            )
        )
        return 0
    }

    override fun deleteColumn(col: Int) {
        return TODO()
    }

    override fun toJSON(): String {
        return tableLayout.toJSON()
    }

    override fun iterator(): Iterator<ColumnData> {
        return tableLayout.iterator()
    }

    private suspend fun s(projectCommand: ProjectCommand) {
        repositoryViewModelAPI.projectHandler.executeQueue.add(projectCommand)
    }

}
