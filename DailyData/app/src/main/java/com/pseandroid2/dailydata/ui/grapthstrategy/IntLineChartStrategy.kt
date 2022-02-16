package com.pseandroid2.dailydata.ui.grapthstrategy

import com.pseandroid2.dailydata.model.graph.Generator
import com.pseandroid2.dailydata.model.graph.Graph
import com.pseandroid2.dailydata.model.graph.IntLineChart
import com.pseandroid2.dailydata.model.project.Project
import com.pseandroid2.dailydata.model.settings.MapSettings
import com.pseandroid2.dailydata.model.settings.Settings
import com.pseandroid2.dailydata.model.transformation.IntLineChartTransformation
import com.pseandroid2.dailydata.model.transformation.TransformationFunction
import java.lang.IllegalArgumentException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class IntLineChartStrategy : GraphCreationStrategy() {
    override fun createTransformation(xAxis: Int?): TransformationFunction<*> {
        if (xAxis == null) {
            throw IllegalArgumentException("Could not Create IntLineChart: xAxis parameter was null")
        }
        return IntLineChartTransformation(xCol = xAxis)
    }

    override fun createBaseSettings(name: String?): Settings {
        return MapSettings(
            mapOf(
                Pair(
                    Generator.GRAPH_NAME_KEY, name ?: "LineChart${
                        LocalDateTime.now().format(
                            DateTimeFormatter.ISO_DATE_TIME
                        )
                    }"
                )
            )
        )
    }

    override fun createGraph(
        transformation: Project.DataTransformation<out Any>,
        settings: Settings
    ): Graph<*, *> {
        @Suppress("Unchecked_Cast")
        return IntLineChart(
            transformation = transformation as Project.DataTransformation<Map<Int, Float>>,
            settings = settings
        )
    }
}