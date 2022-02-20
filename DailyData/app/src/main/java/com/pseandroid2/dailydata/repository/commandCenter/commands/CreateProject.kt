package com.pseandroid2.dailydata.repository.commandCenter.commands

import android.util.Log
import com.pseandroid2.dailydata.model.project.Project
import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.repository.commandCenter.PublishQueue
import com.pseandroid2.dailydata.util.Consts.LOG_TAG
import com.pseandroid2.dailydata.repository.viewModelAPI.ProjectHandler
import kotlinx.coroutines.flow.MutableSharedFlow

class CreateProject(
    private val project: Project,
    private val projectIDReturn: MutableSharedFlow<Int>? = null,
    api: RepositoryViewModelAPI
) : ProjectCommand(
    project.id,
    commandByUser = @Suppress("Deprecation") api.remoteDataSourceAPI.getUserID(),
    createdByAdmin = true,
    repositoryViewModelAPI = api
) {

    companion object {
        fun isIllegal(project: ProjectHandler): Boolean {
            return isIllegal()
        }

        const val isAdminOperation: Boolean = false

        const val publishable: Boolean = false
    }

    override suspend fun execute() {
        @Suppress("Deprecation")
        val project1 = repositoryViewModelAPI.appDataBase.projectCDManager().insertProject(project)
        projectID = project1.id
        projectIDReturn?.emit(projectID)
        super.execute()
    }

    override suspend fun publish(): Boolean {
        return super.publish() && publishable
    }
}