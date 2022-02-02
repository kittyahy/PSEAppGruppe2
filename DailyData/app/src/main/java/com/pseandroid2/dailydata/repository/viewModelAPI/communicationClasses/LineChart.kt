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

import android.content.Context
import android.graphics.Bitmap
import android.view.View
import com.pseandroid2.dailydata.model.database.AppDataBase
import com.pseandroid2.dailydata.model.graph.FloatLineChart
import com.pseandroid2.dailydata.model.graph.Generator
import com.pseandroid2.dailydata.model.graph.LineChart
import com.pseandroid2.dailydata.model.settings.MapSettings
import com.pseandroid2.dailydata.model.transformation.FloatSum
import com.pseandroid2.dailydata.model.transformation.PieChartTransformation
import com.pseandroid2.dailydata.repository.commandCenter.ExecuteQueue
import com.pseandroid2.dailydata.util.IOUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.runBlocking
import com.pseandroid2.dailydata.model.graph.Graph as ModelGraph

class LineChart(
    override val id: Int,
    override val image: Bitmap?,
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
        val sum = FloatSum(mappingInt)
        val trafo = PieChartTransformation(sum)
        val dataTrapo = com.pseandroid2.dailydata.model.project.Project.DataTransformation<Float>()
        val settings = MapSettings()
        settings[Generator.GRAPH_NAME_KEY] = id.toString()
        return LineChart(id, dataTrapo, settings)
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
