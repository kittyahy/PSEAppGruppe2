package com.pseandroid2.dailydata.repository.commandCenter.commands

import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI

class LeaveOnlineProject(projectID: Int, api: RepositoryViewModelAPI) :
    ProjectCommand(projectID = projectID, repositoryViewModelAPI = api) {
    companion object {
        fun isPossible(): Boolean {
            return false
        }

        const val isAdminOperation: Boolean = false

        const val publishable: Boolean = false
    }

    override suspend fun execute() {
        @Suppress("DEPRECATION")
        repositoryViewModelAPI.remoteDataSourceAPI.removeUser(
            repositoryViewModelAPI.remoteDataSourceAPI.getUserID(),
            repositoryViewModelAPI.appDataBase.projectDataDAO().getOnlineId(projectID!!)
        )
        @Suppress("DEPRECATION")
        repositoryViewModelAPI.appDataBase.projectDataDAO().setOnline(false, projectID!!)
        super.execute()
    }

    override suspend fun publish(): Boolean {
        return super.publish() && publishable
    }


}
