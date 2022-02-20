package com.pseandroid2.dailydata.repository.commandCenter.commands

import com.pseandroid2.dailydata.model.project.Project
import com.pseandroid2.dailydata.model.users.User
import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI

class AddUser(private val id: Int, private val user: User, api: RepositoryViewModelAPI) :
    ProjectCommand(projectID = id, repositoryViewModelAPI = api) {
    companion object {
        fun isIllegal(project: Project): Boolean {
            return project.users.size >= 24 || !project.isOnline
        }

        const val publishable: Boolean = true
    }

    override suspend fun execute() {
        @Suppress("Deprecation")
        repositoryViewModelAPI.appDataBase.projectDataDAO().addUser(id, user)
        super.execute()
    }
}
