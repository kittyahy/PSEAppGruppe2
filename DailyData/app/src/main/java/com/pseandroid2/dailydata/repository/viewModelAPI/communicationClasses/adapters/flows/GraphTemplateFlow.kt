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

import com.pseandroid2.dailydata.model.database.AppDataBase
import com.pseandroid2.dailydata.repository.commandCenter.ExecuteQueue
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.GraphTemplate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class GraphTemplateFlow(
    private val db: AppDataBase,
    private val eq: ExecuteQueue,
    private val provider: GraphTemplateFlowProvider
) {
    fun getTemplates(): Flow<List<GraphTemplate>> {
        return provider.provideFlow.distinctUntilChanged().map { templates ->
            val list = mutableListOf<GraphTemplate>()
            for (template in templates) {
                val addTemplate = GraphTemplate(
                    template.name,
                    template.getWallpaper(),
                    template.id,
                    template.desc,
                    template.background,
                    template.customizing,
                    template.type
                )
                //TODO figure out if GraphTemplates should have an executeQueue?
                //addTemplate.executeQueue = eq
                list.add(addTemplate)
            }
            list.toList()
        }
    }
}
