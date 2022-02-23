package com.pseandroid2.dailydata.repository.commandCenter.commands

import com.pseandroid2.dailydata.model.uielements.UIElement
import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.repository.commandCenter.PublishQueue
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Project

class SetButton(projectID: Int, val button: UIElement) : ProjectCommand(projectID = projectID) {
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
        val uIElementDAO =
            @Suppress("DEPRECATION")
            repositoryViewModelAPI.appDataBase.uiElementDAO()
        uIElementDAO.changeUIElementName(projectID!!, button.id, button.name)
        uIElementDAO.changeUIElementState(projectID!!, button.id, button.state)

        super.execute(repositoryViewModelAPI, publishQueue)
    }

    override suspend fun publish(
        repositoryViewModelAPI: RepositoryViewModelAPI,
        publishQueue: PublishQueue
    ): Boolean {
        return super.publish(repositoryViewModelAPI, publishQueue) && publishable
    }

}
