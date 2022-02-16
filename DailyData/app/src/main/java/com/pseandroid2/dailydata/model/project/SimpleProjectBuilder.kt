/*

    DailyData is an android app to easily create diagrams from data one has collected
    Copyright (C) 2022  Antonia Heiming, Anton Kadelbach, Arne Kuchenbecker, Merlin Opp, Robin Amman

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.

*/

package com.pseandroid2.dailydata.model.project

import android.graphics.Color
import com.pseandroid2.dailydata.model.graph.Graph
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
            SimpleSkeleton(
                0,
                0,
                "",
                "",
                "",
                Color.BLACK,
                MapSettings(),
                mutableListOf()
            ),
            ArrayListTable(ArrayListLayout()),
            users = mutableListOf()
        )

    override fun reset(id: Int): ProjectBuilder<SimpleProject> {
        @Suppress("Deprecation")
        project = SimpleProject(
            SimpleSkeleton(
                id,
                0,
                "",
                "",
                "",
                Color.BLACK,
                MapSettings(),
                mutableListOf()
            ),
            ArrayListTable(ArrayListLayout()),
            users = mutableListOf()
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

    override fun setBackground(color: Int): ProjectBuilder<SimpleProject> {
        project.color = color
        return this
    }

    override fun addGraphs(graphs: List<Graph<*, *>>): ProjectBuilder<SimpleProject> {
        project.graphs.addAll(graphs)
        return this
    }

    override fun addSettings(settings: Settings): ProjectBuilder<SimpleProject> {
        project.addSettings(settings)
        return this
    }

    override fun addNotifications(notifications: List<Notification>): ProjectBuilder<SimpleProject> {
        project.addNotification(notifications)
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
