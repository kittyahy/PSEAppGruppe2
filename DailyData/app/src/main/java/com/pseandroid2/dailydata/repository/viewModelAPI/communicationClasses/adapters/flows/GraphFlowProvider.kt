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
import com.pseandroid2.dailydata.model.transformation.TransformationFunction
import com.pseandroid2.dailydata.util.Consts.LOG_TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class GraphFlowProvider(private val project: Project, private val db: AppDataBase) :
    FlowProvider<List<Graph<*, *>>>() {

    override suspend fun initialize() = coroutineScope {
        launch(Dispatchers.IO) {
            db.graphDAO().getGraphDataForProject(project.id).collect { graphData ->
                val graphs = mutableListOf<Graph<*, *>>()
                for (data in graphData) {
                    val settings = db.settingsDAO().getSingleGraphSettings(project.id, data.id)
                    graphs.add(
                        when (data.type) {
                            GraphType.INT_LINE_CHART -> {
                                @Suppress("Unchecked_Cast")
                                IntLineChart(
                                    data.id,
                                    project.createDataTransformation(
                                        TransformationFunction.parse(data.function),
                                        Gson().fromJson(
                                            data.cols,
                                            List::class.java
                                        ) as List<Int>
                                    ) as Project.DataTransformation<Map<Int, Float>>,
                                    settings
                                )
                            }
                            GraphType.FLOAT_LINE_CHART -> {
                                @Suppress("Unchecked_Cast")
                                (FloatLineChart(
                                    data.id,
                                    project.createDataTransformation(
                                        TransformationFunction.parse(data.function),
                                        Gson().fromJson(
                                            data.cols,
                                            List::class.java
                                        ) as List<Int>
                                    ) as Project.DataTransformation<Map<Float, Float>>,
                                    settings
                                ))
                            }
                            GraphType.TIME_LINE_CHART -> {
                                @Suppress("Unchecked_Cast")
                                (DateTimeLineChart(
                                    data.id,
                                    project.createDataTransformation(
                                        TransformationFunction.parse(data.function),
                                        Gson().fromJson(
                                            data.cols,
                                            List::class.java
                                        ) as List<Int>
                                    ) as Project.DataTransformation<Map<LocalDateTime, Float>>,
                                    settings
                                ))
                            }
                            GraphType.PIE_CHART -> {
                                @Suppress("Unchecked_Cast")
                                PieChart(
                                    data.id,
                                    project.createDataTransformation(
                                        TransformationFunction.parse(data.function),
                                        Gson().fromJson(
                                            data.cols,
                                            List::class.java
                                        ) as List<Int>
                                    ) as Project.DataTransformation<Float>,
                                    settings
                                )
                            }
                        }
                    )
                }
                Log.d(LOG_TAG, "Emitting ${graphs.size} Graphs")
                mutableFlow.emit(graphs)
            }
        }
        Unit
    }
}