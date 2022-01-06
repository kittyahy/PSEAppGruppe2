package com.pseandroid2.dailydata.model.database.entities

import androidx.room.Entity
import java.time.LocalDateTime

@Entity(tableName = "missingSlot")
data class MissingSlotEntity(
    val projectId: Int,
    val from: LocalDateTime,
    val until: LocalDateTime
)