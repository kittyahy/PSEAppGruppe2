/*

    DailyData is an android app to easily create diagrams from data one has collected
    Copyright (C) 2022  Antonia Heiming, Anton Kadelbach, Arne Kuchenbecker, Merlin Opp, Robin Amman

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.

*/

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