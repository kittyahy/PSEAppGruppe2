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

import kotlin.reflect.KClass

abstract class Sum<N : Number>(private val cols: List<Int>) :
    TransformationFunction<N>(functionString = "$SUM_ID|col=$cols") {
    companion object {
        const val TYPE_INT = "INT"
        const val TYPE_FLOAT = "FLOAT"
    }

    override fun execute(input: List<List<Any>>): List<N> {
        val result = mutableListOf<N>()
        for (i in cols) {
            if (i < input.size) {
                result.add(unsafeSum(input[i]))
            }
        }
        return result
    }

    abstract fun unsafeSum(list: List<Any>): N
}