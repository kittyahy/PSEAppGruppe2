package com.pseandroid2.dailydata.model.database.entities

import androidx.room.Entity
import com.pseandroid2.dailydata.model.User
import java.time.LocalDateTime

@Entity(tableName = "row", primaryKeys = ["projectId", "createdOn", "createdBy"])
data class RowEntity(
    val projectId: Int,
    val createdOn: LocalDateTime,
    val createdBy: User,
    val values: String,
    val publishedOnServer: LocalDateTime
)