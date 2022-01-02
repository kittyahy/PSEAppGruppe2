package com.pseandroid2.dailydata.model.database

import com.pseandroid2.dailydata.model.Graph
import java.util.SortedSet
import java.util.TreeSet

class GraphCDManager {

    //TODO make this a map with key projectId
    val existingIds: SortedSet<Int> = TreeSet<Int>()

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
        //TODO
        return 0
    }

}