package com.pseandroid2.dailydata.model.graph

import com.pseandroid2.dailydata.model.project.Project
import com.pseandroid2.dailydata.model.settings.Settings

class FloatLineChart(
    id: Int = -1,
    transformation: Project.DataTransformation<Map<Float, Float>>,
    settings: Settings,
    path: String? = null
) : LineChart<Float>(id, transformation, settings, path) {
    override fun xToFloat(maps: List<Map<Float, Float>>): List<Map<Float, Float>> {
        return maps
    }

    override fun getType() = GraphType.FLOAT_LINE_CHART
}