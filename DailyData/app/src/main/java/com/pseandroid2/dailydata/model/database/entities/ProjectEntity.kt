package com.pseandroid2.dailydata.model.database.entities

import com.pseandroid2.dailydata.model.User

data class ProjectEntity(
    val id: Int,
    val skeleton: ProjectSkeletonEntity,
    val admin: User,
    val onlineId: Long
) {
}