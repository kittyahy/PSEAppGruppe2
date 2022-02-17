package com.pseandroid2.dailydata.repository.commandCenter.commands

import com.pseandroid2.dailydata.model.project.Project
import com.pseandroid2.dailydata.model.users.SimpleUser
import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.ViewModelProject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.first

class JoinOnlineProject(
    private val onlineID: Long,
    private val idFlow: MutableSharedFlow<Int>,
    api: RepositoryViewModelAPI
) :
    ProjectCommand(onlineProjectID = onlineID, repositoryViewModelAPI = api) {
    override val publishable: Boolean = false

    companion object {
        fun isIllegal(project: Project): Boolean {
            return true
        }
    }

    override suspend fun execute() {
        @Suppress("DEPRECATION")
        repositoryViewModelAPI.remoteDataSourceAPI.joinProject(onlineID)
        val admin = SimpleUser(
            repositoryViewModelAPI.remoteDataSourceAPI.getProjectAdmin(onlineID),
            "ADMIN"
        )
        val onlineProject =
            ViewModelProject(repositoryViewModelAPI = repositoryViewModelAPI, admin = admin)
        val idFlow = MutableSharedFlow<Int>()
        CreateProject(onlineProject, idFlow, repositoryViewModelAPI).execute()
        val id: Int = idFlow.first()
        @Suppress("DEPRECATION")
        repositoryViewModelAPI.appDataBase.projectDataDAO().setOnlineID(id, onlineProjectID!!)
        super.execute()
    }


}
