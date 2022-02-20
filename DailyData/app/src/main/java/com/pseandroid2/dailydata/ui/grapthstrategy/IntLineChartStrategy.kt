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