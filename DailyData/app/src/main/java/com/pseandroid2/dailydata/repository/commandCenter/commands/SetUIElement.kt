package com.pseandroid2.dailydata.repository.commandCenter.commands

import com.pseandroid2.dailydata.model.project.Project
import com.pseandroid2.dailydata.model.table.TableLayout
import com.pseandroid2.dailydata.model.uielements.UIElement
import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI

class SetUIElement(
    projectID: Int,
    val uiElement: UIElement,
    val col: Int,
    api: RepositoryViewModelAPI
) : ProjectCommand(projectID = projectID, repositoryViewModelAPI = api) {
    companion object {
        fun isIllegal(layout: TableLayout): Boolean {
            return isIllegal()
        }

        const val isAdminOperation: Boolean = false

        const val publishable: Boolean = false
    }

    override suspend fun execute() {
        @Suppress("Deprecation")
        repositoryViewModelAPI.appDataBase.uiElementDAO()
            .changeUIElementState(projectID, col, uiElement.state)
        @Suppress("Deprecation")
        repositoryViewModelAPI.appDataBase.uiElementDAO()
            .changeUIElementName(projectID, col, uiElement.name)
        super.execute()
    }

    override suspend fun publish(): Boolean {
        return super.publish() && publishable
    }

}
