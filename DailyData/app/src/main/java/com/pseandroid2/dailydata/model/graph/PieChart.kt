package com.pseandroid2.dailydata.model.graph

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.pseandroid2.dailydata.model.graph.Graph.Companion.SET_LABEL_KEY
import com.pseandroid2.dailydata.model.project.Project
import com.pseandroid2.dailydata.model.settings.Settings

class PieChart(
    override var id: Int,
    private val transformation: Project.DataTransformation<Float>,
    private val settings: Settings,
    private val path: String? = null
) : Graph<PieDataSet, PieEntry> {

    companion object {
        const val SLICE_COLOR_KEY = "SLICE COLOR"

        const val PERCENTAGE_ENABLE_KEY = "ENABLE PERCENTAGE"
    }

    override fun getDataSets(): List<PieDataSet> {
        val values = transformation.recalculate()
        val entries = ArrayList<PieEntry>()
        for (value in values) {
            entries.add(PieEntry(value))
        }
        return listOf(PieDataSet(entries, settings[SET_LABEL_KEY]))
    }

    override fun getCustomizing() = settings

    override fun getImage(): Bitmap? {
        return BitmapFactory.decodeFile(path)
    }

    override fun getPath() = path

    override fun getType() = GraphType.PIE_CHART

    override fun getCalculationFunction() = transformation
}