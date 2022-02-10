package com.pseandroid2.dailydata

import android.util.Log
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.pseandroid2.dailydata.model.graph.FloatLineChart
import com.pseandroid2.dailydata.model.graph.Generator
import com.pseandroid2.dailydata.model.graph.Graph
import com.pseandroid2.dailydata.model.graph.IntLineChart
import com.pseandroid2.dailydata.model.project.Project
import com.pseandroid2.dailydata.model.project.SimpleProjectBuilder
import com.pseandroid2.dailydata.model.settings.MapSettings
import com.pseandroid2.dailydata.model.table.ArrayListLayout
import com.pseandroid2.dailydata.model.table.ArrayListRow
import com.pseandroid2.dailydata.model.table.ArrayListTable
import com.pseandroid2.dailydata.model.table.RowMetaData
import com.pseandroid2.dailydata.model.transformation.FloatIdentity
import com.pseandroid2.dailydata.model.transformation.FloatLineChartTransformation
import com.pseandroid2.dailydata.model.transformation.FloatSum
import com.pseandroid2.dailydata.model.transformation.IntLineChartTransformation
import com.pseandroid2.dailydata.model.transformation.LineChartTransformation
import com.pseandroid2.dailydata.model.transformation.PieChartTransformation
import com.pseandroid2.dailydata.util.getSerializableClassName
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import com.pseandroid2.dailydata.model.graph.PieChart as GraphPieChart
import com.pseandroid2.dailydata.model.graph.LineChart as GraphLineChart

class GraphUITester {

    @get:Rule
    val composeRule = createComposeRule()

    companion object {
        val values = listOf(listOf(12.0f, 34.0f, 21.0f, 19.0f), listOf(2.0f, 24.0f, 27.0f, 32.0f))
        val pieSettingsMapMock = mapOf(
            Pair(GraphPieChart.SLICE_COLOR_KEY + 0, "#ff0000"),
            Pair(GraphPieChart.SLICE_COLOR_KEY + 1, "#ff00ff"),
            Pair(GraphPieChart.SLICE_COLOR_KEY + 2, "#0000ff"),
            Pair(GraphPieChart.SLICE_COLOR_KEY + 3, "#00ff00"),
            Pair(GraphPieChart.PERCENTAGE_ENABLE_KEY, Graph.ENABLE),
            Pair(Graph.SET_LABEL_KEY, "Test"),
            Pair(Generator.GRAPH_NAME_KEY, "Test"),
            Pair(GraphPieChart.VALUE_LABEL_ENABLE_KEY, Graph.ENABLE),
            Pair(GraphPieChart.VALUE_LABEL_KEY + 0, "Test 1"),
            Pair(GraphPieChart.VALUE_LABEL_KEY + 1, "Test 2"),
            Pair(GraphPieChart.VALUE_LABEL_KEY + 2, "Test 2"),
            Pair(GraphPieChart.VALUE_LABEL_KEY + 3, "Test 4")
        )
        val lineSettingsMapMock = mapOf(
            Pair(Generator.GRAPH_NAME_KEY, "TestLine"),
            Pair(Graph.SET_LABEL_KEY + 0, "Set 1"),
            Pair(Graph.SET_LABEL_KEY + 1, "Set 2"),
            Pair(GraphLineChart.DOT_ENABLE_KEY + 0, Graph.ENABLE),
            Pair(GraphLineChart.LINE_STYLE_KEY + 0, GraphLineChart.LINE_STYLE_SOLID)
        )
        val pieSettings = mapOf(
            Pair(Generator.GRAPH_NAME_KEY, "TestPie"),
            Pair(Graph.SET_LABEL_KEY, "Set1"),
            Pair(GraphPieChart.VALUE_LABEL_ENABLE_KEY, Graph.ENABLE),
            Pair(GraphPieChart.VALUE_LABEL_KEY + 0, "Test 1"),
            Pair(GraphPieChart.VALUE_LABEL_KEY + 1, "Test 2")
        )
        val lineSettings = mapOf(
            Pair(Generator.GRAPH_NAME_KEY, "TestLine2"),
            Pair(Graph.SET_LABEL_KEY + 0, "Set 1"),
            Pair(Graph.SET_LABEL_KEY + 1, "Set 2")
        )

        lateinit var pieChartMock: GraphPieChart
        lateinit var lineChartMock: FloatLineChart

        lateinit var pieChart: GraphPieChart
        lateinit var lineChart: FloatLineChart

        @JvmStatic
        @BeforeClass
        fun classSetup() {
            val layout = ArrayListLayout()
            layout.addColumn(Float::class.getSerializableClassName(), "Test1")
            layout.addColumn(Float::class.getSerializableClassName(), "Test2")
            val project = SimpleProjectBuilder().addTable(ArrayListTable(layout)).build()

            val cols = mutableListOf<MutableList<Any>>(
                mutableListOf(),
                mutableListOf(),
                mutableListOf(),
                mutableListOf()
            )

            for (i in values[0].indices) {
                for (j in 0..1) {
                    cols[i].add(values[j][i])
                }
            }

            Log.d("XXX", cols.toString())

            project.table.addRow(ArrayListRow(cols[0]))
            project.table.addRow(ArrayListRow(cols[1]))
            project.table.addRow(ArrayListRow(cols[2]))
            project.table.addRow(ArrayListRow(cols[3]))

            val transformationMock: Project.DataTransformation<Float> = mockk()
            val lineMock: Project.DataTransformation<Map<Float, Float>> = mockk()

            every { transformationMock.recalculate() } returns values[0]
            val maps = mutableListOf<Map<Float, Float>>()
            for (list in values) {
                val map = mutableMapOf<Float, Float>()
                for (i in list.indices) {
                    map[i.toFloat()] = list[i]
                }
                maps.add(map)
            }
            every { lineMock.recalculate() } returns maps

            val pieFunc = PieChartTransformation(FloatSum())
            val pieTransform = project.createDataTransformation(pieFunc)

            val lineFunc = FloatLineChartTransformation(FloatIdentity())
            val lineTransform = project.createDataTransformation(lineFunc, listOf(0, 1))

            val pieSettingsMock = MapSettings(pieSettingsMapMock)
            val lineSettingsMock = MapSettings(lineSettingsMapMock)
            val pieSettings = MapSettings(pieSettings)
            val lineSettings = MapSettings(lineSettings)

            pieChartMock = GraphPieChart(0, transformationMock, pieSettingsMock, "")
            lineChartMock = FloatLineChart(1, lineMock, lineSettingsMock, "")
            pieChart = GraphPieChart(2, pieTransform, pieSettings, "")
            lineChart = FloatLineChart(3, lineTransform, lineSettings, "")
        }
    }

    @Test
    fun checkPieChartGeneration() {
        runBlocking {
            composeRule.setContent {
                AndroidView(
                    factory = {
                        Generator.generateChart(pieChartMock, it)
                    },
                    modifier = Modifier.size(500.dp, 500.dp)
                )
            }
            delay(1000)
        }
    }

    @Test
    fun checkLineChartGeneration() {
        runBlocking {
            composeRule.setContent {
                AndroidView(
                    factory = {
                        Generator.generateChart(lineChartMock, it)
                    },
                    modifier = Modifier.size(500.dp, 500.dp)
                )
            }
            delay(1000)
        }
    }

    @Test
    fun checkPieFromProject() {
        runBlocking {
            composeRule.setContent {
                AndroidView(
                    factory = {
                        Generator.generateChart(pieChart, it)
                    },
                    modifier = Modifier.size(500.dp, 500.dp)
                )
            }
            delay(1000)
        }
    }

    @Test
    fun checkLineFromProject() {
        runBlocking {
            composeRule.setContent {
                AndroidView(
                    factory = {
                        Generator.generateChart(lineChart, it)
                    }, modifier = Modifier.size(500.dp, 500.dp)
                )
            }
            delay(10000)
        }
    }
}