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

package com.pseandroid2.dailydata.model.transformation

import com.google.gson.Gson
import com.pseandroid2.dailydata.util.getSerializableClassName
import kotlin.reflect.KClass

abstract class LineChartTransformation<T : Any>(
    private val identity: Identity<out Number>,
    typeString: String,
    private val xCol: Int
) :
    TransformationFunction<Map<T, Float>>(
        "$typeString$CHART_TYPE_LINE::${identity.toCompleteString()}%${xCol}"
    ) {
    companion object {
        const val CHART_TYPE_LINE = "LINECHART"
    }

    override fun execute(input: List<List<Any>>): List<Map<T, Float>> {
        val xValues = input[xCol]
        val yValues = mutableListOf<List<Any>>()
        for (i in input.indices) {
            if (i != xCol) {
                yValues.add(input[i])
            }
        }
        val floatList = identity.execute(yValues)
        val floatXValues = convertX(xValues)
        val maps = mutableListOf<Map<T, Float>>()
        for (list in floatList) {
            val map = mutableMapOf<T, Float>()
            for (i in list.indices) {

                map[floatXValues[i]] = list[i].toFloat()
            }
            maps.add(map)
        }
        //TODO Sort by X-Values if User inputs weird stuff
        return maps
    }

    abstract fun convertX(xValues: List<Any>): List<T>
}