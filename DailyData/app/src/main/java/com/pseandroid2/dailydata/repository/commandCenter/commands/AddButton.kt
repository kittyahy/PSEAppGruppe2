package com.pseandroid2.dailydata.repository.commandCenter.commands

import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.repository.commandCenter.PublishQueue
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Button
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.ViewModelProject

class AddButton(projectID: Int, val button: Button) : ProjectCommand(projectID = projectID) {
    companion object {
        fun isPossible(viewModelProject: ViewModelProject): Boolean {
            return ProjectCommand.isPossible(viewModelProject)
        }
    }

    override val publishable: Boolean = false

    override suspend fun execute(
        repositoryViewModelAPI: RepositoryViewModelAPI,
        publishQueue: PublishQueue
    ) {
        val uiElement = button.toDBEquivalent()
        repositoryViewModelAPI.appDataBase.uiElementDAO()
            .insertUIElement(projectID!!, button.columnId, uiElement)
        super.execute(repositoryViewModelAPI, publishQueue)
    }

}
