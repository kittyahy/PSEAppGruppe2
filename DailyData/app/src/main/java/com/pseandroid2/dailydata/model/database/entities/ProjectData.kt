package com.pseandroid2.dailydata.model.database.entities

data class ProjectData(
    val id: Int,
    val name: String,
    val description: String,
    val onlineId: Long,
    val wallpaper: String
) {
}