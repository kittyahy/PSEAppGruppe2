package com.pseandroid2.dailydata.repository.commandCenter.commands

import com.pseandroid2.dailydata.model.project.SimpleProjectBuilder
import com.pseandroid2.dailydata.model.users.SimpleUser
import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.repository.commandCenter.PublishQueue
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Button
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Column
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Graph
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Notification
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Project
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.adapters.projectParts.Table
import kotlinx.coroutines.flow.MutableSharedFlow
import com.pseandroid2.dailydata.model.project.Project as ModelProject

class CreateProject(
    private val name: String,
    private val description: String,
    private val wallpaper: Int,
    private val table: List<Column>,
    private val buttons: List<Button>,
    private val notification: List<Notification>,
    private val graphs: List<Graph>,
    private val projectIDReturn: MutableSharedFlow<Int>? = null
) : ProjectCommand(
    isProjectAdmin = true
) {
    constructor(
        project: Project,
        projectIDReturn: MutableSharedFlow<Int>? = null
    ) : this(
        project.title,
        project.description,
        project.wallpaper,
        project.table,
        project.buttons,
        project.notifications,
        project.graphs,
        projectIDReturn
    )

    companion object {
        fun isPossible(project: Project): Boolean {
            return ProjectCommand.isPossible(project)
        }

        const val issuerNeedsAdminRights: Boolean = false

        const val publishable: Boolean = false
    }

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

        val project: ModelProject = pb.build()
        project.admin = SimpleUser(
            repositoryViewModelAPI.remoteDataSourceAPI.getUserID(),
            repositoryViewModelAPI.remoteDataSourceAPI.getUserName()
        )
        val prod = repositoryViewModelAPI.appDataBase.projectCDManager().insertProject(project)
        projectID = prod.id
        projectIDReturn?.emit(prod.id)
        super.execute(repositoryViewModelAPI, publishQueue)
    }

    override fun publish(
        repositoryViewModelAPI: RepositoryViewModelAPI,
        publishQueue: PublishQueue
    ): Boolean {
        return super.publish(repositoryViewModelAPI, publishQueue) && publishable
    }
}