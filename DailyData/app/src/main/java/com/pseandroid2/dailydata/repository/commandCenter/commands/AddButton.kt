package com.pseandroid2.dailydata.repository.commandCenter.commands

import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.repository.commandCenter.PublishQueue
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Button
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Project

class AddButton(projectID: Int, val button: Button) : ProjectCommand(projectID = projectID) {
    companion object {
        fun isPossible(project: Project): Boolean {
            return ProjectCommand.isPossible(project)
        }

        const val issuerNeedsAdminRights: Boolean = false

        const val publishable: Boolean = false
    }

    override suspend fun execute(
        repositoryViewModelAPI: RepositoryViewModelAPI,
        publishQueue: PublishQueue
    ) {
        val uiElement = button.toDBEquivalent()
        repositoryViewModelAPI.appDataBase.uiElementDAO()
            .insertUIElement(projectID!!, button.columnId, uiElement)
        super.execute(repositoryViewModelAPI, publishQueue)
    }

    override suspend fun publish(
        repositoryViewModelAPI: RepositoryViewModelAPI,
        publishQueue: PublishQueue
    ): Boolean {
        return super.publish(repositoryViewModelAPI, publishQueue) && publishable
    }

}
