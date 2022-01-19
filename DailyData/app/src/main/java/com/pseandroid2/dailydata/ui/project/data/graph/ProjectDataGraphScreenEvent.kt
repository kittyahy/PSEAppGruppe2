package com.pseandroid2.dailydata.ui.project.data.graph

sealed class ProjectDataGraphScreenEvent {

    data class OnShowGraphDialog(val index: Int) : ProjectDataGraphScreenEvent()
    object OnCloseGraphDialog : ProjectDataGraphScreenEvent()
}
