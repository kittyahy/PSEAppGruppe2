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

import android.graphics.Bitmap
import com.pseandroid2.dailydata.model.graph.Graph
import com.pseandroid2.dailydata.model.notifications.Notification
import com.pseandroid2.dailydata.model.settings.MapSettings
import com.pseandroid2.dailydata.model.settings.Settings

class SimpleSkeleton(
    override var id: Int = -1,
    override var onlineId: Long = -1,
    override var name: String = "",
    override var desc: String = "",
    override var path: String = "",
    override var color: Int = -1,
    private val graphList: MutableList<Graph<*, *>> = mutableListOf(),
    private var skeletonSettings: Settings = MapSettings(),
    private val notificationList: MutableList<Notification> = mutableListOf()
) : ProjectSkeleton {
    override fun getWallpaper(): Bitmap {
        TODO()
    }

    override fun getGraphs() = graphList
    override fun addGraphs(graphs: Collection<Graph<*, *>>) {
        graphList.addAll(graphs)
    }

    override fun getProjectSettings() = skeletonSettings
    override fun addProjectSettings(settings: Settings) {
        for (setting in settings) {
            skeletonSettings[setting.first] = setting.second
        }
    }

    override fun getNotifications() = notificationList
    override fun addNotifications(notifications: Collection<Notification>) {
        notificationList.addAll(notifications)
    }
}