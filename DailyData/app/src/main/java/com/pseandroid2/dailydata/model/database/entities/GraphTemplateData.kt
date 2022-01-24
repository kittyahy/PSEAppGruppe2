package com.pseandroid2.dailydata.model.database.entities

import com.pseandroid2.dailydata.model.GraphType
import com.pseandroid2.dailydata.model.users.User

data class GraphTemplateData(
    val id: Int,
    val name: String,
    val description: String,
    val type: GraphType,
    val creator: User
)