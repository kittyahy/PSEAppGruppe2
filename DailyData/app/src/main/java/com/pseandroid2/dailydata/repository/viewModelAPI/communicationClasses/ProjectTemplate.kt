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
import android.graphics.BitmapFactory
import com.pseandroid2.dailydata.model.notifications.TimeNotification
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.forRepoReturns.TemplateDetailWithPicture
import com.pseandroid2.dailydata.repository.commandCenter.ExecuteQueue
import kotlinx.coroutines.flow.Flow
import com.pseandroid2.dailydata.model.project.ProjectTemplate as ModelProjectTemplate


class ProjectTemplate : Identifiable, Template {
    lateinit var titel: String
    lateinit var description: String
    var wallpaper: Int = 0
    lateinit var table: List<Column>
    lateinit var buttons: List<Button>
    lateinit var notifications: List<Notification>
    lateinit var graphTemplates: List<GraphTemplate>
    var image: Bitmap? = null

    constructor(
        title: String = "<empty>",
        description: String = "<empty>",
        wallpaper: Int = 0,
        table: List<Column> = ArrayList(),
        buttons: List<Button> = ArrayList(),
        notifications: List<Notification> = ArrayList(),
        graphTemplates: List<GraphTemplate> = ArrayList(),
        image: Bitmap? = null
    ) {
        this.titel = title
        this.description = description
        this.wallpaper = wallpaper
        this.table = table
        this.buttons = buttons
        this.notifications = notifications
        this.graphTemplates = graphTemplates
        this.image = image
    }

    constructor(
        templateDetailWithPicture: TemplateDetailWithPicture,
        graphTemplates: List<GraphTemplate>
    )

    constructor(template: ModelProjectTemplate) {
        this.titel = template.name
        this.description = template.desc
        this.wallpaper = template.color
        val layout = template.getTableLayout()
        this.table = layout.toColumnList()
        val uiElements = mutableListOf<Button>()
        for (i in 0 until layout.getSize()) {
            for (element in layout.getUIElements(i)) {
                uiElements.add(Button(element, i))
            }
        }
        this.buttons = uiElements.toList()
        val notifs = mutableListOf<Notification>()
        for (notification in template.notifications) {
            if (notification is TimeNotification) {
                notifs.add(Notification(notification))
            }
        }
        this.notifications = notifs
        val graphs = mutableListOf<GraphTemplate>()
        for (graph in template.graphs) {
            graphs.add(GraphTemplate(graph))
        }
        this.image = BitmapFactory.decodeFile(template.path)
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
