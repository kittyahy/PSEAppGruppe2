package com.pseandroid2.dailydata.ui.server.templates

sealed class ServerTemplateScreenEvent {

    data class OnPostDownload(val id: Int) : ServerTemplateScreenEvent()
    data class OnPostEntryDownload(val id : Int) : ServerTemplateScreenEvent()

    data class OnShowDialog(val index: Int) : ServerTemplateScreenEvent()
    object OnCloseDialog : ServerTemplateScreenEvent()
}
