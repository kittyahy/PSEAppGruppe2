package com.pseandroid2.dailydata.repository.commandCenter.commands

import com.pseandroid2.dailydata.model.project.Project
import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI

class SetDescription(
    projectID: Int,
    private val newDescription: String,
    api: RepositoryViewModelAPI
) : ProjectCommand(projectID = projectID, repositoryViewModelAPI = api) {

    companion object {
        fun isIllegal(project: Project): Boolean {
            return isIllegal()
        }

        const val isAdminOperation: Boolean = true

        const val publishable: Boolean = true
    }

    override suspend fun execute() {
        @Suppress("Deprecation")
        repositoryViewModelAPI.appDataBase.projectDataDAO()
            .setDescription(projectID, newDescription)
    }

    override suspend fun publish(): Boolean {
        return super.publish() && publishable
    }
}