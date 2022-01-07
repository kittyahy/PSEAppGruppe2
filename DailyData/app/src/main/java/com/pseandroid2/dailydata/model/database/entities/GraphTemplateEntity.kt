package com.pseandroid2.dailydata.model.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pseandroid2.dailydata.model.GraphType
import com.pseandroid2.dailydata.model.User

@Entity(tableName = "graphTemplate")
data class GraphTemplateEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val description: String,
    val type: GraphType,
    val createdBy: User,
    val onlineId: String
)