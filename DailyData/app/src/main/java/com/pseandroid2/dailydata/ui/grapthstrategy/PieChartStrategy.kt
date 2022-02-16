package com.pseandroid2.dailydata.ui.grapthstrategy

import com.pseandroid2.dailydata.model.graph.Generator
import com.pseandroid2.dailydata.model.graph.Graph
import com.pseandroid2.dailydata.model.graph.IntLineChart
import com.pseandroid2.dailydata.model.graph.PieChart
import com.pseandroid2.dailydata.model.project.Project
import com.pseandroid2.dailydata.model.settings.MapSettings
import com.pseandroid2.dailydata.model.settings.Settings
import com.pseandroid2.dailydata.model.transformation.FloatSum
import com.pseandroid2.dailydata.model.transformation.PieChartTransformation
import com.pseandroid2.dailydata.model.transformation.TransformationFunction
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class PieChartStrategy : GraphCreationStrategy() {
    override fun createTransformation(xAxis: Int?): TransformationFunction<*> {
        return PieChartTransformation(FloatSum())
    }

    override fun createBaseSettings(name: String?): Settings {
        return MapSettings(
            mapOf(
                Pair(
                    Generator.GRAPH_NAME_KEY, name ?: "PieChart${
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
        return PieChart(
            transformation = transformation as Project.DataTransformation<Float>,
            settings = settings
        )
    }
}