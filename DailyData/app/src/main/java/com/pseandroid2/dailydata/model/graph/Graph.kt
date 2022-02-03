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
import com.github.mikephil.charting.data.DataSet
import com.github.mikephil.charting.data.Entry
import com.pseandroid2.dailydata.model.project.Project
import com.pseandroid2.dailydata.model.settings.Settings
import com.pseandroid2.dailydata.model.users.User

interface Graph<T : DataSet<S>, S : Entry> {
    companion object {
        const val SET_LABEL_KEY = "SET LABEL"

        const val ENABLE = "ENABLED"
        const val DISABLE = "DISABLED"
    }

    var id: Int

    fun getDataSets(): List<T>

    fun getCustomizing(): Settings

    fun getImage(): Bitmap?

    fun getPath(): String?

    fun getType(): GraphType

    fun getCalculationFunction(): Project.DataTransformation<out Any>

}

interface GraphTemplate {
    val id: Int

    val name: String

    val desc: String

    val path: String
    val background: Int

    fun getWallpaper(): Bitmap?

    var customizing: Settings
    fun addSetting(key: String, value: String)

    val type: GraphType

    val creator: User

    val onlineId: Long

}

enum class GraphType {

    INT_LINE_CHART,
    FLOAT_LINE_CHART,
    TIME_LINE_CHART,
    PIE_CHART

}