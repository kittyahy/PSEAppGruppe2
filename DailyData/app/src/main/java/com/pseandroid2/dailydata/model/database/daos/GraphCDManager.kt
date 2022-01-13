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

import com.pseandroid2.dailydata.model.Graph
import com.pseandroid2.dailydata.model.database.AppDataBase
import com.pseandroid2.dailydata.util.SortedIntListUtil
import java.util.SortedSet
import java.util.TreeSet

class GraphCDManager private constructor() {

    private val existingIds: MutableMap<Int, out SortedSet<Int>> = mutableMapOf<Int, TreeSet<Int>>()

    suspend fun insertGraph(projectId: Int, graph: Graph): Int {
        //TODO
        return 0
    }

    suspend fun deleteGraph(projectId: Int, id: Int) {
        //TODO
    }

    private suspend fun insertGraphEntity(projectId: Int, graph: Graph): Int {
        //TODO
        return 0
    }

    private fun getNextId(projectId: Int): Int {
        //Get the List of existing Ids for the project
        val list: List<Int> = ArrayList(existingIds[projectId] ?: sortedSetOf())

        //Get the next missing id
        return SortedIntListUtil.getFirstMissingInt(list)
    }

}