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

package com.pseandroid2.dailydata.model.graph

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.pseandroid2.dailydata.model.database.entities.GraphData
import com.pseandroid2.dailydata.model.database.entities.GraphTemplateData
import com.pseandroid2.dailydata.model.settings.MapSettings
import com.pseandroid2.dailydata.model.settings.Settings
import com.pseandroid2.dailydata.model.users.NullUser
import com.pseandroid2.dailydata.model.users.User

class SimpleGraphTemplate(
    override val id: Int = -1,
    override val name: String = "",
    override val desc: String = "",
    override val type: GraphType,
    override val creator: User = NullUser(),
    override val onlineId: Long = -1,
    override val path: String = "",
    override val background: Int = -1
) : GraphTemplate {
    constructor(data: GraphTemplateData) : this(
        data.id,
        data.name,
        data.description,
        data.type,
        data.creator,
        data.onlineId,
        background = data.color
    )

    override var customizing: Settings = MapSettings(mapOf())

    override fun addSetting(key: String, value: String) {
        customizing[key] = value
    }

    override fun getWallpaper(): Bitmap? {
        return BitmapFactory.decodeFile(path)
    }
}