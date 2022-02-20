package com.pseandroid2.dailydata.repository.commandCenter.commands

import com.pseandroid2.dailydata.model.project.Project
import com.pseandroid2.dailydata.model.users.SimpleUser
import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI

class ResetAdmin(projectID: Int, api: RepositoryViewModelAPI) :
    ProjectCommand(projectID = projectID, repositoryViewModelAPI = api) {

    companion object {
        fun isIllegal(project: Project): Boolean {
            return project.isOnline
        }

        const val isAdminOperation: Boolean = false

        const val publishable: Boolean = true
    }

    override suspend fun execute() {
        if (cameFromServer) {
            @Suppress("Deprecation")
            repositoryViewModelAPI.appDataBase.projectDataDAO().changeAdmin(
                projectID,
                SimpleUser(
                    repositoryViewModelAPI.remoteDataSourceAPI.getProjectAdmin(
                        @Suppress("DEPRECATION")
                        repositoryViewModelAPI.appDataBase.projectDataDAO().getOnlineId(projectID)
                    ),
                    "ADMIN"
                )
            )
            @Suppress("DEPRECATION")
            repositoryViewModelAPI.appDataBase.projectDataDAO().setOnline(true, projectID)
        }

    }

    override suspend fun publish(): Boolean {
        return super.publish() && publishable
    }
}
