package com.pseandroid2.dailydata.model.graph

import com.pseandroid2.dailydata.model.project.Project
import com.pseandroid2.dailydata.model.settings.Settings

class IntLineChart(
    id: Int = -1,
    transformation: Project.DataTransformation<Map<Int, Float>>,
    settings: Settings,
    path: String? = null
) : LineChart<Int>(id, transformation, settings, path) {
    override fun xToFloat(maps: List<Map<Int, Float>>): List<Map<Float, Float>> {
        val list = mutableListOf<Map<Float, Float>>()
        for (map in maps) {
            val floatMap = mutableMapOf<Float, Float>()
            for (entry in map) {
                floatMap[entry.key.toFloat()] = entry.value
            }
            list.add(floatMap)
        }
        return list
    }

    override fun getType() = GraphType.INT_LINE_CHART

    override fun applyTemplateSettings(template: GraphTemplate) {
        TODO("Not yet implemented")
    }
}