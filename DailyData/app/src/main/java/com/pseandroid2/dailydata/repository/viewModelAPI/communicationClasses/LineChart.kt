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
import com.pseandroid2.dailydata.repository.commandCenter.ExecuteQueue
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.runBlocking

class LineChart(
    override var id: Int = -1,
    override var image: Bitmap? = null,
    var dotSize: DotSize = DotSize.MEDIUM,
    var dotColor: Int = 0,
    var lineType: LineType = LineType.CONTINUOUS,
    var mappingVertical: List<Column> = ArrayList()
) : Graph() {
    override lateinit var executeQueue: ExecuteQueue
    override lateinit var persistentProject: PersistentProject
    override lateinit var appDataBase: AppDataBase
    override val typeName: String = "Line Chart" //TODO Magic String

    override fun deleteIsPossible(): Flow<Boolean> {
        TODO("Not yet implemented")
    }

    //@throws IllegalOperationException
    override suspend fun delete() {
        TODO("Not yet implemented")
    }

    fun addVerticalMappingIsPossible(): Flow<Boolean> {
        //Todo replace with valid proof
        val flow = MutableSharedFlow<Boolean>(1)
        runBlocking {
            flow.emit(true)
        }
        return flow
    }


    suspend fun addVerticalMapping(column: Column) {

    }

    fun deleteVerticalMappingIsPossible(): Flow<Boolean> {
        //Todo replace with valid proof
        val flow = MutableSharedFlow<Boolean>(1)
        runBlocking {
            flow.emit(true)
        }
        return flow
    }


    suspend fun deleteVerticalMapping(index: Int) {

    }

    fun changeDotSizeIsPossible(): Flow<Boolean> {
        //Todo replace with valid proof
        val flow = MutableSharedFlow<Boolean>(1)
        runBlocking {
            flow.emit(true)
        }
        return flow
    }


    suspend fun changeDotSize(dotSize: DotSize) {

    }

    fun changeDotColorIsPossible(): Flow<Boolean> {
        //Todo replace with valid proof
        val flow = MutableSharedFlow<Boolean>(1)
        runBlocking {
            flow.emit(true)
        }
        return flow
    }


    suspend fun changeDotColor(color: Int) {

    }

    fun changeLineTypeIsPossible(): Flow<Boolean> {
        //Todo replace with valid proof
        val flow = MutableSharedFlow<Boolean>(1)
        runBlocking {
            flow.emit(true)
        }
        return flow
    }


    suspend fun changeLineType(lineType: LineType) {


    }
}
