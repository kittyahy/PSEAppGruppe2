package com.pseandroid2.dailydata.repository.commandCenter.commands

import com.pseandroid2.dailydata.model.project.Project
import com.pseandroid2.dailydata.model.project.SimpleProjectBuilder
import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.repository.commandCenter.PublishQueue
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Button
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Column
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Graph
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Notification
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.adapters.projectParts.Table
import kotlinx.coroutines.flow.MutableSharedFlow

class CreateProject(
    commandByUser: String,
    private val name: String,
    private val description: String,
    private val wallpaper: Int,
    private val table: List<Column>,
    private val buttons: List<Button>,
    private val notification: List<Notification>,
    private val graphs: List<Graph>,
    private val projectIDReturn: MutableSharedFlow<Int>? = null
) //Todo secondary constructor with communicationClasses.project
    : ProjectCommand(
    commandByUser = commandByUser,
    isProjectAdmin = true
) {
    companion object {
        fun isPossible(project: com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Project): Boolean {
            return ProjectCommand.isPossible(project)
        }
    }

    override val publishable: Boolean = false

    override suspend fun execute(
        repositoryViewModelAPI: RepositoryViewModelAPI,
        publishQueue: PublishQueue
    ) {
        val pb = SimpleProjectBuilder()
        pb.setName(name)
        pb.setDescription(description)
        pb.addTable(Table.extractFrom(table, buttons))
        pb.setBackground(wallpaper)
        @Suppress("DEPRECATION")
        graphs.forEach { graph -> graph.addYourself(pb) }
        @Suppress("DEPRECATION")
        notification.forEach { notification -> notification.addYourself(pb) }

        val project: Project = pb.build()
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
        onlineProjectID = repositoryViewModelAPI.remoteDataSourceAPI.addProject()
        //Make Created Project Online Project
        repositoryViewModelAPI.appDataBase.projectDataDAO().setOnlineID(projectID!!, onlineProjectID!!)
        return super.publish(repositoryViewModelAPI, publishQueue)
    }
}