package com.pseandroid2.dailydata.repository.commandCenter.commands

import com.pseandroid2.dailydata.model.project.InMemoryProject
import com.pseandroid2.dailydata.model.project.Project
import com.pseandroid2.dailydata.model.users.SimpleUser
import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.first
import com.pseandroid2.dailydata.repository.viewModelAPI.ProjectHandler


class JoinOnlineProject(
    private val onlineID: Long,
    api: RepositoryViewModelAPI
) : ProjectCommand(-1, repositoryViewModelAPI = api) {
    companion object {
        fun isIllegal(project: ProjectHandler): Boolean {
            return isIllegal()
        }

        const val isAdminOperation: Boolean = false

        const val publishable: Boolean = false
    }

    override suspend fun execute() {
        @Suppress("DEPRECATION")
        repositoryViewModelAPI.remoteDataSourceAPI.joinProject(onlineID)
        val admin = SimpleUser(
            @Suppress("DEPRECATION")
            repositoryViewModelAPI.remoteDataSourceAPI.getProjectAdmin(onlineID),
            "ADMIN"
        )
        val onlineProject =
            InMemoryProject(admin = admin)
        val id: Int = onlineProject.writeToDBAsync(repositoryViewModelAPI.projectHandler).await()
        @Suppress("DEPRECATION")
        repositoryViewModelAPI.appDataBase.projectDataDAO().setOnlineID(id, onlineID)
        super.execute()
    }

    override suspend fun publish(): Boolean {
        return super.publish() && publishable
    }


}
