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

package com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.adapters.flows

import com.pseandroid2.dailydata.model.graph.GraphType
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Graph
import com.pseandroid2.dailydata.model.graph.Graph as ModelGraph
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow

@InternalCoroutinesApi
class GraphFlow(flow: Flow<List<ModelGraph<*,*>>>) : FlowAdapter<ModelGraph<*,*>, Graph>(flow) {
    override fun provide(i: com.pseandroid2.dailydata.model.graph.Graph<*,*>): Graph {
        return when (i.getType()) {
            GraphType.LINE_CHART -> {
                TODO("LineChart") //LineChart()
            }
            GraphType.PIE_CHART -> {
                TODO("PieChart") //PieChart()
            }
            else -> {
                throw IllegalArgumentException()
            }
        }
    }
}