package com.pseandroid2.dailydata.repository.commandCenter.commands

import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.repository.commandCenter.PublishQueue
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Button
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Project

class AddButton(val id: Int, val button: Button) : ProjectCommand() {
    companion object {
        fun isPossible(project: Project): Boolean {
            return ProjectCommand.isPossible(project)
        }
    }
    override val publishable: Boolean = false

    override suspend fun execute(
        repositoryViewModelAPI: RepositoryViewModelAPI,
        publishQueue: PublishQueue
    ) {
        val uiElement = button.toDBEquivalent()
        repositoryViewModelAPI.appDataBase.uiElementDAO().insertUIElement(id, button.columnId, uiElement)
        super.execute(repositoryViewModelAPI, publishQueue)
    }

}