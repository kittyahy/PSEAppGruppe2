package com.pseandroid2.dailydata.ui.server.templates

sealed class ServerTemplateScreenEvent {

    data class OnTemplateDownload(val id: Int) : ServerTemplateScreenEvent()
    data class OnGraphTemplateDownload(val projectId : Int, val graphId : Int) : ServerTemplateScreenEvent()

    data class OnShowDialog(val index: Int) : ServerTemplateScreenEvent()
    object OnCloseDialog : ServerTemplateScreenEvent()
}
