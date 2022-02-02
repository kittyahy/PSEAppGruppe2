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
import com.pseandroid2.dailydata.repository.commandCenter.ExecuteQueue
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.runBlocking

class PieChart(
    override val id: Int,
    override val image: Bitmap,
    val color: List<Int>,
    val mapping: List<Column>,
    val showPercentages: Boolean
) : Graph() {
    override lateinit var executeQueue: ExecuteQueue
    override val typeName: String = "Pie Chart" //TODO Magic String

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

    fun addMappingColorIsPossible(): Flow<Boolean> {
        //Todo replace with valid proof
        val flow = MutableSharedFlow<Boolean>()
        runBlocking {
            flow.emit(true)
        }
        return flow
    }

    fun addMapping(color: Int, column: Column) { //Todo Robin fragen was die color hier bedeutet

    }

    fun addMappingIsPossible(): Flow<Boolean> {
        //Todo replace with valid proof
        val flow = MutableSharedFlow<Boolean>()
        runBlocking {
            flow.emit(true)
        }
        return flow
    }

    fun addMapping(column: Column) {
        addMapping(PieChartColors.ORANGE.value.toInt(), column = column)
    }

    fun showPercentagesIsPossible(): Flow<Boolean> {
        //Todo replace with valid proof
        val flow = MutableSharedFlow<Boolean>()
        runBlocking {
            flow.emit(true)
        }
        return flow
    }

    fun showPercentages(show: Boolean) {

    }
}

enum class PieChartColors(val value: Long, val representation: String) {
    ORANGE(0xFFF57C00, "Orange"),
    GREEN(0xFF388E3C, "Green"),
    BLUE(0xFF2196F3, "Blue")
}