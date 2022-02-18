package com.pseandroid2.dailydata.model.graph

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.Color
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.pseandroid2.dailydata.model.graph.Graph.Companion.SET_LABEL_KEY
import com.pseandroid2.dailydata.model.project.Project
import com.pseandroid2.dailydata.model.settings.Settings

class PieChart(
    override var id: Int = -1,
    private val transformation: Project.DataTransformation<Float>,
    private val settings: Settings,
    private val path: String? = null
) : Graph<PieDataSet, PieEntry> {

    companion object {
        const val SLICE_COLOR_KEY = "SLICE COLOR"

        const val PERCENTAGE_ENABLE_KEY = "ENABLE PERCENTAGE"

        const val VALUE_LABEL_ENABLE_KEY = "ENABLE VALUES LABEL"
        const val VALUE_LABEL_KEY = "VALUE LABEL"
    }

    override val primaryColors: MutableMap<Int, Color> = mutableMapOf()

    val showPercentages: Boolean = false

    suspend fun setShowPercentage(showPercentages: Boolean) {
        TODO("Call the respective Command")
    }

    override fun getDataSets(): List<PieDataSet> {
        val values = transformation.recalculate()
        val entries = ArrayList<PieEntry>()
        for (i in values.indices) {
            val label = if (settings.containsKey(VALUE_LABEL_KEY + i)) {
                settings[VALUE_LABEL_KEY + i]
            } else {
                ""
            }
            entries.add(PieEntry(values[i], label))
        }
        if (!settings.containsKey(SET_LABEL_KEY)) {
            settings[SET_LABEL_KEY] = ""
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

    override fun applyTemplateSettings(template: GraphTemplate) {
        TODO("Not yet implemented")
    }
}