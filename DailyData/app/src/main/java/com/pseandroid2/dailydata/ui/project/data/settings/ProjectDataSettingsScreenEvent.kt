package com.pseandroid2.dailydata.ui.project.data.settings

import androidx.compose.ui.graphics.Color
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.DataType
import com.pseandroid2.dailydata.model.graph.Graph
import com.pseandroid2.dailydata.model.graph.GraphType
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
    data class OnChoseMapping(val mapping: List<Int>) : ProjectDataSettingsScreenEvent()
    data class OnChoseGraphName(val name: String) : ProjectDataSettingsScreenEvent()

    object OnLeaveProject : ProjectDataSettingsScreenEvent()
    object OnDeleteProject : ProjectDataSettingsScreenEvent()

}
