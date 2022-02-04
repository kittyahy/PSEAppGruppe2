package com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.adapters.flows

import android.util.Log
import com.google.gson.Gson
import com.pseandroid2.dailydata.model.database.AppDataBase
import com.pseandroid2.dailydata.model.graph.DateTimeLineChart
import com.pseandroid2.dailydata.model.graph.FloatLineChart
import com.pseandroid2.dailydata.model.graph.Graph
import com.pseandroid2.dailydata.model.graph.GraphType
import com.pseandroid2.dailydata.model.graph.IntLineChart
import com.pseandroid2.dailydata.model.graph.PieChart
import com.pseandroid2.dailydata.model.project.Project
import com.pseandroid2.dailydata.model.project.SimpleProjectBuilder
import com.pseandroid2.dailydata.model.table.ArrayListLayout
import com.pseandroid2.dailydata.model.table.ArrayListTable
import com.pseandroid2.dailydata.model.users.User
import com.pseandroid2.dailydata.util.Consts.LOG_TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class ProjectFlowProvider(private val projId: Int, private val db: AppDataBase) :
    FlowProvider<Project?>() {
    private val project = SimpleProjectBuilder().setId(projId).build()


    override suspend fun initialize() = coroutineScope {
        //TODO Probably the different launches should be their own respective methods
        //Observe changes to ProjectData
        launch(Dispatchers.IO) {
            Log.i(LOG_TAG, "Start observing ProjectData for Project with id $projId.")
            db.projectDataDAO().getProjectData(projId).distinctUntilChanged().collect {
                if (it != null) {
                    Log.d(LOG_TAG, Gson().toJson(it))
                    project.name = it.name
                    project.desc = it.description
                    project.onlineId = it.onlineId
                    project.path = it.wallpaper
                    project.color = it.color
                    mutableFlow.emit(project)
                } else {
                    Log.d(LOG_TAG, "ProjectData was null")
                    mutableFlow.emit(null)
                }
            }
        }
        //Observe changes to Project Graphs
        launch(Dispatchers.IO) {
            Log.i(LOG_TAG, "Start observing Graphs for Project with id $projId")
            GraphFlowProvider(projId, db).provideFlow.distinctUntilChanged().collect { graphs ->
                project.graphs = graphs.toMutableList()
                mutableFlow.emit(project)
            }
        }
        //Observe changes to Project Settings
        launch(Dispatchers.IO) {
            Log.i(LOG_TAG, "Start observing Settings for Project with id $projId.")
            db.settingsDAO().getProjectSettings(projId).distinctUntilChanged().collect { settings ->
                project.settings = settings
                mutableFlow.emit(project)
            }
        }
        //Observe changes to Project Notifications
        launch(Dispatchers.IO) {
            Log.i(LOG_TAG, "Start observing Notifications for Project with id $projId.")
            db.notificationsDAO().getNotifications(projId).distinctUntilChanged()
                .collect { notifications ->
                    project.notifications = notifications.toMutableList()
                    mutableFlow.emit(project)
                }
        }
        //Observe changes to Project Table
        launch(Dispatchers.IO) {
            Log.i(LOG_TAG, "Start observing the Table of Project with id $projId")
            db.tableContentDAO().getRowsById(projId).distinctUntilChanged().collect { rows ->
                val layout = ArrayListLayout(db.projectDataDAO().getCurrentLayout(projId))
                val table = ArrayListTable(layout)
                for (row in rows) {
                    table.addRow(row)
                }
                project.table = table
                mutableFlow.emit(project)
            }
        }
        //Observe changes to Projects online status
        launch(Dispatchers.IO) {
            Log.i(LOG_TAG, "Start observing the online status of Project with id $projId")
            db.projectDataDAO().isOnline(projId).distinctUntilChanged().collect {
                project.isOnline = it
                mutableFlow.emit(project)
            }
        }
        //Observe changes to Users of the Project
        launch(Dispatchers.IO) {
            Log.i(LOG_TAG, "Start observing Users of Project with id $projId")
            db.projectDataDAO().getUsersByIds(projId).distinctUntilChanged().collect { users ->
                val userList = mutableListOf<User>()
                for (user in users) {
                    userList.add(user.user)
                }
                project.users = userList
                mutableFlow.emit(project)
            }
        }
        Unit
    }
}