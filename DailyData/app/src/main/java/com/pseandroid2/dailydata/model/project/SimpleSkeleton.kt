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
    private val onlineId: Long = -1,
    private val name: String = "",
    private val description: String = "",
    private val wallpaperPath: String = "",
    private val graphs: List<Graph<*, *>> = listOf(),
    private val settings: Settings = MapSettings(),
    private val notifications: List<Notification> = listOf()
) : ProjectSkeleton {

    override fun getOnlineId() = onlineId

    override fun getName() = name

    override fun getDescription() = description

    override fun getWallpaper(): Bitmap {
        TODO("Figure out how to obtain pictures from disk")
    }

    override fun getWallpaperPath() = wallpaperPath

    override fun getGraphs() = graphs

    override fun getProjectSettings() = settings

    override fun getNotifications() = notifications
}