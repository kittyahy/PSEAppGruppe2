package com.pseandroid2.dailydata.repository.commandCenter.commands

import com.pseandroid2.dailydata.model.project.Project
import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.ViewModelProject

class PublishProject(private val viewModelProject: ViewModelProject, api: RepositoryViewModelAPI) :
    ProjectCommand(projectID = viewModelProject.id, repositoryViewModelAPI = api) {

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
            viewModelProject.id,
            repositoryViewModelAPI.remoteDataSourceAPI.createNewOnlineProject("")
        ) //Todo Fehlerbehandlung, falls publishen fehl schlägt
        viewModelProject.isOnline = true
        if (publish()) {
            repositoryViewModelAPI.projectHandler.executeQueue.publishQueue.add(
                CreateProject(
                    viewModelProject, null, repositoryViewModelAPI
                )
            )
        }


    }

    override suspend fun publish(): Boolean {
        return super.publish() && publishable
    }
}
