package com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses

import com.pseandroid2.dailydata.model.table.ColumnData
import com.pseandroid2.dailydata.model.table.TableLayout
import com.pseandroid2.dailydata.model.uielements.UIElement
import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.repository.commandCenter.commands.AddColumn
import com.pseandroid2.dailydata.repository.commandCenter.commands.AddUIElement
import com.pseandroid2.dailydata.repository.commandCenter.commands.DeleteColumn
import com.pseandroid2.dailydata.repository.commandCenter.commands.DeleteUIElement
import com.pseandroid2.dailydata.repository.commandCenter.commands.ProjectCommand
import com.pseandroid2.dailydata.repository.commandCenter.commands.SetColumn
import com.pseandroid2.dailydata.repository.commandCenter.commands.SetUIElement
import kotlinx.coroutines.flow.MutableSharedFlow
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
        saveToDB(
            AddUIElement(
                projectID,
                element,
                col,
                repositoryViewModelAPI
            ),
            LayoutOperation.ADD_COLUMN.id
        )

        return 0
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

    override suspend fun setUIElement(col: Int, element: UIElement) {
        saveToDB(
            SetUIElement(projectID, element, col, repositoryViewModelAPI),
            LayoutOperation.CHANGE_UIELEMENT.id
        )
    }

    override suspend fun removeUIElement(col: Int, id: Int) {
        saveToDB(
            DeleteUIElement(projectID, id, repositoryViewModelAPI),
            LayoutOperation.DELETE_UIELEMENT.id
        )
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

    override suspend fun addColumn(specs: ColumnData): Int {
        saveToDB(
            AddColumn(
                projectID,
                specs,
                repositoryViewModelAPI
            ), LayoutOperation.ADD_COLUMN.id
        )
        return 0
    }

    override suspend fun setColumn(specs: ColumnData) {
        saveToDB(
            SetColumn(projectID, specs, repositoryViewModelAPI),
            LayoutOperation.CHANGE_COLUMN.id
        )
    }

    override suspend fun deleteColumn(col: Int) {
        saveToDB(
            DeleteColumn(projectID, col, repositoryViewModelAPI),
            LayoutOperation.DELETE_COLUMN.id
        )
    }

    override fun toJSON(): String {
        return tableLayout.toJSON()
    }

    override fun iterator(): Iterator<ColumnData> {
        return tableLayout.iterator()
    }

    override val columnDataList: List<ColumnData>
        get() = tableLayout.columnDataList

    private suspend fun saveToDB(projectCommand: ProjectCommand, vararg operationIDs: String) {
        for (id in operationIDs) {
            @Suppress("DEPRECATION")
            mutableIllegalOperation[id]!!.emit(false)
        }
        repositoryViewModelAPI.projectHandler.executeQueue.add(projectCommand)
    }

}
