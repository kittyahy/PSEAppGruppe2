package com.pseandroid2.dailydata.repository.commandCenter.commands

import com.pseandroid2.dailydata.model.project.Project
import com.pseandroid2.dailydata.model.table.ColumnData
import com.pseandroid2.dailydata.model.table.TableLayout
import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.DataType

class AddColumn(projectID: Int, private val specs: ColumnData, api: RepositoryViewModelAPI) :
    ProjectCommand(projectID = projectID, repositoryViewModelAPI = api) {
    companion object {
        fun isIllegal(project: Project): Boolean {
            return addableTypes(project.table.layout).isEmpty()
        }

        const val isAdminOperation: Boolean = true

        const val publishable: Boolean = true

        fun addableTypes(tableLayout: TableLayout): Collection<DataType> {
            val list: MutableList<DataType> = ArrayList()
            var total = DataType.storageSizeBaseline
            for (col in tableLayout) {
                total += col.type.storageSize
            }
            for (dataType in DataType.values()) {
                if (total + dataType.storageSize <= DataType.maxStorageSize) {
                    list.add(dataType)
                }
            }
            return list
        }
    }

    override suspend fun execute() {
        @Suppress("Deprecation")
        repositoryViewModelAPI.appDataBase.layoutDAO().addColumn(projectID, specs)
    }

    override suspend fun publish(): Boolean {
        return super.publish() && publishable
    }

}
