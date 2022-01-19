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

abstract class TransformationFunction<I : Any, M : Any, O : Any>(
    private var composition: TransformationFunction<I, out Any, M>? = Identity<I, M>(),
    private var functionString: String
) {

    companion object {
        fun <I : Any> identity() = Identity<I, I>()

        fun <I : Any> sumC(cols: List<Int>) = SumColumns<I>(cols)

        fun <I : Any> sumS(cols: List<Int>) = SumSingles<I>(cols)

        fun fromString(functionString: String): TransformationFunction<out Any, out Any, out Any> {
            var function: String = functionString.substringBefore('(')
            var args: String = function.substringAfter('|', "")
            if (args != "") {
                args = args.substring(0, args.length - 2)
                function = function.substringBefore('|')
            }

            var inner: String = functionString.substringAfter('(')
            inner = inner.substring(0, inner.length - 2)

            val transform: TransformationFunction<out Any, out Any, out Any> = when (function) {
                "SUMS" -> {
                    val split = args.substring(1, args.length - 2).split(", ")
                    val cols = mutableListOf<Int>()
                    for (string in split) {
                        cols.add(string.toInt())
                    }
                    SumSingles<Any>(cols)
                }
                else -> {
                    Identity<Any, Any>()
                }
            }
            if (inner != "") {
                transform.setComposition
            }
            return transform
        }
    }

    fun execute(input: List<I>): List<O> {
        val intermediate = composition?.execute(input)
        @Suppress("Unchecked_Cast")
        return calculate(intermediate ?: input as List<M>)
    }

    open fun toCompleteString(): String {
        return "$functionString(${composition?.toCompleteString() ?: ""})"
    }

    fun setComposition(composeWith: TransformationFunction<I, out Any, M>) {
        composition = composeWith
    }

    abstract fun calculate(intermediate: List<M>): List<O>
}