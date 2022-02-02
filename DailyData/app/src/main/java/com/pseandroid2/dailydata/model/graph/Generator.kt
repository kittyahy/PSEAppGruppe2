package com.pseandroid2.dailydata.model.graph

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.Log
import android.view.View
import androidx.compose.foundation.layout.size
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.Chart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.formatter.PercentFormatter
import com.pseandroid2.dailydata.model.graph.Graph.Companion.ENABLE
import com.pseandroid2.dailydata.model.graph.LineChart.Companion.DOT_COLOR_KEY
import com.pseandroid2.dailydata.model.graph.LineChart.Companion.DOT_ENABLE_KEY
import com.pseandroid2.dailydata.model.graph.LineChart.Companion.DOT_SIZE_KEY
import com.pseandroid2.dailydata.model.graph.LineChart.Companion.LINE_COLOR_KEY
import com.pseandroid2.dailydata.model.graph.LineChart.Companion.LINE_STRENGTH_KEY
import com.pseandroid2.dailydata.model.graph.LineChart.Companion.LINE_STYLE_KEY
import com.pseandroid2.dailydata.model.graph.LineChart.Companion.LINE_STYLE_NONE
import com.pseandroid2.dailydata.model.graph.LineChart.Companion.LINE_STYLE_SOLID
import com.pseandroid2.dailydata.model.graph.PieChart.Companion.PERCENTAGE_ENABLE_KEY
import com.pseandroid2.dailydata.model.graph.PieChart.Companion.SLICE_COLOR_KEY
import com.pseandroid2.dailydata.model.graph.PieChart.Companion.VALUE_LABEL_ENABLE_KEY
import com.pseandroid2.dailydata.model.graph.PieChart.Companion.VALUE_LABEL_KEY
import com.pseandroid2.dailydata.model.settings.Settings
import com.pseandroid2.dailydata.util.IOUtil

object Generator {
    const val GRAPH_NAME_KEY = "NAME"
    const val GRAPH_DIR_NAME = "Charts"

    fun generateChart(
        graph: Graph<*, *>,
        context: Context
    ): View {
        return when (graph.getType()) {
            GraphType.LINE_CHART -> {
                //graph is a Line Chart and thus will always return LineDataSets
                @Suppress("Unchecked_Cast")
                generateLineChart(
                    graph.getDataSets() as List<LineDataSet>,
                    graph.getCustomizing(),
                    context
                )
            }
            GraphType.PIE_CHART -> {
                //graph is a Pie Chart and thus will always return a single PieDataSet
                @Suppress("Unchecked_Cast")
                generatePieChart(
                    graph.getDataSets()[0] as PieDataSet,
                    graph.getCustomizing(),
                    context
                )
            }
        }
    }

    private fun generateLineChart(
        data: List<LineDataSet>,
        settings: Settings,
        context: Context
    ): LineChart {
        for (i in data.indices) {
            //Line Color
            if (settings.containsKey(LINE_COLOR_KEY + i)) {
                data[i].color = Color.parseColor(settings[LINE_COLOR_KEY + i])
            } else {
                data[i].color = Color.BLACK
            }

            //Line Width
            if (settings.containsKey(LINE_STRENGTH_KEY + i)) {
                data[i].lineWidth = settings[LINE_STRENGTH_KEY + i].toFloat()
            } else {
                data[i].lineWidth = 5.0f
            }

            //Line Style
            if (settings.containsKey(LINE_STYLE_KEY + i)) {
                when (settings[LINE_STYLE_KEY + i]) {
                    LINE_STYLE_SOLID -> {
                        data[i].setColor(data[i].color, 1)
                    }
                    LINE_STYLE_NONE -> {
                        data[i].setColor(data[i].color, 0)
                    }
                    else -> {
                        data[i].setColor(data[i].color, 1)
                    }
                }
            } else {
                data[i].setColor(data[i].color, 1)
            }

            //DOT SIZE
            if (settings.containsKey(DOT_SIZE_KEY + i)) {
                data[i].circleRadius = settings[DOT_SIZE_KEY + i].toFloat()
            } else {
                data[i].circleRadius = 20.0f
            }

            //DOT COLOR
            if (settings.containsKey(DOT_COLOR_KEY + i)) {
                data[i].circleColors = listOf(Color.parseColor(settings[DOT_COLOR_KEY + i]))
            } else {
                data[i].circleColors = listOf(Color.WHITE)
            }

            //DISABLE DOTS
            if (settings.containsKey(DOT_ENABLE_KEY + i)) {
                if (settings[DOT_ENABLE_KEY + i] == ENABLE) {
                    data[i].circleRadius = 0.0f
                }
            }
        }
        val lineChart = LineChart(context)
        lineChart.data = LineData(data)
        IOUtil.saveToFile(lineChart, GRAPH_DIR_NAME, settings[GRAPH_NAME_KEY], context)
        return lineChart
    }

    private fun generatePieChart(
        dataSet: PieDataSet,
        settings: Settings,
        context: Context
    ): PieChart {
        //Get rid of the base Color
        dataSet.resetColors()
        for (i in 0 until dataSet.entryCount) {
            //Set Slice Color
            if (settings.containsKey(SLICE_COLOR_KEY + i)) {
                dataSet.colors.add(Color.parseColor(settings[SLICE_COLOR_KEY + i]))
            } else {
                dataSet.colors.add(
                    Color.argb(
                        255, 0, (255 / (dataSet.entryCount - 1)) * i,
                        (255 / (dataSet.entryCount - 1)) * (dataSet.entryCount - i)
                    )
                )
            }
        }

        val data = PieData(dataSet)

        //Increase Text Size of Entries
        data.setValueTextSize(60.0f)

        val pieChart = PieChart(context)
        pieChart.data = data

        //Make the hole in the middle slightly smaller as per Default
        pieChart.holeRadius = pieChart.holeRadius / 1.5f
        pieChart.setHoleColor(Color.TRANSPARENT)
        pieChart.transparentCircleRadius = pieChart.transparentCircleRadius / 1.25f

        //Disable Description
        pieChart.description.isEnabled = false

        //Disable Legend
        pieChart.legend.isEnabled = false

        //Enable Label Entry Drawing
        if (settings.containsKey(VALUE_LABEL_ENABLE_KEY)) {
            pieChart.setDrawEntryLabels(settings[VALUE_LABEL_ENABLE_KEY] == ENABLE)
            pieChart.setEntryLabelTextSize(30.0f)
            pieChart.setEntryLabelColor(Color.BLACK)
        } else {
            pieChart.setDrawEntryLabels(false)
        }

        //Enable Percentage display
        if (settings.containsKey(PERCENTAGE_ENABLE_KEY)) {
            if (settings[PERCENTAGE_ENABLE_KEY] == ENABLE) {
                pieChart.setUsePercentValues(true)
                pieChart.data.setValueFormatter(PercentFormatter(pieChart))
            } else {
                pieChart.setDrawEntryLabels(false)
            }
        } else {
            pieChart.setDrawEntryLabels(false)
        }
        IOUtil.saveToFile(pieChart, GRAPH_DIR_NAME, settings[GRAPH_NAME_KEY], context)
        return pieChart
    }
}