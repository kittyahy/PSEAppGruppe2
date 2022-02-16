package com.pseandroid2.dailydata.repository.commandCenter.commands

import com.pseandroid2.dailydata.model.uielements.UIElement
import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.repository.commandCenter.PublishQueue
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Button
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.ViewModelProject

class AddButton(
    projectID: Int,
    val uiElement: UIElement,
    val col: Int,
    api: RepositoryViewModelAPI
) : ProjectCommand(projectID = projectID, repositoryViewModelAPI = api) {
    companion object {
        fun isPossible(viewModelProject: ViewModelProject): Boolean {
            return ProjectCommand.isPossible(viewModelProject)
        }

        const val isAdminOperation: Boolean = false

        const val publishable: Boolean = false
    }

    override suspend fun execute() {
        @Suppress("Deprecation")
        repositoryViewModelAPI.appDataBase.uiElementDAO()
            .insertUIElement(projectID!!, col, uiElement)
        super.execute()
    }

    override suspend fun publish(): Boolean {
        return super.publish() && publishable
    }

}
