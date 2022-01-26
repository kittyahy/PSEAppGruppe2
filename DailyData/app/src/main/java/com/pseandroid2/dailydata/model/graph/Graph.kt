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
import android.graphics.drawable.Drawable
import com.github.mikephil.charting.data.DataSet
import com.github.mikephil.charting.data.Entry
import com.pseandroid2.dailydata.model.Settings
import com.pseandroid2.dailydata.model.project.Project
import com.pseandroid2.dailydata.model.users.User

interface Graph<T : DataSet<S>, S : Entry> {
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

    fun getName(): String

    fun getDescription(): String

    fun getCustomizing(): Settings

    fun getType(): GraphType

    fun getCreator(): User

    fun getOnlineId(): Long

}

enum class GraphType {

    LINE_CHART,
    PIE_CHART

}