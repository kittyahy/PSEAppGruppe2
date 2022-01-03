package com.pseandroid2.dailydata.model.database.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pseandroid2.dailydata.model.User

@Entity(tableName = "project")
data class ProjectEntity(
    @PrimaryKey val id: Int,
    @Embedded val skeleton: ProjectSkeletonEntity,
    val admin: User,
    val onlineId: Long
) {
}