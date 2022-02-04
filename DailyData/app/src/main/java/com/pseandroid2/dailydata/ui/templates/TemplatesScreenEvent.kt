package com.pseandroid2.dailydata.ui.templates

import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.GraphTemplate
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.ProjectTemplate

sealed class TemplatesScreenEvent {

    data class OnTabChange(val index : Int) : TemplatesScreenEvent()
    data class OnGraphTemplateDelete(val template : GraphTemplate) : TemplatesScreenEvent()
    data class OnProjectTemplateDelete(val template: ProjectTemplate) : TemplatesScreenEvent()

}