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

import android.content.Context
import android.graphics.Bitmap
import android.view.View
import com.pseandroid2.dailydata.model.database.AppDataBase
import com.pseandroid2.dailydata.model.graph.Generator
import com.pseandroid2.dailydata.model.graph.Graph as ModelGraph
import com.pseandroid2.dailydata.model.project.Project
import com.pseandroid2.dailydata.model.project.ProjectBuilder
import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.util.IOUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.runBlocking


abstract class Graph : Identifiable, Convertible<ModelGraph<*, *>> {
    companion object {
        val availableGraphs: MutableList<String> = ArrayList<String>()


        fun createFromType(graph: String): Graph {
            TODO("createFromType")
        }

        fun createFromTemplate(graph: GraphTemplate): Graph {
            TODO("createFromTemplate")
        }

    }


    abstract override val id: Int
    abstract val image: Bitmap?
    abstract val typeName: String
    abstract var appDataBase: AppDataBase

    override fun connectToRepository(repositoryViewModelAPI: RepositoryViewModelAPI) {
        appDataBase = repositoryViewModelAPI.appDataBase
        super.connectToRepository(repositoryViewModelAPI)
    }

    override fun addYourself(builder: ProjectBuilder<out Project>) {
        builder.addGraphs(listOf(toDBEquivalent())) //TODO Arne: es kommen Änderungen
    }

    fun showIsPossible(): Flow<Boolean> {
        //Todo replace with valid proof
        val flow = MutableSharedFlow<Boolean>()
        runBlocking {
            flow.emit(true)
        }
        return flow
    }

    suspend fun show(context: Context): View {
        val graph: ModelGraph<*, *> = TODO("aus DB holen") // Todo richtiger graph typ
        val view = Generator.generateChart(graph, context)
        image = IOUtil.getGraphImage(graph.getCustomizing()[Generator.GRAPH_NAME_KEY], context)
        return view
    }
}
