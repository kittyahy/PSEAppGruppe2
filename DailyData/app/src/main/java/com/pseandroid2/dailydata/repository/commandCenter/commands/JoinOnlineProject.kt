package com.pseandroid2.dailydata.repository.commandCenter.commands

import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.repository.commandCenter.PublishQueue
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Project
import kotlinx.coroutines.flow.MutableSharedFlow

class JoinOnlineProject(private val onlineID: Long, private val idFlow: MutableSharedFlow<Int>) : ProjectCommand() {
    companion object {
        fun isPossible(): Boolean {
            return false
        }
    }

    override suspend fun execute(
        repositoryViewModelAPI: RepositoryViewModelAPI,
        publishQueue: PublishQueue
    ) {
        repositoryViewModelAPI.remoteDataSourceAPI.joinProject(onlineID)
        val onlineProject = Project(repositoryViewModelAPI)
        CreateProject()
        super.execute(repositoryViewModelAPI, publishQueue)
    }

    override val publishable: Boolean = false


}
