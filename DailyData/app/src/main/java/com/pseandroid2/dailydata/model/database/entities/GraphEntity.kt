package com.pseandroid2.dailydata.model.database.entities

import androidx.room.Entity
import com.pseandroid2.dailydata.model.GraphType
import com.pseandroid2.dailydata.model.Project

@Entity(tableName = "graph", primaryKeys = ["id", "projectId"])
data class GraphEntity(
    val id: Int,
    val projectId: Int,
    val dataTransformation: Project.DataTransformation<Any>,
    val type: GraphType,
    val path: String
)