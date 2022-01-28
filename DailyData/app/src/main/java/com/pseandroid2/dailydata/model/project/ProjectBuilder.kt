package com.pseandroid2.dailydata.model.project

import android.graphics.Color
import com.pseandroid2.dailydata.model.Graph
import com.pseandroid2.dailydata.model.settings.Settings
import com.pseandroid2.dailydata.model.notifications.Notification
import com.pseandroid2.dailydata.model.table.Table
import com.pseandroid2.dailydata.model.users.User

interface ProjectBuilder<P : Project> {
    fun reset(id: Int): ProjectBuilder<P>

    fun setId(id: Int): ProjectBuilder<P>

    fun setOnlineId(id: Long): ProjectBuilder<P>

    fun setName(name: String): ProjectBuilder<P>

    fun setDescription(desc: String): ProjectBuilder<P>

    fun setPath(path: String): ProjectBuilder<P>
    fun setBackground(color: Color): ProjectBuilder<P>

    fun addGraphs(graphs: List<Graph>): ProjectBuilder<P>

    fun addSettings(settings: Settings): ProjectBuilder<P>

    fun addNotifications(notifications: List<Notification>): ProjectBuilder<P>

    fun addTable(table: Table): ProjectBuilder<P>

    fun setOnlineProperties(admin: User? = null, isOnline: Boolean): ProjectBuilder<P>

    fun addUsers(users: List<User>): ProjectBuilder<P>

    fun build(): P
}