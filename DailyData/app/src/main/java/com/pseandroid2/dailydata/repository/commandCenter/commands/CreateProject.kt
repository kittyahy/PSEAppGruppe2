package com.pseandroid2.dailydata.repository.commandCenter.commands

import com.pseandroid2.dailydata.model.project.Project
import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.repository.commandCenter.PublishQueue
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.ViewModelProject
import kotlinx.coroutines.flow.MutableSharedFlow

class CreateProject(
    private val project: Project,
    private val projectIDReturn: MutableSharedFlow<Int>? = null
) : ProjectCommand(
    currentUser = true
) {

    companion object {
        fun isPossible(viewModelProject: ViewModelProject): Boolean {
            return ProjectCommand.isPossible(viewModelProject)
        }
    }

    override val publishable: Boolean = false

    override suspend fun execute(
        repositoryViewModelAPI: RepositoryViewModelAPI,
        publishQueue: PublishQueue
    ) {
        val prod = repositoryViewModelAPI.appDataBase.projectCDManager().insertProject(project)
        projectID = prod.id
        projectIDReturn?.emit(prod.id)
        super.execute(repositoryViewModelAPI, publishQueue)
    }

    override suspend fun publish(
        repositoryViewModelAPI: RepositoryViewModelAPI,
        publishQueue: PublishQueue
    ): Boolean {
        //ReserveServerSlot
        onlineProjectID =
            repositoryViewModelAPI.remoteDataSourceAPI.createNewOnlineProject("") //TODO Add project details as JSON here

        //Make Created Project Online Project
        repositoryViewModelAPI.appDataBase.projectDataDAO()
            .setOnlineID(projectID!!, onlineProjectID!!)
        return super.publish(repositoryViewModelAPI, publishQueue)
    }
}