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

package com.pseandroid2.dailydata.ui.project.creation

import androidx.compose.ui.graphics.Color
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.DataType
import com.pseandroid2.dailydata.model.graph.Graph
import com.pseandroid2.dailydata.model.graph.GraphType
import java.time.LocalTime

sealed class ProjectCreationEvent {

    data class OnTitleChange(val title : String) : ProjectCreationEvent()
    data class OnDescriptionChange(val description : String) : ProjectCreationEvent()
    data class OnWallpaperChange(val wallpaper : Color) : ProjectCreationEvent()
    data class OnTableAdd(val name : String, val unit : String, val dataType: DataType) : ProjectCreationEvent()
    data class OnTableRemove(val index : Int) : ProjectCreationEvent()
    data class OnButtonAdd(val name : String, val columnId : Int, val value: Int) : ProjectCreationEvent()
    data class OnButtonRemove(val id: Int, val columnID: Int) : ProjectCreationEvent()
    data class OnNotificationAdd(val message : String, val time : LocalTime) : ProjectCreationEvent()
    data class OnNotificationRemove(val index : Int) : ProjectCreationEvent()
    data class OnGraphAdd(val graph: GraphType) : ProjectCreationEvent()
    data class OnGraphRemove(val graph: Graph<*, *>) : ProjectCreationEvent()
    object OnSaveClick : ProjectCreationEvent()

    //dialogs
    data class OnShowWallpaperDialog(val isOpen : Boolean) : ProjectCreationEvent()
    data class OnShowTableDialog(val isOpen : Boolean) : ProjectCreationEvent()
    data class OnShowButtonsDialog(val isOpen : Boolean) : ProjectCreationEvent()
    data class OnShowNotificationDialog(val isOpen : Boolean) : ProjectCreationEvent()
    data class OnShowGraphDialog(val isOpen : Boolean) : ProjectCreationEvent()

    data class OnShowBackDialog(val isOpen : Boolean) : ProjectCreationEvent()

    object OnNavigateBack : ProjectCreationEvent()

}
