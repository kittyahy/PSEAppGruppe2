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
            TODO("Publish Queue not yet accessible")
            /*publishQueue.add(
                CreateProject(
                    viewModelProject.title,
                    viewModelProject.desc,
                    viewModelProject.wallpaper,
                    viewModelProject.table,
                    viewModelProject.buttons,
                    viewModelProject.notifications,
                    viewModelProject.graphs //Todo Problem wenn teilnehmende noch nicht die Verwendeten Graphtypen hätten
                )
            )*/
        }
    }

    override suspend fun publish(): Boolean {
        return super.publish() && publishable
    }

}
