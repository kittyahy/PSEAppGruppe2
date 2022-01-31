package com.pseandroid2.dailydata

import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.pseandroid2.dailydata.model.graph.Generator
import com.pseandroid2.dailydata.model.graph.Graph
import com.pseandroid2.dailydata.model.project.Project
import com.pseandroid2.dailydata.model.settings.MapSettings
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import com.pseandroid2.dailydata.model.graph.PieChart as GraphPieChart

class GraphUITester {

    @get:Rule
    val composeRule = createComposeRule()

    companion object {
        val values = listOf(listOf(12.0f, 34.0f, 21.0f, 19.0f))
        val pieSettingsMap = mapOf(
            Pair(GraphPieChart.SLICE_COLOR_KEY + 0, "#ff0000"),
            Pair(GraphPieChart.SLICE_COLOR_KEY + 1, "#ff00ff"),
            Pair(GraphPieChart.SLICE_COLOR_KEY + 2, "#0000ff"),
            Pair(GraphPieChart.SLICE_COLOR_KEY + 3, "#00ff00"),
            Pair(GraphPieChart.PERCENTAGE_ENABLE_KEY, Graph.ENABLE),
            Pair(Graph.SET_LABEL_KEY, "Test"),
            Pair(Generator.GRAPH_NAME_KEY, "Test")
        )
        lateinit var pieChart: GraphPieChart

        @JvmStatic
        @BeforeClass
        fun classSetup() {
            val transformationMock: Project.DataTransformation<Float> = mockk()
            every { transformationMock.recalculate() } returns values[0]

            val pieSettings = MapSettings(pieSettingsMap)

            pieChart = GraphPieChart(0, transformationMock, pieSettings, "")
        }
    }

    @Test
    fun checkPieChartGeneration() {
        runBlocking {
            composeRule.setContent {
                AndroidView(
                    factory = {
                        Generator.generateChart(pieChart, it)
                    },
                    modifier = Modifier.size(500.dp, 500.dp)
                )
            }
            delay(10000)
        }
    }
}