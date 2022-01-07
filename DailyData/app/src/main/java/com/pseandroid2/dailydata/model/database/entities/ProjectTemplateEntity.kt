package com.pseandroid2.dailydata.model.database.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pseandroid2.dailydata.model.ProjectSkeleton
import com.pseandroid2.dailydata.model.User

@Entity(tableName = "projectTemplate")
data class ProjectTemplateEntity(
    @PrimaryKey val id: Int,
    @Embedded val skeleton: ProjectSkeletonEntity,
    val createdBy: User,
    val onlineId: String
)