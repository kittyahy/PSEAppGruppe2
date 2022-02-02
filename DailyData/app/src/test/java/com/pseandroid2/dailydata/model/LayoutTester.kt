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
                        String::class.getSerializableClassName(),
                        "Test",
                        "Test",
                        mutableListOf(UIElement(0, UIElementType.BUTTON, "Test", "0"))
                    ),
                    ColumnData(
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