package com.pseandroid2.dailydata.ui

import androidx.compose.ui.test.junit4.createComposeRule
import com.pseandroid2.dailydata.model.graph.Generator
import com.pseandroid2.dailydata.model.graph.PieChart
import com.pseandroid2.dailydata.model.project.Project
import com.pseandroid2.dailydata.model.settings.MapSettings
import com.pseandroid2.dailydata.model.table.ArrayListLayout
import com.pseandroid2.dailydata.model.table.ArrayListRow
import com.pseandroid2.dailydata.model.table.ArrayListTable
import com.pseandroid2.dailydata.model.table.ColumnData
import com.pseandroid2.dailydata.model.table.RowMetaData
import com.pseandroid2.dailydata.model.table.Table
import com.pseandroid2.dailydata.model.transformation.FloatSum
import com.pseandroid2.dailydata.model.transformation.PieChartTransformation
import com.pseandroid2.dailydata.model.users.SimpleUser
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.DataType
import com.pseandroid2.dailydata.ui.project.data.graph.PieChartDialog
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PieChartDialogTester {
    lateinit var table: Table
    val admin = SimpleUser("admin", "admin")
    lateinit var pieCart: PieChart

    @get:Rule
    val composeRule = createComposeRule()

    @Before
    fun setup() {
        runBlocking {
            val values = listOf(listOf(2, 6), listOf(3, 5), listOf(9, 5), listOf(1, 2))
            val layout = ArrayListLayout(
                listOf(
                    ColumnData(0, DataType.WHOLE_NUMBER, "Col 1", "m", listOf()),
                    ColumnData(1, DataType.WHOLE_NUMBER, "Col 2", "m", listOf())
                )
            )
            table = ArrayListTable(layout)
            for (i in values.indices) {
                table.addRow(
                    ArrayListRow(values[i].toMutableList(), RowMetaData(createdBy = admin))
                )
            }
            val transformation = PieChartTransformation(FloatSum())

            @Suppress("Deprecation")
            val calc = Project.DataTransformation(
                table,
                transformation,
                layout.columnDataList.toMutableList()
            )
            pieCart = PieChart(0, calc, MapSettings(mapOf(Generator.GRAPH_NAME_KEY to "Test")))
        }
    }
    
    @Test
    fun testDialog() {
        runBlocking {
            composeRule.setContent {
                PieChartDialog(graph = pieCart, table = table)
            }
            delay(100000)
        }
    }
}