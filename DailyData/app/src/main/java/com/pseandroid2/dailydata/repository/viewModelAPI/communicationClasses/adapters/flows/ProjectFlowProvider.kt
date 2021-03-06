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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class ProjectFlowProvider(val projId: Int, private val db: AppDataBase) :
    FlowProvider<Project?>() {

    companion object {
        private const val DATA_INIT = "data"
        private const val TABLE_INIT = "table"
        private const val ONLINE_INIT = "online"
        private const val USER_INIT = "users"
    }

    private val project = SimpleProjectBuilder().setId(projId).build()
    private var graphProvider = GraphFlowProvider(project, db)

    private val initialized = mutableMapOf(
        Pair(DATA_INIT, false),
        Pair(TABLE_INIT, false),
        Pair(ONLINE_INIT, false),
        Pair(USER_INIT, false)
    )

    override suspend fun initialize() = coroutineScope {
        //TODO Probably the different launches should be their own respective methods
        //Observe changes to ProjectData
        launch(Dispatchers.IO) {
            Log.i(LOG_TAG, "Start observing ProjectData for Project with id $projId.")
            db.projectDataDAO().getProjectData(projId).distinctUntilChanged().collect {
                if (it != null) {
                    project.name = it.name
                    project.desc = it.description
                    project.onlineId = it.onlineId
                    project.path = it.wallpaper
                    project.color = it.color
                    project.admin = db.projectDataDAO().getAdminByIds(it.id).first()[0].user
                    Log.d(LOG_TAG, "Trying to emit ProjectData: ${Gson().toJson(initialized)}")
                    if (emitData()) {
                        mutableFlow.emit(project)
                        Log.v(LOG_TAG, "Emission of ProjectData: ${Gson().toJson(it)}")
                    }
                    initialized[DATA_INIT] = true
                } else {
                    Log.d(LOG_TAG, "ProjectData was null")
                    mutableFlow.emit(null)
                }
            }
        }
        //Observe changes to Project Graphs
        launch(Dispatchers.IO) {
            Log.i(LOG_TAG, "Start observing Graphs for Project with id $projId")
            launch {
                graphProvider.initialize()
            }
            graphProvider.provideFlow.distinctUntilChanged().collect { graphs ->
                project.graphs = graphs.toMutableList()
                Log.d(LOG_TAG, "Trying to emit Graphs: ${Gson().toJson(initialized)}")
                if (emitGraphs()) {
                    mutableFlow.emit(project)
                    Log.v(LOG_TAG, "Emission of Graphs: ${Gson().toJson(graphs)}")
                }
            }
        }
        //Observe changes to Project Settings
        launch(Dispatchers.IO) {
            Log.i(LOG_TAG, "Start observing Settings for Project with id $projId.")
            db.settingsDAO().getProjectSettings(projId).distinctUntilChanged().collect { settings ->
                project.settings = settings
                Log.d(LOG_TAG, "Trying to emit Settings: ${Gson().toJson(initialized)}")
                if (emitSettings()) {
                    mutableFlow.emit(project)
                    Log.v(LOG_TAG, "Emission of Settings: ${Gson().toJson(settings)}")
                }
            }
        }
        //Observe changes to Project Notifications
        launch(Dispatchers.IO) {
            Log.i(LOG_TAG, "Start observing Notifications for Project with id $projId.")
            db.notificationsDAO().getNotifications(projId).distinctUntilChanged()
                .collect { notifications ->
                    project.notifications = notifications.toMutableList()
                    Log.d(LOG_TAG, "Trying to emit Notifications: ${Gson().toJson(initialized)}")
                    if (emitNotifs()) {
                        mutableFlow.emit(project)
                        Log.v(LOG_TAG, "Emission of Notifications: ${Gson().toJson(notifications)}")
                    }
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
                Log.d(LOG_TAG, "Trying to emit Table: ${Gson().toJson(initialized)}")
                if (emitTable()) {
                    graphProvider = GraphFlowProvider(project, db)
                    mutableFlow.emit(project)
                    Log.v(LOG_TAG, "Emission of Rows: ${Gson().toJson(rows)}")
                }
                initialized[TABLE_INIT] = true
            }
        }
        //Observe changes to Projects online status
        launch(Dispatchers.IO) {
            Log.i(LOG_TAG, "Start observing the online status of Project with id $projId")
            db.projectDataDAO().isOnline(projId).distinctUntilChanged().collect {
                project.isOnline = it
                Log.d(LOG_TAG, "Trying to emit online status: ${Gson().toJson(initialized)}")
                if (emitOnline()) {
                    mutableFlow.emit(project)
                    Log.v(LOG_TAG, "Emission of Online Status: $it")
                }
                initialized[ONLINE_INIT] = true
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
                Log.d(LOG_TAG, "Trying to emit Users: ${Gson().toJson(initialized)}")
                if (emitUsers()) {
                    mutableFlow.emit(project)
                    Log.v(LOG_TAG, "Emission of Users: ${Gson().toJson(users)}")
                }
                initialized[USER_INIT] = true
            }
        }
        Unit
    }

    private fun emitData() = initialized[ONLINE_INIT] ?: false
            && initialized[TABLE_INIT] ?: false
            && initialized[USER_INIT] ?: false

    private fun emitGraphs() = initialized[DATA_INIT] ?: false
            && initialized[ONLINE_INIT] ?: false
            && initialized[TABLE_INIT] ?: false
            && initialized[USER_INIT] ?: false

    private fun emitSettings() = initialized[DATA_INIT] ?: false
            && initialized[ONLINE_INIT] ?: false
            && initialized[TABLE_INIT] ?: false
            && initialized[USER_INIT] ?: false

    private fun emitNotifs() = initialized[DATA_INIT] ?: false
            && initialized[ONLINE_INIT] ?: false
            && initialized[TABLE_INIT] ?: false
            && initialized[USER_INIT] ?: false

    private fun emitTable() = initialized[DATA_INIT] ?: false
            && initialized[ONLINE_INIT] ?: false
            && initialized[USER_INIT] ?: false

    private fun emitOnline() = initialized[DATA_INIT] ?: false
            && initialized[TABLE_INIT] ?: false
            && initialized[USER_INIT] ?: false

    private fun emitUsers() = initialized[DATA_INIT] ?: false
            && initialized[TABLE_INIT] ?: false
            && initialized[ONLINE_INIT] ?: false
}