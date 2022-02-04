package com.pseandroid2.dailydata.model.database.entities

import com.pseandroid2.dailydata.model.graph.GraphType
import com.pseandroid2.dailydata.model.project.Project

data class GraphData(
    val id: Int,
    val function: String,
    val cols: String,
    val type: GraphType,
    val path: String
)