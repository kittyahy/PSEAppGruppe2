package com.pseandroid2.dailydata.model.graph

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet
import com.pseandroid2.dailydata.model.graph.Graph.Companion.SET_LABEL_KEY
import com.pseandroid2.dailydata.model.settings.Settings
import com.pseandroid2.dailydata.model.project.Project

abstract class LineChart<T : Any>(
    override var id: Int,
    private val transformation: Project.DataTransformation<Map<T, Float>>,
    private val settings: Settings,
    private val path: String? = null
) : Graph<LineDataSet, Entry> {
    companion object {
        const val DOT_SIZE_KEY = "DOT SIZE"
        const val DOT_COLOR_KEY = "DOT COLOR"
        const val DOT_ENABLE_KEY = "DOT ENABLE"

        const val LINE_STYLE_KEY = "LINE STYLE"
        const val LINE_COLOR_KEY = "LINE COLOR"
        const val LINE_STRENGTH_KEY = "LINE STRENGTH"

        const val LINE_STYLE_NONE = "NONE"
        const val LINE_STYLE_SOLID = "SOLID"
    }

    override fun getDataSets(): List<LineDataSet> {
        val dataSetMaps = xToFloat(transformation.recalculate())
        val dataSets = mutableListOf<LineDataSet>()
        for (i in dataSetMaps.indices) {
            val entries = ArrayList<Entry>()
            for (entry in dataSetMaps[i]) {
                entries.add(Entry(entry.key, entry.value))
            }
            dataSets.add(LineDataSet(entries, settings[SET_LABEL_KEY + i]))
        }
        return dataSets.toList()
    }

    override fun getCustomizing() = settings

    override fun getImage(): Bitmap? {
        return BitmapFactory.decodeFile(path)
    }

    override fun getPath() = path

    override fun getCalculationFunction() = transformation

    abstract fun xToFloat(maps: List<Map<T, Float>>): List<Map<Float, Float>>
}