package com.pseandroid2.dailydata.repository.commandCenter.commands

import com.pseandroid2.dailydata.model.project.Project
import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.repository.commandCenter.PublishQueue
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.ViewModelProject
import kotlinx.coroutines.flow.MutableSharedFlow

class CreateProject(
    private val project: Project,
    private val projectIDReturn: MutableSharedFlow<Int>? = null,
    api: RepositoryViewModelAPI
) : ProjectCommand(
    commandByUser = @Suppress("Deprecation") api.remoteDataSourceAPI.getUserID(),
    createdByAdmin = true,
    repositoryViewModelAPI = api
) {

    companion object {
        fun isIllegal(project: Project): Boolean {
            return ProjectCommand.isIllegal(project)
        }

        const val isAdminOperation: Boolean = false

        const val publishable: Boolean = false
    }

    override suspend fun execute() {
        @Suppress("Deprecation")
        val id = repositoryViewModelAPI.appDataBase.projectCDManager().insertProject(project)
        projectID = id
        projectIDReturn?.emit(id)
        super.execute()
    }

    override suspend fun publish(): Boolean {
        return super.publish() && publishable
    }
}