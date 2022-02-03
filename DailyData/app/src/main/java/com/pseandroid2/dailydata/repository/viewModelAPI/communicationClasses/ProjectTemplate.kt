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
import com.pseandroid2.dailydata.repository.commandCenter.ExecuteQueue
import kotlinx.coroutines.flow.Flow


class ProjectTemplate : Identifiable {
    lateinit var titel: String
    lateinit var description: String
    var wallpaper: Int = 0
    lateinit var table: List<Column>
    lateinit var buttons: List<Button>
    lateinit var notifications: List<Notification>
    lateinit var graphTemplates: List<GraphTemplate>
    lateinit var image: Bitmap

    constructor(
        titel: String,
        description: String,
        wallpaper: Int,
        table: List<Column>,
        buttons: List<Button>,
        notifications: List<Notification>,
        graphTemplates: List<GraphTemplate>,
        image: Bitmap
    ) {
        this.titel = titel
        this.description = description
        this.wallpaper = wallpaper
        this.table = table
        this.buttons = buttons
        this.notifications = notifications
        this.graphTemplates = graphTemplates
        this.image = image
    }

    constructor(projectTemplateData: ProjectTemplateData) {
        this.titel = projectTemplateData.name
        this.description = projectTemplateData.description
        this.wallpaper = TODO("projectTemplateData.wallpaper")//projectTemplateData.wallpaper
        val layout = projectTemplateData.layout
        val buttons = ArrayList<Button>()
        val table = ArrayList<Column>()
        for (i in 0 until layout.getSize()) {
            val columnData = layout[i]
            for (uIElement in columnData.uiElements) {
                buttons.add(Button(uIElement, i))
            }
            table.add(
                Column(
                    i,
                    columnData.name,
                    columnData.unit,
                    DataType.fromSerializableClassName(columnData.type)
                )
            )
        }

    }

    override lateinit var executeQueue: ExecuteQueue
    override lateinit var project: Project
    override val id: Int
        get() = TODO("Not yet implemented")

    override fun deleteIsPossible(): Flow<Boolean> {
        TODO("Not yet implemented")
    }

    //@throws IllegalOperationException
    override suspend fun delete() {
        TODO("Not yet implemented")
    }

    //TODO Implementierung
    fun toProject(): Project {
        TODO("toProject")
    }
}
