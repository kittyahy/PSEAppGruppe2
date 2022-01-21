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

package com.pseandroid2.dailydata.model

import com.pseandroid2.dailydata.model.transformation.Sum
import com.pseandroid2.dailydata.model.transformation.TransformationFunction
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test

class DataTransformationTester {
    private lateinit var transformChaining: SumSingles<List<Int>>
    private lateinit var transformSimple: Sum<List<Int>>
    private val testSet1 = listOf(listOf(2, 3))
    private val testSet2 = listOf(
        listOf(2, 3, -1), listOf(9, 21, -20), listOf(234, 92, -10000), listOf(3)
    )

    @Before
    fun setup() {
        transformChaining = TransformationFunction.sumS<List<Int>>(listOf<Int>(0, 2))
        transformChaining.setComposition(
            TransformationFunction.sumC<List<Int>>(listOf(0, 1, 3))
        )
        transformSimple = TransformationFunction.sumC<List<Int>>(listOf<Int>(0))
    }

    @Test
    fun testSimpleTransform() {
        val result1 = transformSimple.execute(testSet1)
        val result2 = transformSimple.execute(testSet2)

        assertNotEquals(0, result1.size)
        assertEquals(5, result1[0])
        assertEquals(4, result2[0])
    }

    @Test
    fun testChaining() {
        val result = transformChaining.execute(testSet2)

        assertEquals(1, result.size)
        assertEquals(17, result[0])
    }

    @Test
    fun testToText() {
        assertEquals("SUMC|col=[0]|(id())", transformSimple.toCompleteString())
        assertEquals(
            "SUMS|col=[0, 2]|(SUMC|col=[0, 1, 3]|(id()))",
            transformChaining.toCompleteString()
        )
    }
}