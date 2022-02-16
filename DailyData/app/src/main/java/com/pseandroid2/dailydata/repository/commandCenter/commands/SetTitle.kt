package com.pseandroid2.dailydata.repository.commandCenter.commands

import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.ViewModelProject

class SetTitle(
    viewModelProject: ViewModelProject,
    private val newTitle: String,
    api: RepositoryViewModelAPI
) : ProjectCommand(projectID = viewModelProject.id, repositoryViewModelAPI = api) {


    override val publishable = true

    companion object {

        fun isIllegal(project: ViewModelProject): Boolean {
            return ProjectCommand.isIllegal(project)
        }
    }

    override suspend fun execute() {
        repositoryViewModelAPI.appDataBase.projectDataDAO().setName(projectID!!, newTitle)
    }
}