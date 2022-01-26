package com.pseandroid2.dailydata.model.graph

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Environment
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet
import com.pseandroid2.dailydata.model.Settings
import com.pseandroid2.dailydata.model.project.Project
import java.io.File

abstract class LineChart<T : Any>(
    override var id: Int,
    private val transformation: Project.DataTransformation<Map<T, Float>>,
    private val settings: Settings,
    private val path: String? = null
) : Graph<LineDataSet, Entry> {
    override fun getDataSets(): List<LineDataSet> {
        val dataSetMaps = xToFloat(transformation.recalculate())
        val dataSets = mutableListOf<LineDataSet>()
        for (map in dataSetMaps) {
            val entries = ArrayList<Entry>()
            for (entry in map) {
                entries.add(Entry(entry.key, entry.value))
            }
            dataSets.add(LineDataSet(entries, ""))
        }
        return dataSets.toList()
    }

    override fun getCustomizing() = settings

    override fun getImage(): Bitmap? {
        return if (path != null) {
            BitmapFactory.decodeFile(path)
        } else {
            null
        }
    }

    override fun getPath() = path

    override fun getType() = GraphType.LINE_CHART

    override fun getCalculationFunction() = transformation

    abstract fun xToFloat(maps: List<Map<T, Float>>): List<Map<Float, Float>>
}