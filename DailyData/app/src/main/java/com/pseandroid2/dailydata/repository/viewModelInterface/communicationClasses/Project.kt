package com.pseandroid2.dailydata.repository.viewModelInterface.communicationClasses

import android.graphics.drawable.Drawable

class Project(
    override val id: Long = 0,
    val isOnlineProject: Boolean = false,
    val isAdmin: Boolean = false,
    val title: String = "title missing",
    val description: String= "description missing",
    val wallpaper: String = "wallpaper missing",
    val table: List<Column> = ArrayList<Column>(),
    val data: List<Row> = ArrayList<Row>(),
    val buttons: List<Button> = ArrayList<Button>(),
    val notifications: List<Notification> = ArrayList<Notification>(),
    val graphs: List<Graph> = ArrayList<Graph>(),
    val members: List<Member> = ArrayList<Member>()
): Identifiable