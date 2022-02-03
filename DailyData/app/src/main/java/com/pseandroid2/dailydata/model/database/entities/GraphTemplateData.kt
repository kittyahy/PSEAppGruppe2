package com.pseandroid2.dailydata.model.database.entities

import com.pseandroid2.dailydata.model.graph.GraphType
import com.pseandroid2.dailydata.model.users.User

data class GraphTemplateData(
    val id: Int,
    val projectTemplateId: Int,
    val name: String,
    val description: String,
    val type: GraphType,
    val color: Int,
    val creator: User,
    val onlineId: Long
)