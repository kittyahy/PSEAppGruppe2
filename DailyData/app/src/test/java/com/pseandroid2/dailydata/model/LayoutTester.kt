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

import com.pseandroid2.dailydata.model.table.ArrayListLayout
import com.pseandroid2.dailydata.model.table.ColumnData
import com.pseandroid2.dailydata.model.uielements.UIElement
import com.pseandroid2.dailydata.model.uielements.UIElementType
import com.pseandroid2.dailydata.util.Quadruple
import com.pseandroid2.dailydata.util.getSerializableClassName
import org.junit.Assert.assertEquals
import org.junit.BeforeClass
import org.junit.Test
import java.time.LocalDateTime

class LayoutTester {

    companion object {
        @JvmStatic
        lateinit var layoutList: ArrayList<ColumnData>

        @BeforeClass
        @JvmStatic
        fun classSetup() {
            layoutList = ArrayList(
                mutableListOf(
                    ColumnData(
                        0,
                        String::class.getSerializableClassName(),
                        "Test",
                        "Test",
                        mutableListOf(UIElement(0, UIElementType.BUTTON, "Test", "0"))
                    ),
                    ColumnData(
                        1,
                        String::class.getSerializableClassName(),
                        "Test",
                        "Test",
                        mutableListOf(
                            UIElement(1, UIElementType.BUTTON, "Test", "0.0"),
                            UIElement(2, UIElementType.NUMBER_FIELD, "Test", "0.1")
                        )
                    )
                )
            )
        }
    }

    @Test
    fun checkListConstructor() {
        val layout = ArrayListLayout(layoutList)
        for (i in layoutList.indices) {
            assertEquals(Class.forName(layoutList[i].type).kotlin, layout.getColumnType(i))
            for (j in layout.getUIElements(i).indices) {
                assertEquals(layoutList[i].uiElements[j], layout.getUIElements(i)[j])
            }
        }
    }

    @Test
    fun checkTypeStrings() {
        assertEquals(Class.forName(String::class.getSerializableClassName()).kotlin, String::class)
        assertEquals(Class.forName(Int::class.getSerializableClassName()).kotlin, Int::class)
        assertEquals(
            Class.forName(LocalDateTime::class.getSerializableClassName()).kotlin,
            LocalDateTime::class
        )
    }
}