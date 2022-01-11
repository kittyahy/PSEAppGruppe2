package com.pseandroid2.dailydata.ui.project.overview

sealed class ProjectOverviewEvent {
    object OnNewProjectClick : ProjectOverviewEvent()
    data class OnProjectClick(val id : Int) : ProjectOverviewEvent()
}