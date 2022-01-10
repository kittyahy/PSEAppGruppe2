package com.pseandroid2.dailydata.model.database.entities

import androidx.room.Entity
import com.pseandroid2.dailydata.model.User

@Entity(tableName = "user", primaryKeys = ["projectId", "user"])
data class ProjectUserMap(
    val projectId: Int,
    val user: User
)