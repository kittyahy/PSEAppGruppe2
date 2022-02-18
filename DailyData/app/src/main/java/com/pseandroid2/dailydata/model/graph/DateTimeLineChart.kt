package com.pseandroid2.dailydata.model.graph

import androidx.compose.ui.graphics.Color
import com.pseandroid2.dailydata.model.project.Project
import com.pseandroid2.dailydata.model.settings.Settings
import java.time.LocalDateTime
import java.time.ZoneOffset

class DateTimeLineChart(
    id: Int = -1,
    transformation: Project.DataTransformation<Map<LocalDateTime, Float>>,
    settings: Settings,
    path: String? = null
) : LineChart<LocalDateTime>(id, transformation, settings, path) {
    override fun xToFloat(maps: List<Map<LocalDateTime, Float>>): List<Map<Float, Float>> {
        val keys = mutableSetOf<LocalDateTime>()
        for (i in maps.indices) {
            keys.addAll(maps[i].keys)
        }
        val min = findMin(keys)

        val floatMaps = mutableListOf<Map<Float, Float>>()
        for (map in maps) {
            floatMaps.add(convertKeysToFloat(map, min))
        }
        return floatMaps
    }

    override fun getType() = GraphType.TIME_LINE_CHART

    private fun findMin(dates: Set<LocalDateTime>): LocalDateTime {
        var min = LocalDateTime.MAX
        for (date in dates) {
            if (date.isBefore(min)) {
                min = date
            }
        }
        return min
    }

    private fun convertKeysToFloat(
        map: Map<LocalDateTime, Float>,
        min: LocalDateTime,
    ): Map<Float, Float> {
        val floatMap = mutableMapOf<Float, Float>()
        val minLong = min.toEpochSecond(ZoneOffset.UTC)
        for (entry in map) {
            val floatKey = (entry.key.toEpochSecond(ZoneOffset.UTC) - minLong).toFloat()
            floatMap[floatKey] = entry.value
        }
        return floatMap
    }

    override fun applyTemplateSettings(template: GraphTemplate) {
        TODO("Not yet implemented")
    }
}