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
import android.graphics.drawable.Drawable
import com.pseandroid2.dailydata.model.graph.Graph
import com.pseandroid2.dailydata.model.project.Project
import com.pseandroid2.dailydata.model.project.ProjectBuilder


abstract class Graph : Identifiable, Convertible<Graph<*, *>> {
    companion object {
        val availableGraphs: MutableList<String> = ArrayList<String>()

        //TODO("Robin changes")
        fun createFromType(graph: String): com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Graph {
            TODO()
        }

        fun createFromTemplate(graph: GraphTemplate): com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Graph {
            TODO()
        }

    }

    init {
        availableGraphs.add(typeName)
    }

    abstract override val id: Int
    abstract val image: Bitmap //TODO("Robin changes")
    abstract val typeName: String

    override fun toDBEquivalent(): Graph<*, *> {
        return TODO() //Todo Arne fragen, wie ich den richtigen Graph erstelle: Kommt noch
    }

    override fun addYourself(builder: ProjectBuilder<out Project>) {
        builder.addGraphs(listOf(toDBEquivalent())) //TODO Arne: es kommen Änderungen
    }
}
