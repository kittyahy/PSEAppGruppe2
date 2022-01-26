package com.pseandroid2.dailydata.model.graph

import android.content.Context
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.pseandroid2.dailydata.model.Settings

class Generator private constructor() {
    companion object {
        @JvmStatic
        private var instance: Generator? = null

        @JvmStatic
        fun getInstance(): Generator {
            return instance ?: Generator()
        }
    }

    fun generateLineChart(
        data: List<LineDataSet>,
        settings: Settings,
        context: Context
    ): LineChart {
        //TODO handle settings
        val lineChart = LineChart(context)
        lineChart.data = LineData(data)
    }
}