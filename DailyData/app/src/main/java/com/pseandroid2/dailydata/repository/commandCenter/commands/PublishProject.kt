package com.pseandroid2.dailydata.repository.commandCenter.commands

import com.pseandroid2.dailydata.model.project.Project
import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import kotlinx.coroutines.flow.first

class PublishProject(projectID: Int, api: RepositoryViewModelAPI) :
    ProjectCommand(projectID = projectID, repositoryViewModelAPI = api) {

    companion object {
        fun isIllegal(project: Project): Boolean {
            return project.isOnline
        }

        const val isAdminOperation: Boolean = false

        const val publishable: Boolean = false
    }

    override suspend fun execute() {
        @Suppress("Deprecation")
        repositoryViewModelAPI.appDataBase.projectDataDAO().setOnlineID(
            projectID,
            repositoryViewModelAPI.remoteDataSourceAPI.createNewOnlineProject("")
        ) //Todo Fehlerbehandlung, falls publishen fehl schlägt
        repositoryViewModelAPI.appDataBase.projectDataDAO().setOnline(true, projectID)

        val project = repositoryViewModelAPI.projectHandler.getProjectByID(projectID)
        repositoryViewModelAPI.projectHandler.executeQueue.publishQueue.add(
            CreateProject(
                project.first(), null, repositoryViewModelAPI
            )
        )


    }

    override suspend fun publish(): Boolean {
        return super.publish() && publishable
    }
}
