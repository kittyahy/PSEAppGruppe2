package com.pseandroid2.dailydata.ui.project.data.settings

import androidx.compose.ui.graphics.Color
import com.pseandroid2.dailydata.util.ui.DataType
import com.pseandroid2.dailydata.util.ui.Graphs

sealed class ProjectDataSettingsScreenEvent {

    data class OnTitleChange(val title : String) : ProjectDataSettingsScreenEvent()
    data class OnDescriptionChange(val description : String) : ProjectDataSettingsScreenEvent()
    data class OnWallpaperChange(val wallpaper : Color) : ProjectDataSettingsScreenEvent()
    data class OnTableAdd(val name : String, val unit : String, val dataType: DataType) : ProjectDataSettingsScreenEvent()
    data class OnTableRemove(val index : Int) : ProjectDataSettingsScreenEvent()
    data class OnButtonAdd(val name : String, val columnId : Int, val value: Int) : ProjectDataSettingsScreenEvent()
    data class OnButtonRemove(val index : Int) : ProjectDataSettingsScreenEvent()
    /*
     TODO : change time from string to meaningful
      */
    data class OnNotificationAdd(val message : String, val time : String) : ProjectDataSettingsScreenEvent()
    data class OnNotificationRemove(val index : Int) : ProjectDataSettingsScreenEvent()
    data class OnGraphAdd(val graph: Graphs) : ProjectDataSettingsScreenEvent()
    data class OnGraphRemove(val index : Int) : ProjectDataSettingsScreenEvent()
    object OnSaveClick : ProjectDataSettingsScreenEvent()

    //dialogs
    data class OnShowWallpaperDialog(val isOpen : Boolean) : ProjectDataSettingsScreenEvent()
    data class OnShowTableDialog(val isOpen : Boolean) : ProjectDataSettingsScreenEvent()
    data class OnShowButtonsDialog(val isOpen : Boolean) : ProjectDataSettingsScreenEvent()
    data class OnShowNotificationDialog(val isOpen : Boolean) : ProjectDataSettingsScreenEvent()
    data class OnShowGraphDialog(val isOpen : Boolean) : ProjectDataSettingsScreenEvent()

    data class OnShowBackDialog(val isOpen : Boolean) : ProjectDataSettingsScreenEvent()

    object OnNavigateBack : ProjectDataSettingsScreenEvent()

    data class OnChangeEditMember(val name : String) : ProjectDataSettingsScreenEvent()
    object OnAddMember : ProjectDataSettingsScreenEvent()
    data class OnShowMemberDialog(val isOpen : Boolean) : ProjectDataSettingsScreenEvent()
    data class OnMemberRemove(val index : Int) : ProjectDataSettingsScreenEvent()
    object OnLeaveProject : ProjectDataSettingsScreenEvent()
    object OnDeleteProject : ProjectDataSettingsScreenEvent()

}
