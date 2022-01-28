package com.pseandroid2.dailydata.repository.commandCenter.commands

import com.pseandroid2.dailydata.model.database.AppDataBase
import com.pseandroid2.dailydata.model.database.daos.ProjectCDManager
import com.pseandroid2.dailydata.model.project.Project
import com.pseandroid2.dailydata.model.project.ProjectBuilder
import com.pseandroid2.dailydata.model.project.SimpleProjectBuilder
import com.pseandroid2.dailydata.model.table.ArrayListLayout
import com.pseandroid2.dailydata.model.table.ArrayListTable
import com.pseandroid2.dailydata.model.table.Table
import com.pseandroid2.dailydata.model.table.TableLayout
import com.pseandroid2.dailydata.remoteDataSource.RemoteDataSourceAPI
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Button
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Column
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Graph
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Notification

class CreateProject (commandByUser: String,
                     private val projectCDManager: ProjectCDManager, //TODO Arne fragen, dass dieses feld auch über appDataBase zugänglich ist
                     private val name: String,
                     private val description: String,
                     private val wallpaper: Int,
                     private val table: List<Column>,
                     private val buttons: List<Button>,
                     private val notification: List<Notification>,
                     private val graphs: List<Graph>
)
    : ProjectCommand(
    commandByUser = commandByUser,
    isProjectAdmin = true) {
    override suspend fun execute(
        appDataBase: AppDataBase,
        remoteDataSourceAPI: RemoteDataSourceAPI
    ) {
        val pb = SimpleProjectBuilder()
        pb.setName(name)
        pb.setDescription(description)
        val tableLayout: TableLayout = ArrayListLayout()
        val table: Table = ArrayListTable(tableLayout)
        graphs.forEach { graph -> graph.addYourself(pb as ProjectBuilder<Project>) } //TODO rausfinden warum dieser Cast notwendig ist
        notification.forEach{ notification -> notification.addYourself(pb as ProjectBuilder<Project>)}

        val project: Project = pb.build()
        projectCDManager.insertProject(project)
        super.execute(appDataBase, remoteDataSourceAPI)
    }

    override suspend fun publish() {
    }
}