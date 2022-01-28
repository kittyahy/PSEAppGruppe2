package com.pseandroid2.dailydata.model.project

import android.graphics.Color
import com.pseandroid2.dailydata.model.Graph
import com.pseandroid2.dailydata.model.settings.MapSettings
import com.pseandroid2.dailydata.model.settings.Settings
import com.pseandroid2.dailydata.model.notifications.Notification
import com.pseandroid2.dailydata.model.table.ArrayListLayout
import com.pseandroid2.dailydata.model.table.ArrayListTable
import com.pseandroid2.dailydata.model.table.Table
import com.pseandroid2.dailydata.model.users.NullUser
import com.pseandroid2.dailydata.model.users.User
import java.lang.IllegalArgumentException

class SimpleProjectBuilder : ProjectBuilder<SimpleProject> {
    @Suppress("Deprecation")
    private var project =
        SimpleProject(
            SimpleSkeleton(0, 0, "", "", "", mutableListOf(), MapSettings(), mutableListOf()),
            ArrayListTable(ArrayListLayout()),
            userList = mutableListOf()
        )

    override fun reset(id: Int): ProjectBuilder<SimpleProject> {
        @Suppress("Deprecation")
        project = SimpleProject(
            SimpleSkeleton(id, 0, "", "", "", mutableListOf(), MapSettings(), mutableListOf()),
            ArrayListTable(ArrayListLayout()),
            userList = mutableListOf()
        )
        return this
    }

    override fun setId(id: Int): ProjectBuilder<SimpleProject> {
        project.id = id
        return this
    }

    override fun setOnlineId(id: Long): ProjectBuilder<SimpleProject> {
        project.onlineId = id
        return this
    }

    override fun setName(name: String): ProjectBuilder<SimpleProject> {
        project.name = name
        return this
    }

    override fun setDescription(desc: String): ProjectBuilder<SimpleProject> {
        project.desc = desc
        return this
    }

    override fun setPath(path: String): ProjectBuilder<SimpleProject> {
        project.path = path
        return this
    }

    override fun setBackground(color: Color): ProjectBuilder<SimpleProject> {
        project.color = color
        return this
    }

    override fun addGraphs(graphs: List<Graph>): ProjectBuilder<SimpleProject> {
        project.addGraphs(graphs)
        return this
    }

    override fun addSettings(settings: Settings): ProjectBuilder<SimpleProject> {
        project.addSettings(settings)
        return this
    }

    override fun addNotifications(notifications: List<Notification>): ProjectBuilder<SimpleProject> {
        project.addNotifications(notifications)
        return this
    }

    override fun addTable(table: Table): ProjectBuilder<SimpleProject> {
        project.table = table
        return this
    }

    override fun setOnlineProperties(
        admin: User?,
        isOnline: Boolean
    ): ProjectBuilder<SimpleProject> {
        if (admin != null) {
            project.isOnline = isOnline
            project.admin = admin
        } else {
            if (isOnline) {
                throw IllegalArgumentException("Online Projects must have an Admin!")
            }
            project.admin = NullUser()
        }
        return this
    }

    override fun addUsers(users: List<User>): ProjectBuilder<SimpleProject> {
        project.addUsers(users)
        return this
    }

    override fun build(): SimpleProject {
        val ret = project
        reset(0)
        return ret
    }
}
