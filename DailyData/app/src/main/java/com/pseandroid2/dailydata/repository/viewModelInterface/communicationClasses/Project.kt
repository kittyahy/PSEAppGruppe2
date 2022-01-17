package com.pseandroid2.dailydata.repository.viewModelInterface.communicationClasses

import android.graphics.drawable.Drawable

data class Project(
    override val id: Long,
    val isOnlineProject: Boolean,
    val isAdmin: Boolean,
    val title: String,
    val description: String,
    val wallpaper: Drawable,
    val table: List<Column>,
    val data: List<Row>,
    val buttons: List<Button>,
    val notifications: List<Notification>,
    val graphs: List<Graph>,
    val members: List<Member>
): Identifiable