package com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.adapters.flows

import com.pseandroid2.dailydata.model.database.AppDataBase
import com.pseandroid2.dailydata.model.graph.DateTimeLineChart
import com.pseandroid2.dailydata.model.graph.FloatLineChart
import com.pseandroid2.dailydata.model.graph.Graph
import com.pseandroid2.dailydata.model.graph.GraphType
import com.pseandroid2.dailydata.model.graph.IntLineChart
import com.pseandroid2.dailydata.model.graph.PieChart
import com.pseandroid2.dailydata.model.project.Project
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class GraphFlowProvider(private val projectId: Int, private val db: AppDataBase) :
    FlowProvider<List<Graph<*, *>>>() {

    override suspend fun initialize() = coroutineScope {
        launch(Dispatchers.IO) {
            db.graphDAO().getGraphDataForProject(projectId).collect { graphData ->
                val graphs = mutableListOf<Graph<*, *>>()
                for (data in graphData) {
                    val settings = db.settingsDAO().getSingleGraphSettings(projectId, data.id)
                    graphs.add(
                        when (data.type) {
                            GraphType.INT_LINE_CHART -> {
                                @Suppress("Unchecked_Cast")
                                (IntLineChart(
                                    data.id,
                                    data.dataTransformation as Project.DataTransformation<Map<Int, Float>>,
                                    settings
                                ))
                            }
                            GraphType.FLOAT_LINE_CHART -> {
                                @Suppress("Unchecked_Cast")
                                (FloatLineChart(
                                    data.id,
                                    data.dataTransformation as Project.DataTransformation<Map<Float, Float>>,
                                    settings
                                ))
                            }
                            GraphType.TIME_LINE_CHART -> {
                                @Suppress("Unchecked_Cast")
                                (DateTimeLineChart(
                                    data.id,
                                    data.dataTransformation as Project.DataTransformation<Map<LocalDateTime, Float>>,
                                    settings
                                ))
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
                mutableFlow.emit(graphs)
            }
        }
        Unit
    }
}