package com.pseandroid2.dailydata.ui.project.data

sealed class ProjectDataScreenEvent {

    data class OnTabChange(val index : Int) : ProjectDataScreenEvent()

}
