package com.pseandroid2.dailydata.model

import com.pseandroid2.dailydata.model.table.ArrayListLayout
import com.pseandroid2.dailydata.model.uielements.UIElement
import com.pseandroid2.dailydata.model.uielements.UIElementType
import com.pseandroid2.dailydata.util.getSerializableClassName
import org.junit.Assert.assertEquals
import org.junit.BeforeClass
import org.junit.Test
import java.time.LocalDateTime

class LayoutTester {

    companion object {
        @JvmStatic
        lateinit var layoutList: ArrayList<Pair<String, MutableList<UIElement>>>

        @BeforeClass
        @JvmStatic
        fun classSetup() {
            layoutList = ArrayList(
                mutableListOf(
                    Pair(
                        String::class.getSerializableClassName(),
                        mutableListOf(UIElement(0, UIElementType.BUTTON, "Test", "0"))
                    ),
                    Pair(
                        String::class.getSerializableClassName(),
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
            assertEquals(Class.forName(layoutList[i].first).kotlin, layout.getColumnType(i))
            for (j in layout.getUIElements(i).indices) {
                assertEquals(layoutList[i].second[j], layout.getUIElements(i)[j])
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