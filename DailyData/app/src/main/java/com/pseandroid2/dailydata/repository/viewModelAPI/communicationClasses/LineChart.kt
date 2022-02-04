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
import com.pseandroid2.dailydata.model.graph.DateTimeLineChart
import com.pseandroid2.dailydata.model.graph.FloatLineChart
import com.pseandroid2.dailydata.model.graph.Generator
import com.pseandroid2.dailydata.model.graph.IntLineChart
import com.pseandroid2.dailydata.model.settings.MapSettings
import com.pseandroid2.dailydata.model.transformation.DateTimeLineChartTransformation
import com.pseandroid2.dailydata.model.transformation.FloatIdentity
import com.pseandroid2.dailydata.model.transformation.FloatLineChartTransformation
import com.pseandroid2.dailydata.model.transformation.IntLineChartTransformation
import com.pseandroid2.dailydata.repository.commandCenter.ExecuteQueue
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.DataType.FLOATING_POINT_NUMBER
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.DataType.TIME
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.DataType.WHOLE_NUMBER
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.runBlocking
import com.pseandroid2.dailydata.model.graph.Graph as ModelGraph

class LineChart(
    override val id: Int,
    //override val image: Bitmap?,
    val dotSize: DotSize,
    val dotColor: Int,
    val lineType: LineType,
    val mappingVertical: List<Column>
) : Graph() {
    override lateinit var executeQueue: ExecuteQueue
    override lateinit var project: Project
    override lateinit var appDataBase: AppDataBase
    override val typeName: String = "Line Chart" //TODO Magic String

    init {
        availableGraphs.add(typeName)
    }

    override fun deleteIsPossible(): Flow<Boolean> {
        TODO("Not yet implemented")
    }

    //@throws IllegalOperationException
    override suspend fun delete() {
        TODO("Not yet implemented")
    }

    override fun toDBEquivalent(): ModelGraph<*, *> {
        val mappingInt = ArrayList<Int>()
        for (col in mappingVertical) {
            mappingInt.add(col.id)
        }
        val identity = FloatIdentity()
        val settings = MapSettings()
        settings[Generator.GRAPH_NAME_KEY] = id.toString()

        @Suppress("Deprecation")
        val modelProject = project.toDBEquivalent()
        return when (mappingVertical[0].dataType) {
            FLOATING_POINT_NUMBER -> {
                val trafo = FloatLineChartTransformation(identity)
                val dataTrafo = modelProject.createDataTransformation(trafo, mappingInt)
                FloatLineChart(id, dataTrafo, settings)
            }
            WHOLE_NUMBER -> {
                val trafo = IntLineChartTransformation(identity)
                val dataTrafo = modelProject.createDataTransformation(trafo, mappingInt)
                IntLineChart(id, dataTrafo, settings)
            }
            TIME -> {
                val trafo = DateTimeLineChartTransformation(identity)
                val dataTrafo = modelProject.createDataTransformation(trafo, mappingInt)
                DateTimeLineChart(id, dataTrafo, settings)
            }
            else -> {
                throw IllegalArgumentException(
                    "Could not create Line Chart for X-Axis values of " +
                            "type ${mappingVertical[0].dataType}"
                )
            }
        }
    }

    fun addVerticalMappingIsPossible(): Flow<Boolean> {
        //Todo replace with valid proof
        val flow = MutableSharedFlow<Boolean>()
        runBlocking {
            flow.emit(true)
        }
        return flow
    }


    suspend fun addVerticalMapping(column: Column) {

    }

    fun deleteVerticalMappingIsPossible(): Flow<Boolean> {
        //Todo replace with valid proof
        val flow = MutableSharedFlow<Boolean>()
        runBlocking {
            flow.emit(true)
        }
        return flow
    }


    suspend fun deleteVerticalMapping(index: Int) {

    }

    fun changeDotSizeIsPossible(): Flow<Boolean> {
        //Todo replace with valid proof
        val flow = MutableSharedFlow<Boolean>()
        runBlocking {
            flow.emit(true)
        }
        return flow
    }


    suspend fun changeDotSize(dotSize: DotSize) {

    }

    fun changeDotColorIsPossible(): Flow<Boolean> {
        //Todo replace with valid proof
        val flow = MutableSharedFlow<Boolean>()
        runBlocking {
            flow.emit(true)
        }
        return flow
    }


    suspend fun changeDotColor(color: Int) {

    }

    fun changeLineTypeIsPossible(): Flow<Boolean> {
        //Todo replace with valid proof
        val flow = MutableSharedFlow<Boolean>()
        runBlocking {
            flow.emit(true)
        }
        return flow
    }


    suspend fun changeLineType(lineType: LineType) {


    }
}
