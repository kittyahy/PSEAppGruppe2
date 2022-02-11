package com.pseandroid2.dailydata.repository.commandCenter.commands

import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.repository.commandCenter.PublishQueue
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Project

abstract class OnlineAdminCommand(project: Project) :
    ProjectCommand(isProjectAdmin = project.isAdmin, projectID = project.id) {
    //Todo sicherstellen ob isAdmin wirklich aus einem CommunicationClasses Project ausgelsesn werden sollte
    init {
        if (!project.isAdmin) {
            throw IllegalOperationException("This command is only usable by project admins and you are no project admin.")
        }
    }

    companion object {
        fun isPossible(project: Project): Boolean {
            return project.isAdmin
        }
    }

    override val publishable = true

    override suspend fun publish(
        repositoryViewModelAPI: RepositoryViewModelAPI,
        publishQueue: PublishQueue
    ): Boolean {

        return isProjectAdmin
    }
}