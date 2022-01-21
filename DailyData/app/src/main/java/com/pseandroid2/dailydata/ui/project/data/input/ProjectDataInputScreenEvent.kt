package com.pseandroid2.dailydata.ui.project.data.input

sealed class ProjectDataInputScreenEvent {

    object OnDescriptionClick : ProjectDataInputScreenEvent()

    data class OnButtonClickInc(val id : Int) : ProjectDataInputScreenEvent()
    data class OnButtonClickDec(val id : Int) : ProjectDataInputScreenEvent()
    data class OnButtonClickAdd(val id : Int) : ProjectDataInputScreenEvent()

    data class OnColumnChange(val index : Int, val value : String) : ProjectDataInputScreenEvent()
    data class OnColumnInsertRowChange(val value : String) : ProjectDataInputScreenEvent()
    object OnColumnAdd : ProjectDataInputScreenEvent()

    data class OnRowDialogShow(val index : Int) : ProjectDataInputScreenEvent()
    object OnCloseRowDialog : ProjectDataInputScreenEvent()
    object OnRowModifyClick : ProjectDataInputScreenEvent()
    object OnRowDeleteClick : ProjectDataInputScreenEvent()

}
