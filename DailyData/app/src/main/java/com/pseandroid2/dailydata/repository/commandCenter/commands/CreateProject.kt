package com.pseandroid2.dailydata.repository.commandCenter.commands

import com.pseandroid2.dailydata.model.database.AppDataBase
import com.pseandroid2.dailydata.model.project.Project
import com.pseandroid2.dailydata.model.project.SimpleProjectBuilder
import com.pseandroid2.dailydata.remoteDataSource.RemoteDataSourceAPI
import com.pseandroid2.dailydata.repository.commandCenter.PublishQueue
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Button
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Column
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Graph
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Notification
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.adapters.projectParts.Table
import kotlinx.coroutines.flow.MutableSharedFlow

class CreateProject(
    private val projectIDReturn: MutableSharedFlow<Int>,
    commandByUser: String,
    private val name: String,
    private val description: String,
    private val wallpaper: Int,
    private val table: List<Column>,
    private val buttons: List<Button>,
    private val notification: List<Notification>,
    private val graphs: List<Graph>
) //Todo secondary constructor with communicationClasses.project
    : ProjectCommand(
    commandByUser = commandByUser,
    isProjectAdmin = true
) {
    override suspend fun execute(
        appDataBase: AppDataBase,
        remoteDataSourceAPI: RemoteDataSourceAPI,
        publishQueue: PublishQueue
    ) {
        val pb = SimpleProjectBuilder()
        pb.setName(name)
        pb.setDescription(description)
        pb.addTable(Table.extractFrom(table, buttons))
        pb.setBackground(wallpaper)
        graphs.forEach { graph -> graph.addYourself(pb) }
        notification.forEach { notification -> notification.addYourself(pb) }

        val project: Project = pb.build()
        val prod = appDataBase.projectCDManager().insertProject(project)
        projectIDReturn.emit(prod.id)
        super.execute(appDataBase, remoteDataSourceAPI, publishQueue)
    }
}