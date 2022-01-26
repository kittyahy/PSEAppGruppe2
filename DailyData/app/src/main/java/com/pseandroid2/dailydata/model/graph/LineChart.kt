package com.pseandroid2.dailydata.model.graph

import android.graphics.drawable.Drawable
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet
import com.pseandroid2.dailydata.model.Settings
import com.pseandroid2.dailydata.model.project.Project

abstract class LineChart<T : Any>(
    override var id: Int,
    private val transformation: Project.DataTransformation<Map<T, Float>>
) : Graph<LineDataSet, Entry> {
    override fun getDataSets(): List<LineDataSet> {
        val dataSetMaps = xToFloat(transformation.recalculate())
        val dataSets = mutableListOf<LineDataSet>()
        for (map in dataSetMaps) {
            val dataSet = LineDataSet()
            for (entry in map) {

            }
        }
    }

    override fun getCustomizing(): Settings {
        TODO("Not yet implemented")
    }

    override fun getImage(): Drawable? {
        TODO("Not yet implemented")
    }

    override fun getPath(): String? {
        TODO("Not yet implemented")
    }

    override fun getType(): GraphType {
        TODO("Not yet implemented")
    }

    override fun getCalculationFunction(): Project.DataTransformation<out Any> {
        TODO("Not yet implemented")
    }

    abstract fun xToFloat(maps: List<Map<T, Float>>): List<Map<Float, Float>>
}