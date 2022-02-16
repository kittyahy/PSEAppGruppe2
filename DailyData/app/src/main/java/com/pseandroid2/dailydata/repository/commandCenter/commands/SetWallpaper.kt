package com.pseandroid2.dailydata.repository.commandCenter.commands

import com.pseandroid2.dailydata.model.project.Project
import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.repository.commandCenter.PublishQueue

class SetWallpaper(projectID: Int, private val newWallpaper: Int, api: RepositoryViewModelAPI) :
    ProjectCommand(projectID = projectID, repositoryViewModelAPI = api) {

    companion object {
        fun isPossible(project: Project): Boolean {
            return ProjectCommand.isPossible(project)
        }

        const val isAdminOperation: Boolean = true

        const val publishable: Boolean = true
    }

    override suspend fun execute() {
        @Suppress("Deprecation")
        repositoryViewModelAPI.appDataBase.projectDataDAO().setColor(projectID!!, newWallpaper)
    }

    override suspend fun publish(): Boolean {
        return super.publish() && publishable
    }
}