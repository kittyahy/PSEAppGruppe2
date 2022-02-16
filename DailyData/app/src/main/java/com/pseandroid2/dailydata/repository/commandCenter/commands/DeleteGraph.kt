package com.pseandroid2.dailydata.repository.commandCenter.commands

import com.pseandroid2.dailydata.model.graph.Graph
import com.pseandroid2.dailydata.model.project.Project
import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.ViewModelProject

class DeleteGraph(projectID: Int, val graph: Graph<*, *>, api: RepositoryViewModelAPI) :
    ProjectCommand(projectID = projectID, repositoryViewModelAPI = api) {
    companion object {
        fun isIllegal(project: ViewModelProject): Boolean {
            return ProjectCommand.isIllegal(project)
        }
    }

    override val publishable: Boolean = false

    override suspend fun execute() {
        @Suppress("Deprecation")
        repositoryViewModelAPI.appDataBase.graphCDManager().deleteGraph(projectID!!, graph.id)
        super.execute()
    }
}
