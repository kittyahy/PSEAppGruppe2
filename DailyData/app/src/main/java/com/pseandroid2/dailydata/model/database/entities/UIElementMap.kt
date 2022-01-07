package com.pseandroid2.dailydata.model.database.entities

import androidx.room.Entity

@Entity(tableName = "uiElement", primaryKeys = ["projectId", "id"])
data class UIElementMap(
    val projectId: Int,
    val id: Int,
    val columnId: Int,
    val type: String,
    val state: String
)