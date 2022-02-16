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
        fun isIllegal(viewModelProject: ViewModelProject): Boolean {
            return ProjectCommand.isIllegal(viewModelProject)
        }
    }

    override val publishable: Boolean = false

    override suspend fun execute() {
        @Suppress("Deprecation")
        repositoryViewModelAPI.appDataBase.uiElementDAO()
            .insertUIElement(projectID!!, col, uiElement)
        super.execute()
    }

}
