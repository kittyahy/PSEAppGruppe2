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
import com.pseandroid2.dailydata.model.graph.GraphType
import com.pseandroid2.dailydata.model.settings.Settings
import com.pseandroid2.dailydata.repository.commandCenter.ExecuteQueue
import kotlinx.coroutines.flow.Flow
import  com.pseandroid2.dailydata.model.graph.GraphTemplate as ModelTemplate


class GraphTemplate(
    var title: String,
    var image: Bitmap?,
    override var id: Int,
    var descrition: String,
    var color: Int,
    var settings: Settings,
    var type: GraphType
) : Identifiable, Template() {
    constructor(modelTemplate: ModelTemplate) : this(
        modelTemplate.name,
        BitmapFactory.decodeFile(modelTemplate.path),
        modelTemplate.id,
        modelTemplate.desc,
        modelTemplate.background,
        modelTemplate.customizing,
        modelTemplate.type
    )
    // beschreibung farbe SettingsObj, Typ, erstellung onlineId

    override lateinit var executeQueue: ExecuteQueue
    override lateinit var viewModelProject: ViewModelProject

    override fun deleteIsPossible(): Flow<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun delete() {
        TODO("Not yet implemented")
    }
}
