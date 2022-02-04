package com.pseandroid2.dailydata.model.database.entities

import com.pseandroid2.dailydata.model.graph.GraphType
import com.pseandroid2.dailydata.model.project.Project

/**
 * This class contains all data from a Graph, which the can be used outside the model.
 * It contains the id, the function, the column, the type of the graph and the path where the picture of the graph is saved.
 */
data class GraphData(
    val id: Int,
    val function: String,
    val cols: String,
    val type: GraphType,
    val path: String
)