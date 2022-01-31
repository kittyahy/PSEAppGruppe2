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
import com.pseandroid2.dailydata.model.database.entities.ProjectTemplateData
import com.pseandroid2.dailydata.model.table.TableLayout
import com.pseandroid2.dailydata.repository.commandCenter.ExecuteQueue

//TODO("Robin changes")
class ProjectTemplate(
    var titel : String,
    var description : String,
    var wallpaper : Int,
    var table : List<Column>,
    var buttons : List<Button>,
    var notifications : List<Notification>,
    var graphTemplates: List<GraphTemplate>,
    var image : Bitmap
) : Identifiable{
    constructor(projectTemplateData: ProjectTemplateData) : this(
        projectTemplateData.name,
        projectTemplateData.description,
        projectTemplateData.wallpaper,
        extractColumnsButtons(projectTemplateData.layout).first,
        extractColumnsButtons(projectTemplateData.layout).second,
        projectTemplateData.layout
    )
    companion object {
        private fun extractColumnsButtons(layout: TableLayout) : Pair<List<Column>, List<Button>> {
            val returnPair = Pair(ArrayList<Column>(), ArrayList<Button>())
            for(layoutPair in layout) {
                returnPair.first.add(Column(layoutPair.first))
                returnPair.second.add(Button(layoutPair.second)) //TODO Arne: es kommen Änderungen
            }
            return returnPair
        }
    }

    override lateinit var executeQueue: ExecuteQueue
    override val id: Int
        get() = TODO("Not yet implemented")

    override fun deleteIsPossible(): Boolean {
        TODO("Not yet implemented")
    }
    //@throws IllegalOperationException
    override suspend fun delete() {
        TODO("Not yet implemented")
    }
    //TODO Implementierung
    fun toProject(): Project {
        TODO()
    }
}
