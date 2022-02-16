package com.pseandroid2.dailydata.repository.commandCenter.commands

import com.pseandroid2.dailydata.model.graph.Graph
import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.ViewModelProject

class AddGraph(val id: Int, val graph: Graph<*, *>, api: RepositoryViewModelAPI) :
    ProjectCommand(projectID = id, repositoryViewModelAPI = api) {
    companion object {
        fun isPossible(viewModelProject: ViewModelProject): Boolean {
            return ProjectCommand.isPossible(viewModelProject)
        }

        const val isAdminOperation: Boolean = false

        const val publishable: Boolean = false
    }

    override suspend fun execute() {
        @Suppress("Deprecation")
        repositoryViewModelAPI.appDataBase.graphCDManager().insertGraph(id, graph)
        super.execute()
    }

    override suspend fun publish(): Boolean {
        return super.publish() && publishable
    }
}
