package com.pseandroid2.dailydata.repository.commandCenter.commands

import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.repository.commandCenter.PublishQueue
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Project
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.first

class LeaveOnlineProject(private val onlineID: Long) :
    ProjectCommand(onlineProjectID = onlineID) {
    companion object {
        fun isPossible(): Boolean {
            return false
        }

        const val issuerNeedsAdminRights: Boolean = false

        const val publishable: Boolean = false
    }

    override suspend fun execute(
        repositoryViewModelAPI: RepositoryViewModelAPI,
        publishQueue: PublishQueue
    ) {
        @Suppress("DEPRECATION")
        repositoryViewModelAPI.remoteDataSourceAPI.removeUser(
            repositoryViewModelAPI.remoteDataSourceAPI.getUserID(),
            onlineProjectID!!
        )
        @Suppress("DEPRECATION")
        repositoryViewModelAPI.appDataBase.projectDataDAO().setOnline(false, projectID!!)
        super.execute(repositoryViewModelAPI, publishQueue)
    }

    override fun publish(
        repositoryViewModelAPI: RepositoryViewModelAPI,
        publishQueue: PublishQueue
    ): Boolean {
        return super.publish(repositoryViewModelAPI, publishQueue) && publishable
    }


}
