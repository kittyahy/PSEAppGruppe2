package com.pseandroid2.dailydata.repository.commandCenter.commands

import com.pseandroid2.dailydata.model.project.Project
import com.pseandroid2.dailydata.model.uielements.UIElement
import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI

class SetButton(projectID: Int, val button: UIElement, api: RepositoryViewModelAPI) :
    ProjectCommand(projectID = projectID, repositoryViewModelAPI = api) {
    companion object {
        fun isIllegal(project: Project): Boolean {
            return ProjectCommand.isIllegal(project)
        }

        const val isAdminOperation: Boolean = false

        const val publishable: Boolean = false
    }

    override suspend fun execute() {
        val uIElementDAO =
            @Suppress("DEPRECATION")
            repositoryViewModelAPI.appDataBase.uiElementDAO()
        uIElementDAO.changeUIElementName(projectID!!, button.id, button.name)
        uIElementDAO.changeUIElementState(projectID!!, button.id, button.state)

        super.execute()
    }

    override suspend fun publish(): Boolean {
        return super.publish() && publishable
    }

}
