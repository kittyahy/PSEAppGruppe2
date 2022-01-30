package com.pseandroid2.dailydata.model.graph

import android.content.Context
import android.graphics.Color
import com.github.mikephil.charting.charts.Chart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.pseandroid2.dailydata.model.settings.Settings
import com.pseandroid2.dailydata.model.graph.LineChart.Companion.DOT_COLOR_KEY
import com.pseandroid2.dailydata.model.graph.LineChart.Companion.DOT_ENABLE
import com.pseandroid2.dailydata.model.graph.LineChart.Companion.DOT_ENABLE_KEY
import com.pseandroid2.dailydata.model.graph.LineChart.Companion.DOT_SIZE_KEY
import com.pseandroid2.dailydata.model.graph.LineChart.Companion.LINE_COLOR_KEY
import com.pseandroid2.dailydata.model.graph.LineChart.Companion.LINE_STRENGTH_KEY
import com.pseandroid2.dailydata.model.graph.LineChart.Companion.LINE_STYLE_KEY
import com.pseandroid2.dailydata.model.graph.LineChart.Companion.LINE_STYLE_NONE
import com.pseandroid2.dailydata.model.graph.LineChart.Companion.LINE_STYLE_SOLID
import com.pseandroid2.dailydata.util.IOUtil

object Generator {
    const val GRAPH_NAME_KEY = "NAME"
    const val GRAPH_DIR_NAME = "Charts"

    fun generateLineChart(
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
                if (settings[DOT_ENABLE_KEY + i] == DOT_ENABLE) {
                    data[i].circleRadius = 0.0f
                }
            }
        }
        val lineChart = LineChart(context)
        lineChart.data = LineData(data)
        saveChart(lineChart, settings[GRAPH_NAME_KEY], context)
        return lineChart
    }

    private fun saveChart(chart: Chart<*>, name: String, context: Context) {
        chart.saveToPath(name, IOUtil.getSDRelativePath(GRAPH_DIR_NAME, context))
    }
}