package com.pseandroid2.dailydata.repository.commandCenter.commands

import com.pseandroid2.dailydata.model.uielements.UIElement
import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.ViewModelProject

class DeleteButton(projectID: Int, val uiElement: UIElement, api: RepositoryViewModelAPI) :
    ProjectCommand(projectID = projectID, repositoryViewModelAPI = api) {
    companion object {
        fun isPossible(project: ViewModelProject): Boolean {
            return ProjectCommand.isPossible(project)
        }

        const val isAdminOperation: Boolean = false

        const val publishable: Boolean = false
    }

    override suspend fun execute() {
        @Suppress("Deprecation")
        repositoryViewModelAPI.appDataBase.uiElementDAO()
            .removeUIElements(projectID!!, uiElement.id)
        super.execute()
    }

    override suspend fun publish(): Boolean {
        return super.publish() && publishable
    }

}
