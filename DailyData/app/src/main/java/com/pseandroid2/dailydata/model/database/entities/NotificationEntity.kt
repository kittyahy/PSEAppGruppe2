package com.pseandroid2.dailydata.model.database.entities

import androidx.room.Entity
import com.pseandroid2.dailydata.model.notifications.Notification

@Entity(tableName = "notification", primaryKeys = ["projectId", "id"])
data class NotificationEntity(
    val projectId: Int,
    val id: Int,
    val params: String,
    val message: String
)