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

package com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses

import android.graphics.Bitmap
import com.pseandroid2.dailydata.model.database.AppDataBase
import com.pseandroid2.dailydata.model.graph.Generator
import com.pseandroid2.dailydata.model.graph.PieChart
import com.pseandroid2.dailydata.model.settings.MapSettings
import com.pseandroid2.dailydata.model.transformation.FloatSum
import com.pseandroid2.dailydata.model.transformation.PieChartTransformation
import com.pseandroid2.dailydata.repository.commandCenter.ExecuteQueue
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.runBlocking

class PieChart(
    override var id: Int = -1,
    override var image: Bitmap? = null,
    var color: List<Int> = ArrayList(),
    var mapping: MutableList<Column> = ArrayList(),
    var showPercentages: Boolean = false
) : Graph() {
    private val columnColors = mutableMapOf<Int, String>()
    override lateinit var executeQueue: ExecuteQueue
    override lateinit var viewModelProject: ViewModelProject
    override lateinit var appDataBase: AppDataBase
    override val typeName: String = "Pie Chart" //TODO Magic String

    override fun deleteIsPossible(): Flow<Boolean> {
        TODO("Not yet implemented")
    }

    //@throws IllegalOperationException
    override suspend fun delete() {
        TODO("Not yet implemented")
    }

    override fun toDBEquivalent(): com.pseandroid2.dailydata.model.graph.Graph<*, *> {
        val mappingInt = ArrayList<Int>()
        for (col in mapping) {
            mappingInt.add(col.id)
        }
        val sum = FloatSum()
        val trafo = PieChartTransformation(sum)
        val dataTrapo = viewModelProject.toDBEquivalent().createDataTransformation(trafo, mappingInt)
        val settings = MapSettings()
        for (pair in columnColors) {
            settings[PieChart.SLICE_COLOR_KEY + pair.key] = pair.value
        }
        settings[Generator.GRAPH_NAME_KEY] = id.toString()
        return PieChart(id, dataTrapo, settings)
    }

    fun addMappingColor(index : Int, color : Int) {
        //Todo(von Robin)
    }

    fun addMappingColorIsPossible(): Flow<Boolean> {
        //Todo replace with valid proof
        val flow = MutableSharedFlow<Boolean>(1)
        runBlocking {
            flow.emit(true)
        }
        return flow
    }

    suspend fun addMapping(
        color: Int,
        column: Column
    ) {
        val columnColor = PieChartColors.toRgb(color.toLong())
        columnColors[column.id] = columnColor
        mapping.add(column)
    }


    fun addMappingIsPossible(): Flow<Boolean> {
        //Todo replace with valid proof
        val flow = MutableSharedFlow<Boolean>(1)
        runBlocking {
            flow.emit(true)
        }
        return flow
    }

    suspend fun addMapping(column: Column) {
        addMapping(PieChartColors.ORANGE.value.toInt(), column = column)
    }

    fun showPercentagesIsPossible(): Flow<Boolean> {
        //Todo replace with valid proof
        val flow = MutableSharedFlow<Boolean>(1)
        runBlocking {
            flow.emit(true)
        }
        return flow
    }

    suspend fun showPercentages(show: Boolean) {

    }


    enum class PieChartColors(val value: Long, val representation: String, val rgb: String) {

        ORANGE(0xFFF57C00, "Orange", "#F57C00"),
        GREEN(0xFF388E3C, "Green", "#388E3C"),
        BLUE(0xFF2196F3, "Blue", "#2196F3");

        companion object {
            fun toRgb(value: Long): String {
                TODO("toRGB")
            }
        }

    }
}