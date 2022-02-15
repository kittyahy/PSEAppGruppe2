package com.pseandroid2.dailydata.repository.commandCenter.commands

import com.pseandroid2.dailydata.model.users.User
import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.repository.commandCenter.PublishQueue

class DeleteMember(projectID: Int, private val user: User) :
    ProjectCommand(projectID = projectID) {
    companion object {
        fun isPossible(project: Project): Boolean {
            return project.members.size > 2 && project.isOnlineProject
        }
    }

    override val publishable: Boolean = true
    override suspend fun execute(
        repositoryViewModelAPI: RepositoryViewModelAPI,
        publishQueue: PublishQueue
    ) {
        repositoryViewModelAPI.appDataBase.projectDataDAO()
            .removeUsers(projectID!!, user)
        super.execute(repositoryViewModelAPI, publishQueue)
    }
}
