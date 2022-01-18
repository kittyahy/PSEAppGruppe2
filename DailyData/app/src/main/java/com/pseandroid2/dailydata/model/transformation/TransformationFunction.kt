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