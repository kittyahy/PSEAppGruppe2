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
import androidx.compose.ui.graphics.Color
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet
import com.pseandroid2.dailydata.model.graph.Graph.Companion.SET_LABEL_KEY
import com.pseandroid2.dailydata.model.settings.Settings
import com.pseandroid2.dailydata.model.project.Project

abstract class LineChart<T : Any>(
    override var id: Int = -1,
    private val transformation: Project.DataTransformation<Map<T, Float>>,
    private val settings: Settings,
    private val path: String? = null
) : Graph<LineDataSet, Entry> {
    companion object {
        const val DOT_SIZE_KEY = "DOT SIZE"
        const val DOT_COLOR_KEY = "DOT COLOR"
        const val DOT_ENABLE_KEY = "DOT ENABLE"

        const val LINE_STYLE_KEY = "LINE STYLE"
        const val LINE_COLOR_KEY = "LINE COLOR"
        const val LINE_STRENGTH_KEY = "LINE STRENGTH"

        const val LINE_STYLE_NONE = "NONE"
        const val LINE_STYLE_SOLID = "SOLID"
    }

    override val primaryColors: MutableMap<Int, Color> = mutableMapOf()

    override fun getDataSets(): List<LineDataSet> {
        val dataSetMaps = xToFloat(transformation.recalculate())
        val dataSets = mutableListOf<LineDataSet>()
        for (i in dataSetMaps.indices) {
            val entries = ArrayList<Entry>()
            for (entry in dataSetMaps[i]) {
                entries.add(Entry(entry.key, entry.value))
            }
            dataSets.add(LineDataSet(entries, settings[SET_LABEL_KEY + i]))
        }
        return dataSets.toList()
    }

    override fun getCustomizing() = settings

    override fun getImage(): Bitmap? {
        return BitmapFactory.decodeFile(path)
    }

    override fun getPath() = path

    override fun getCalculationFunction() = transformation

    abstract fun xToFloat(maps: List<Map<T, Float>>): List<Map<Float, Float>>
}