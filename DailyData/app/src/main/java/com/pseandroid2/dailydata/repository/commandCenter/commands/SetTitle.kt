package com.pseandroid2.dailydata.repository.commandCenter.commands

import com.pseandroid2.dailydata.model.project.Project
import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.ViewModelProject

class SetTitle(
    viewModelProject: ViewModelProject,
    private val newTitle: String,
    api: RepositoryViewModelAPI
) : ProjectCommand(projectID = viewModelProject.id, repositoryViewModelAPI = api) {


    companion object {

        fun isIllegal(project: Project): Boolean {
            return ProjectCommand.isIllegal(project)
        }

        const val isAdminOperation: Boolean = true

        const val publishable: Boolean = true
    }

    override suspend fun execute() {
        repositoryViewModelAPI.appDataBase.projectDataDAO().setName(projectID!!, newTitle)
    }

    override suspend fun publish(): Boolean {
        return super.publish() && publishable
    }
}