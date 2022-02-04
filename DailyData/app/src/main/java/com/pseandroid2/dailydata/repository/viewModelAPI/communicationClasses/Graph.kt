/*

    DailyData is an android app to easily create diagrams from data one has collected
    Copyright (C) 2022  Antonia Heiming, Anton Kadelbach, Arne Kuchenbecker, Merlin Opp, Robin Amman

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.

*/

package com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import com.google.gson.Gson
import com.pseandroid2.dailydata.model.database.AppDataBase
import com.pseandroid2.dailydata.model.graph.GraphType
import com.pseandroid2.dailydata.model.graph.LineChart.Companion.DOT_COLOR_KEY
import com.pseandroid2.dailydata.model.graph.LineChart.Companion.LINE_STYLE_KEY
import com.pseandroid2.dailydata.model.graph.LineChart.Companion.LINE_STYLE_NONE
import com.pseandroid2.dailydata.model.graph.LineChart.Companion.LINE_STYLE_SOLID
import com.pseandroid2.dailydata.model.graph.Graph as ModelGraph
import com.pseandroid2.dailydata.model.project.Project
import com.pseandroid2.dailydata.model.project.ProjectBuilder
import com.pseandroid2.dailydata.model.table.ArrayListLayout
import com.pseandroid2.dailydata.model.table.TableLayout


abstract class Graph : Identifiable, Convertible<ModelGraph<*, *>> {
    companion object {
        val availableGraphs: MutableList<String> = ArrayList<String>()


        fun createFromType(graph: String): com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Graph {
            TODO("createFromType")
        }

        fun createFromTemplate(graph: GraphTemplate): com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Graph {
            TODO("createFromTemplate")
        }

    }


    abstract override val id: Int
    abstract val image: Bitmap
    abstract val typeName: String

    override fun toDBEquivalent(): ModelGraph<*, *> {
        return TODO("toDBEquivalentGraph") //Todo Arne fragen, wie ich den richtigen Graph erstelle: Kommt noch
    }

    override fun addYourself(builder: ProjectBuilder<out Project>) {
        builder.addGraphs(listOf(toDBEquivalent())) //TODO Arne: es kommen Änderungen
    }
}

fun ModelGraph<*, *>.toViewGraph(layout: TableLayout): Graph {
    val settings = this.getCustomizing()
    return when (this.getType()) {
        GraphType.FLOAT_LINE_CHART, GraphType.TIME_LINE_CHART, GraphType.INT_LINE_CHART -> {
            //TODO getImage probably shouldn't have a NPE thrown, DotSize should be dependent on graph settings
            val dotColor =
                if (settings.containsKey(DOT_COLOR_KEY)) {
                    Color.parseColor(settings[DOT_COLOR_KEY])
                } else {
                    Color.BLACK
                }
            val lineStyle =
                if (settings.containsKey(LINE_STYLE_KEY)) {
                    when (settings[LINE_STYLE_KEY]) {
                        LINE_STYLE_NONE -> LineType.NONE
                        LINE_STYLE_SOLID -> LineType.CONTINUOUS
                        else -> LineType.CONTINUOUS
                    }
                } else {
                    LineType.CONTINUOUS
                }
            val columns = mutableListOf<Column>()
            for (i in this.getCalculationFunction().cols) {
                columns.add(
                    Column(
                        i,
                        layout[i].name,
                        layout[i].unit,
                        DataType.fromSerializableClassName(layout[i].type)
                    )
                )
            }
            LineChart(
                this.id,
                this.getImage()!!, //TODO
                DotSize.MEDIUM, //TODO
                dotColor,
                lineStyle,
                columns
            )

        }
        GraphType.PIE_CHART -> TODO()
    }
}
