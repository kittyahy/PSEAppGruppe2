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

import com.pseandroid2.dailydata.model.transformation.Identity
import com.pseandroid2.dailydata.model.transformation.IntSum
import com.pseandroid2.dailydata.model.transformation.Sum
import com.pseandroid2.dailydata.model.transformation.TransformationFunction
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test

class DataTransformationTester {
    private lateinit var transform1: Sum<Int>
    private lateinit var transform2: Sum<Int>
    private lateinit var transformId: Identity
    private val testSet1 = listOf(listOf(2, 3))
    private val testSet2 = listOf(
        listOf(2, 3, -1), listOf(9, 21, -20), listOf(234, 92, -10000), listOf(3)
    )

    @Before
    fun setup() {
        transform1 = IntSum(listOf(0))
        transform2 = IntSum(listOf(0, 1, 3))
        transformId = Identity()
    }

    @Test
    fun testSimpleTransform() {
        val result1 = transform1.execute(testSet1)
        val result2 = transform2.execute(testSet2)
        val result3 = transformId.execute(testSet2)

        assertNotEquals(0, result1.size)
        assertEquals(5, result1[0])
        assertEquals(3, result2.size)
        val cols = listOf(0, 1, 3)
        for (i in 0..2) {
            assertEquals(testSet2[cols[i]].sum(), result2[i])
        }

        for (i in testSet2.indices) {
            for (j in testSet2[i].indices) {
                assertEquals(testSet2[i][j], result3[i][j])
            }
        }
    }

    @Test
    fun testToText() {
        assertEquals(
            "${TransformationFunction.SUM_ID}|col=[0];type=INT",
            transform1.toCompleteString()
        )
        assertEquals(
            "${TransformationFunction.SUM_ID}|col=[0, 1, 3];type=INT",
            transform2.toCompleteString()
        )
    }

    @Test
    fun testFromText() {
        assertEquals(
            transform1.execute(testSet1)[0],
            TransformationFunction.parse(transform1.toCompleteString()).execute(testSet1)[0]
        )
        assertEquals(
            transform2.execute(testSet1)[0],
            TransformationFunction.parse(transform2.toCompleteString()).execute(testSet1)[0]
        )
    }
}