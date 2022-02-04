package com.pseandroid2.dailydata.ui.templates

import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.GraphTemplate
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.ProjectTemplatePreview

sealed class TemplatesScreenEvent {

    data class OnTabChange(val index : Int) : TemplatesScreenEvent()
    data class OnDeleteGraphTemplate(val template : GraphTemplate) : TemplatesScreenEvent()
    data class OnDeleteProjectTemplate(val template: ProjectTemplatePreview) : TemplatesScreenEvent()

}