package com.pseandroid2.dailydata.ui.project.data.settings

import androidx.compose.ui.graphics.Color
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.DataType
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Graph
import java.time.LocalTime

sealed class ProjectDataSettingsScreenEvent {

    data class OnCreate(val projectId: Int) : ProjectDataSettingsScreenEvent()

    object OnCreateLink : ProjectDataSettingsScreenEvent()

    data class OnTitleChange(val title: String) : ProjectDataSettingsScreenEvent()
    data class OnDescriptionChange(val description: String) : ProjectDataSettingsScreenEvent()
    data class OnWallpaperChange(val wallpaper: Color) : ProjectDataSettingsScreenEvent()
    data class OnTableAdd(val name: String, val unit: String, val dataType: DataType) :
        ProjectDataSettingsScreenEvent()

    data class OnTableRemove(val index: Int) : ProjectDataSettingsScreenEvent()
    data class OnButtonAdd(val name: String, val columnId: Int, val value: Int) :
        ProjectDataSettingsScreenEvent()

    data class OnButtonRemove(val id: Int, val columnId: Int) : ProjectDataSettingsScreenEvent()
    data class OnNotificationAdd(val message: String, val time: LocalTime) :
        ProjectDataSettingsScreenEvent()

    data class OnNotificationRemove(val index: Int) : ProjectDataSettingsScreenEvent()
    data class OnGraphAdd(val graph: Graph) : ProjectDataSettingsScreenEvent()
    data class OnGraphRemove(val index: Int) : ProjectDataSettingsScreenEvent()
    object OnSaveClick : ProjectDataSettingsScreenEvent()

    //dialogs
    data class OnShowWallpaperDialog(val isOpen: Boolean) : ProjectDataSettingsScreenEvent()
    data class OnShowTableDialog(val isOpen: Boolean) : ProjectDataSettingsScreenEvent()
    data class OnShowButtonsDialog(val isOpen: Boolean) : ProjectDataSettingsScreenEvent()
    data class OnShowNotificationDialog(val isOpen: Boolean) : ProjectDataSettingsScreenEvent()
    data class OnShowGraphDialog(val isOpen: Boolean) : ProjectDataSettingsScreenEvent()

    data class OnShowBackDialog(val isOpen: Boolean) : ProjectDataSettingsScreenEvent()

    object OnNavigateBack : ProjectDataSettingsScreenEvent()

    data class OnMemberRemove(val index: Int) : ProjectDataSettingsScreenEvent()
    object OnLeaveProject : ProjectDataSettingsScreenEvent()
    object OnDeleteProject : ProjectDataSettingsScreenEvent()

}
