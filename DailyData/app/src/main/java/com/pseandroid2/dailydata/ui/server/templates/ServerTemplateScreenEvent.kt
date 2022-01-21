package com.pseandroid2.dailydata.ui.server.templates

import com.pseandroid2.dailydata.ui.server.ServerScreenEvent

sealed class ServerTemplateScreenEvent {

    data class OnTabChange(val index : Int) : ServerTemplateScreenEvent()
    data class OnGraphTemplateDownload(val index: Int) : ServerTemplateScreenEvent()
    data class OnProjectTemplateDownload(val index: Int) : ServerTemplateScreenEvent()
    data class OnProjectGraphTemplateDownload(val graphIndex : Int) : ServerTemplateScreenEvent()

    data class OnShowProjectTemplateDialog(val index: Int, val isOpen : Boolean) : ServerTemplateScreenEvent()
}
