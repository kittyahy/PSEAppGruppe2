package com.pseandroid2.dailydata.repository.commandCenter.commands

import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.ViewModelProject

class DeleteProject(private val viewModelProject: ViewModelProject, api: RepositoryViewModelAPI) :
    ProjectCommand(projectID = viewModelProject.id, repositoryViewModelAPI = api) {

    override suspend fun publish(): Boolean {
        return super.publish() && publishable
    }

    companion object {
        const val publishable: Boolean = false
        fun isIllegal(viewModelProject: ViewModelProject): Boolean {
            return ProjectCommand.isIllegal(viewModelProject)
        }
    }

    override suspend fun execute() {
        @Suppress("DEPRECATION")
        repositoryViewModelAPI.appDataBase.projectCDManager().deleteProject(viewModelProject)
    }

}
