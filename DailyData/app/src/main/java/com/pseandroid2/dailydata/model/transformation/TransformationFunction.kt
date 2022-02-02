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

abstract class TransformationFunction<O : Any> protected constructor(
    protected open var functionString: String
) {

    companion object {
        const val SUM_ID = "SUM"
        const val IDENTITY_ID = "ID"

        fun parse(functionString: String): TransformationFunction<out Any> {
            var tableType = functionString.substringBefore("::")
            var function: String = functionString.substringAfter("::").substringBefore('|')
            var args: String = functionString.substringAfter("::").substringAfter('|', "")

            //No need to change stuff if there are no arguments
            if (args != "") {
                args = args.substring(0, args.length)
                function = function.substringBefore('|')
            }

            val transform: TransformationFunction<out Any> = when (function) {
                SUM_ID -> {
                    val split = args.split(";")
                    val argCols = split[0].substringAfter('=', "")
                    val colStrings = argCols.substring(1, argCols.length - 1).split(", ")
                    val cols = mutableListOf<Int>()
                    for (string in colStrings) {
                        cols.add(string.toInt())
                    }
                    when (split[1].substringAfter('=', "")) {
                        Sum.TYPE_INT -> IntSum(cols)
                        else -> throw IllegalArgumentException("No such Sum function: ${split[1]}")
                    }
                }
                IDENTITY_ID -> {
                    if (args != "") {
                        throw IllegalArgumentException(
                            "No Constructor for Identity function found with arguments $args"
                        )
                    } else {
                        FloatIdentity()
                    }
                }
                else -> throw IllegalArgumentException("No such function: $function")
            }
            return transform
        }
    }

    abstract fun execute(input: List<List<Any>>): List<O>

    fun toCompleteString(): String {
        return functionString
    }
}