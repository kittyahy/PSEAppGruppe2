package com.pseandroid2.dailydata.model.database.entities

import androidx.room.Entity

@Entity(tableName = "projectSetting", primaryKeys = ["projectId", "key"])
data class ProjectSettingEntity(
    val projectId: Int,
    val key: String,
    val value: String
)

@Entity(tableName = "graphSetting", primaryKeys = ["projectId", "graphId", "key"])
data class GraphSettingEntity(
    val projectId: Int,
    val graphId: Int,
    val key: String,
    val value: String
)