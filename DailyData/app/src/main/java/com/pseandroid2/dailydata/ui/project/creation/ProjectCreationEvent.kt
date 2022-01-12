package com.pseandroid2.dailydata.ui.project.creation

import androidx.compose.ui.graphics.Color
import com.pseandroid2.dailydata.util.ui.DataType

sealed class ProjectCreationEvent {

    data class OnTitleChange(val title : String) : ProjectCreationEvent()
    data class OnDescriptionChange(val description : String) : ProjectCreationEvent()
    data class OnWallpaperChange(val wallpaper : Color) : ProjectCreationEvent()
    data class OnTableAdd(val name : String, val unit : String, val dataType: DataType) : ProjectCreationEvent()
    data class OnTableRemove(val index : Int) : ProjectCreationEvent()
    data class OnButtonAdd(val name : String, val columnId : Int, val value: Int) : ProjectCreationEvent()
    data class OnButtonRemove(val index : Int) : ProjectCreationEvent()
    object OnSaveClick : ProjectCreationEvent()

    //dialogs
    data class OnShowWallpaperDialog(val isOpen : Boolean) : ProjectCreationEvent()
    data class OnShowTableDialog(val isOpen : Boolean) : ProjectCreationEvent()
    data class OnShowButtonsDialog(val isOpen : Boolean) : ProjectCreationEvent()
    data class OnShowNotificationDialog(val isOpen : Boolean) : ProjectCreationEvent()
    data class OnShowGraphDialog(val isOpen : Boolean) : ProjectCreationEvent()

}
