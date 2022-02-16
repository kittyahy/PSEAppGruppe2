package com.pseandroid2.dailydata.repository.commandCenter.commands

import com.pseandroid2.dailydata.model.project.Project
import com.pseandroid2.dailydata.model.users.User
import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI

class AddMember(projectID: Int, private val user: User, api: RepositoryViewModelAPI) :
    ProjectCommand(projectID = projectID, repositoryViewModelAPI = api) {
    companion object {
        fun isPossible(project: Project): Boolean {
            return project.users.size < 24 && project.isOnline
        }

        const val isAdminOperation: Boolean = true

        const val publishable: Boolean = true
    }

    override suspend fun execute() {
        @Suppress("Deprecation")
        repositoryViewModelAPI.appDataBase.projectDataDAO().addUser(projectID!!, user)
        super.execute()
    }

    override suspend fun publish(): Boolean {
        return super.publish() && publishable
    }
}
