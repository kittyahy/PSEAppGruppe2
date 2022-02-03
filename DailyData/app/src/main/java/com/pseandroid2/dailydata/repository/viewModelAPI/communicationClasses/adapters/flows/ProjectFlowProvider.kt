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
import com.pseandroid2.dailydata.model.users.SimpleUser
import com.pseandroid2.dailydata.model.users.User
import com.pseandroid2.dailydata.util.Consts
import com.pseandroid2.dailydata.util.Consts.LOG_TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime

class ProjectFlowHandler(private val projId: Int, private val db: AppDataBase) {
    private val project = SimpleProjectBuilder().setId(projId).build()
    private val projectFlow = MutableSharedFlow<Project?>()

    suspend fun initialize() = coroutineScope {
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
                    projectFlow.emit(project)
                } else {
                    Log.d(LOG_TAG, "ProjectData was null")
                    projectFlow.emit(null)
                }
            }
        }
        //Observe changes to Project Graphs
        launch(Dispatchers.IO) {
            Log.i(LOG_TAG, "Start observing Graphs for Project with id $projId")
            db.graphDAO().getGraphDataForProject(projId).distinctUntilChanged()
                .collect { graphData ->
                    val graphs = mutableListOf<Graph<*, *>>()
                    for (data in graphData) {
                        val settings = db.settingsDAO().getSingleGraphSettings(projId, data.id)
                        //TODO this kinda should be in another method or class
                        graphs.add(
                            when (data.type) {
                                GraphType.INT_LINE_CHART -> {
                                    @Suppress("Unchecked_Cast")
                                    IntLineChart(
                                        data.id,
                                        data.dataTransformation as Project.DataTransformation<Map<Int, Float>>,
                                        settings
                                    )
                                }
                                GraphType.FLOAT_LINE_CHART -> {
                                    @Suppress("Unchecked_Cast")
                                    FloatLineChart(
                                        data.id,
                                        data.dataTransformation as Project.DataTransformation<Map<Float, Float>>,
                                        settings
                                    )
                                }
                                GraphType.TIME_LINE_CHART -> {
                                    @Suppress("Unchecked_Cast")
                                    DateTimeLineChart(
                                        data.id,
                                        data.dataTransformation as Project.DataTransformation<Map<LocalDateTime, Float>>,
                                        settings
                                    )
                                }
                                GraphType.PIE_CHART -> {
                                    @Suppress("Unchecked_Cast")
                                    PieChart(
                                        data.id,
                                        data.dataTransformation as Project.DataTransformation<Float>,
                                        settings
                                    )
                                }
                            }
                        )
                    }
                    project.graphs = graphs
                    projectFlow.emit(project)
                }
        }
        //Observe changes to Project Settings
        launch(Dispatchers.IO) {
            Log.i(LOG_TAG, "Start observing Settings for Project with id $projId.")
            db.settingsDAO().getProjectSettings(projId).distinctUntilChanged().collect { settings ->
                project.settings = settings
                projectFlow.emit(project)
            }
        }
        //Observe changes to Project Notifications
        launch(Dispatchers.IO) {
            Log.i(LOG_TAG, "Start observing Notifications for Project with id $projId.")
            db.notificationsDAO().getNotifications(projId).distinctUntilChanged()
                .collect { notifications ->
                    project.notifications = notifications.toMutableList()
                    projectFlow.emit(project)
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
                projectFlow.emit(project)
            }
        }
        //Observe changes to Projects online status
        launch(Dispatchers.IO) {
            Log.i(LOG_TAG, "Start observing the online status of Project with id $projId")
            db.projectDataDAO().isOnline(projId).distinctUntilChanged().collect {
                project.isOnline = it
                projectFlow.emit(project)
            }
        }
        launch(Dispatchers.IO) {
            Log.i(LOG_TAG, "Start observing Users of Project with id $projId")
            db.projectDataDAO().getUsersByIds(projId).distinctUntilChanged().collect { users ->
                val userList = mutableListOf<User>()
                for (user in users) {
                    userList.add(user.user)
                }
                project.users = userList
            }
        }
    }

    fun getProject() = projectFlow
}