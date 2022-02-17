package com.pseandroid2.dailydata.repository.commandCenter.commands

import com.pseandroid2.dailydata.model.project.Project
import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.repository.commandCenter.PublishQueue
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.ViewModelProject
import kotlinx.coroutines.flow.MutableSharedFlow

class CreateProject(
    private val project: Project,
    private val projectIDReturn: MutableSharedFlow<Int>? = null,
    api: RepositoryViewModelAPI
) : ProjectCommand(
    commandByUser = @Suppress("Deprecation") api.remoteDataSourceAPI.getUserID(),
    createdByAdmin = true,
    repositoryViewModelAPI = api
) {

    companion object {
        fun isIllegal(project: Project): Boolean {
            return ProjectCommand.isIllegal(project)
        }
    }

    override val publishable: Boolean = false

    override suspend fun execute() {
        @Suppress("Deprecation")
        val prod = repositoryViewModelAPI.appDataBase.projectCDManager().insertProject(project)
        projectID = prod.id
        projectIDReturn?.emit(prod.id)
        super.execute()
    }

    override suspend fun publish(): Boolean {
        //ReserveServerSlot
        @Suppress("Deprecation")
        onlineProjectID =
            repositoryViewModelAPI.remoteDataSourceAPI.createNewOnlineProject("") //TODO Add project details as JSON here

        //Make Created Project Online Project
        @Suppress("Deprecation")
        repositoryViewModelAPI.appDataBase.projectDataDAO()
            .setOnlineID(projectID!!, onlineProjectID!!)
        return super.publish()
    }
}