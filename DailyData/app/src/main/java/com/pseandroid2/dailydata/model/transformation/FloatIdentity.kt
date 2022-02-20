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

import java.lang.IllegalArgumentException

class FloatIdentity : Identity<Float>("FLOAT") {
    override fun convertElements(input: List<List<Any>>): List<List<Float>> {
        val outer = mutableListOf<List<Float>>()
        for (list in input) {
            val inner = mutableListOf<Float>()
            for (element in list) {
                if (element is Number) {
                    inner.add(element.toFloat())
                } else {
                    throw IllegalArgumentException("Could not convert $element to Float")
                }
            }
            outer.add(inner)
        }
        return outer
    }
}