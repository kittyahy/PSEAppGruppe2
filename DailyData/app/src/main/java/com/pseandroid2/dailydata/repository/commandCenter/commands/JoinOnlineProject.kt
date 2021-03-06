package com.pseandroid2.dailydata.repository.commandCenter.commands

import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.repository.commandCenter.PublishQueue
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Project
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.first

class JoinOnlineProject(private val onlineID: Long, private val idFlow: MutableSharedFlow<Int>) :
    ProjectCommand() {
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
        repositoryViewModelAPI.remoteDataSourceAPI.joinProject(onlineID)
        val onlineProject = Project(repositoryViewModelAPI = repositoryViewModelAPI)
        val idFlow = MutableSharedFlow<Int>()
        CreateProject(onlineProject, idFlow).execute(repositoryViewModelAPI, publishQueue)
        val id: Int = idFlow.first()
        @Suppress("DEPRECATION")
        repositoryViewModelAPI.appDataBase.projectDataDAO().setOnlineID(id, onlineID)
        super.execute(repositoryViewModelAPI, publishQueue)
    }

    override fun publish(
        repositoryViewModelAPI: RepositoryViewModelAPI,
        publishQueue: PublishQueue
    ): Boolean {
        return super.publish(repositoryViewModelAPI, publishQueue) && publishable
    }


}
