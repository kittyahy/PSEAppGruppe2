package com.pseandroid2.dailydata.model.database.daos

import com.pseandroid2.dailydata.model.Graph
import com.pseandroid2.dailydata.util.SortedIntListUtil
import java.util.SortedSet
import java.util.TreeSet

class GraphCDManager private constructor() {

    //GraphCDManager is a Singleton
    companion object {
        private var instance: GraphCDManager? = null

        fun getInstance(): GraphCDManager {
            return instance ?: GraphCDManager()
        }
    }

    private val existingIds: MutableMap<Int, out SortedSet<Int>> = mutableMapOf<Int, TreeSet<Int>>()

    public fun insertGraph(projectId: Int, graph: Graph): Int {
        //TODO
        return 0
    }

    public fun deleteGraph(projectId: Int, id: Int) {
        //TODO
    }

    private fun insertGraphEntity(projectId: Int, graph: Graph): Int {
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