package com.pseandroid2.dailydata.ui.templates

sealed class TemplatesScreenEvent {

    data class OnTabChange(val index : Int) : TemplatesScreenEvent()
    data class OnGraphTemplateDelete(val index: Int) : TemplatesScreenEvent()
    data class OnProjectTemplateDelete(val index: Int) : TemplatesScreenEvent()

}