package com.pseandroid2.dailydata.repository.commandCenter.commands

import com.pseandroid2.dailydata.model.project.Project
import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI

class DeleteProject(private val projectId: Int, api: RepositoryViewModelAPI) :
    ProjectCommand(projectID = projectId, repositoryViewModelAPI = api) {

    override suspend fun publish(): Boolean {
        return super.publish() && publishable
    }

    companion object {
        const val publishable: Boolean = false
        fun isIllegal(project: Project): Boolean {
            return isIllegal()
        }
    }

    override suspend fun execute() {
        @Suppress("DEPRECATION")
        repositoryViewModelAPI.appDataBase.projectCDManager().deleteProjectById(projectID)
    }

}
