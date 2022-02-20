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

package com.pseandroid2.dailydata.ui.project.data.settings

import androidx.compose.ui.graphics.Color
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.DataType
import com.pseandroid2.dailydata.model.graph.Graph
import com.pseandroid2.dailydata.model.graph.GraphType
import com.pseandroid2.dailydata.model.table.ColumnData
import com.pseandroid2.dailydata.model.users.User
import java.time.LocalTime

sealed class ProjectDataSettingsScreenEvent {

    data class OnCreate(val projectId: Int) : ProjectDataSettingsScreenEvent()

    object OnCreateLink : ProjectDataSettingsScreenEvent()

    data class OnTitleChange(val title: String) : ProjectDataSettingsScreenEvent()
    data class OnDescriptionChange(val description: String) : ProjectDataSettingsScreenEvent()
    data class OnWallpaperChange(val wallpaper: Color) : ProjectDataSettingsScreenEvent()
    data class OnTableAdd(val name: String, val unit: String, val dataType: DataType) :
        ProjectDataSettingsScreenEvent()

    data class OnColumnRemove(val columnId: Int) : ProjectDataSettingsScreenEvent()
    data class OnButtonAdd(val name: String, val columnId: Int, val value: Int) :
        ProjectDataSettingsScreenEvent()

    data class OnButtonRemove(val id: Int, val columnId: Int) : ProjectDataSettingsScreenEvent()
    data class OnNotificationAdd(val message: String, val time: LocalTime) :
        ProjectDataSettingsScreenEvent()

    data class OnNotificationRemove(val id: Int) : ProjectDataSettingsScreenEvent()
    object OnGraphAdd : ProjectDataSettingsScreenEvent()
    data class OnGraphRemove(val id: Int) : ProjectDataSettingsScreenEvent()
    object OnSaveClick : ProjectDataSettingsScreenEvent()

    //dialogs
    data class OnShowWallpaperDialog(val isOpen: Boolean) : ProjectDataSettingsScreenEvent()
    data class OnShowTableDialog(val isOpen: Boolean) : ProjectDataSettingsScreenEvent()
    data class OnShowButtonsDialog(val isOpen: Boolean) : ProjectDataSettingsScreenEvent()
    data class OnShowNotificationDialog(val isOpen: Boolean) : ProjectDataSettingsScreenEvent()
    data class OnShowGraphDialog(val isOpen: Boolean) : ProjectDataSettingsScreenEvent()
    data class OnShowBackDialog(val isOpen: Boolean) : ProjectDataSettingsScreenEvent()
    data class OnShowXAxisDialog(val isOpen: Boolean, val hasSuccessfullyChosen: Boolean = false) :
        ProjectDataSettingsScreenEvent()

    data class OnShowMappingDialog(
        val isOpen: Boolean,
        val hasSuccessfullyChosen: Boolean = false
    ) : ProjectDataSettingsScreenEvent()

    data class OnShowGraphNameDialog(val isOpen: Boolean) : ProjectDataSettingsScreenEvent()

    object OnNavigateBack : ProjectDataSettingsScreenEvent()
    data class OnUserRemove(val user: User) : ProjectDataSettingsScreenEvent()

    data class OnChoseXAxis(val col: Int) : ProjectDataSettingsScreenEvent()
    data class OnChoseGraphType(val graphType: String) : ProjectDataSettingsScreenEvent()
    data class OnChoseMapping(val mapping: List<ColumnData>) : ProjectDataSettingsScreenEvent()
    data class OnChoseGraphName(val name: String) : ProjectDataSettingsScreenEvent()

    object OnLeaveProject : ProjectDataSettingsScreenEvent()
    object OnDeleteProject : ProjectDataSettingsScreenEvent()

}
