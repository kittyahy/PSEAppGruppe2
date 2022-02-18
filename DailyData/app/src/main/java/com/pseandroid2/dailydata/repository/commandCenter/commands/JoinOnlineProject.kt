package com.pseandroid2.dailydata.repository.commandCenter.commands

import com.pseandroid2.dailydata.model.project.Project
import com.pseandroid2.dailydata.model.users.SimpleUser
import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.ViewModelProject
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.first


class JoinOnlineProject(
    private val onlineID: Long,
    private val idFlow: MutableSharedFlow<Int>,
    api: RepositoryViewModelAPI
) : ProjectCommand(repositoryViewModelAPI = api) {
    companion object {
        fun isIllegal(project: Project): Boolean {
            return true
        }

        const val isAdminOperation: Boolean = false

        const val publishable: Boolean = false
    }

    override suspend fun execute() = coroutineScope { //TODO Does this still work?
        @Suppress("DEPRECATION")
        repositoryViewModelAPI.remoteDataSourceAPI.joinProject(onlineID)
        val admin = SimpleUser(
            repositoryViewModelAPI.remoteDataSourceAPI.getProjectAdmin(onlineID),
            "ADMIN"
        )
        val onlineProject =
            ViewModelProject(repo = repositoryViewModelAPI, admin = admin, scope = this)
        val idFlow = MutableSharedFlow<Int>()
        CreateProject(onlineProject, idFlow, repositoryViewModelAPI).execute()
        val id: Int = idFlow.first()
        @Suppress("DEPRECATION")
        repositoryViewModelAPI.appDataBase.projectDataDAO().setOnlineID(id, onlineID)
        super.execute()
    }

    override suspend fun publish(): Boolean {
        return super.publish() && publishable
    }


}
