package com.pseandroid2.dailydata.model.database.entities

import androidx.room.Entity

@Entity(tableName = "layout", primaryKeys = ["projectId", "id"])
data class ColumnEntity(
    val projectId: Int,
    val id: Int,
    val type: String,
    val name: String,
    val unit: String
)