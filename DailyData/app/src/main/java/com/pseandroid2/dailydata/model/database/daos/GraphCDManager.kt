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

package com.pseandroid2.dailydata.model.database.daos

import com.pseandroid2.dailydata.model.database.AppDataBase
import com.pseandroid2.dailydata.model.database.entities.GraphEntity
import com.pseandroid2.dailydata.model.database.entities.GraphTemplateEntity
import com.pseandroid2.dailydata.model.graph.Graph
import com.pseandroid2.dailydata.model.graph.GraphTemplate
import com.pseandroid2.dailydata.util.SortedIntListUtil
import java.util.TreeSet

class GraphCDManager(
    private val appDataBase: AppDataBase
) {
    companion object {
        const val TEMPLATE_SETTINGS_PROJ_ID = -1
    }

    private val graphDAO: GraphDAO = appDataBase.graphDAO()
    private val templateDAO: TemplateDAO = appDataBase.templateDAO()
    private val settingsDAO: SettingsDAO = appDataBase.settingsDAO()
    private val existingGraphIds: MutableMap<Int, TreeSet<Int>> =
        mutableMapOf<Int, TreeSet<Int>>()
    private val existingTemplateIds: TreeSet<Int> = sortedSetOf()

    suspend fun insertGraph(projectId: Int, graph: Graph<*, *>): Int {
        val newId = insertGraphEntity(projectId, graph)
        for (setting in graph.getCustomizing()) {
            settingsDAO.createGraphSetting(projectId, newId, setting.first, setting.second)
        }
        return newId
    }

    suspend fun deleteGraph(projectId: Int, id: Int) {
        settingsDAO.deleteAllGraphSettings(projectId, id)
    }

    suspend fun insertGraphTemplate(graphTemplate: GraphTemplate, projectTemplateId: Int): Int {
        val newId = insertGraphTemplateEntity(graphTemplate, projectTemplateId)
        for (setting in graphTemplate.customizing) {
            settingsDAO.createGraphSetting(
                TEMPLATE_SETTINGS_PROJ_ID,
                newId,
                setting.first,
                setting.second
            )
        }
        return newId
    }

    private suspend fun insertGraphEntity(projectId: Int, graph: Graph<*, *>): Int {
        val newId = getNextGraphId(projectId)
        @Suppress("Deprecation")
        graphDAO.insertGraph(
            GraphEntity(
                newId,
                projectId,
                graph.getCalculationFunction(),
                graph.getType(),
                graph.getPath() ?: ""
            )
        )
        return newId
    }

    private suspend fun insertGraphTemplateEntity(
        graphTemplate: GraphTemplate,
        projectTemplateId: Int
    ): Int {
        val newId = getNextTemplateId()
        @Suppress("Deprecation")
        templateDAO.insertGraphTemplate(
            GraphTemplateEntity(
                newId,
                projectTemplateId,
                graphTemplate.name,
                graphTemplate.desc,
                graphTemplate.type,
                graphTemplate.background,
                graphTemplate.creator,
                graphTemplate.onlineId
            )
        )
        return newId
    }

    private fun getNextGraphId(projectId: Int): Int {
        if (existingGraphIds[projectId] == null) {
            existingGraphIds[projectId] = TreeSet<Int>()
        }
        //Get the List of existing Ids for the project
        val list: List<Int> = ArrayList(existingGraphIds[projectId]!!)

        //Get the next missing id
        return SortedIntListUtil.getFirstMissingInt(list)
    }

    private fun getNextTemplateId(): Int {
        return SortedIntListUtil.getFirstMissingInt(ArrayList(existingTemplateIds))
    }

}