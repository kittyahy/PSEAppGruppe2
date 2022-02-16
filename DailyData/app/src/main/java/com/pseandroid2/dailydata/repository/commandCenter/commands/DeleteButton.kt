package com.pseandroid2.dailydata.repository.commandCenter.commands

import com.pseandroid2.dailydata.model.uielements.UIElement
import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.ViewModelProject

class DeleteButton(projectID: Int, val uiElement: UIElement, api: RepositoryViewModelAPI) :
    ProjectCommand(projectID = projectID, repositoryViewModelAPI = api) {
    companion object {
        fun isIllegal(project: ViewModelProject): Boolean {
            return ProjectCommand.isIllegal(project)
        }
    }

    override val publishable: Boolean = false

    override suspend fun execute() {
        @Suppress("Deprecation")
        repositoryViewModelAPI.appDataBase.uiElementDAO()
            .removeUIElements(projectID!!, uiElement.id)
        super.execute()
    }

}
