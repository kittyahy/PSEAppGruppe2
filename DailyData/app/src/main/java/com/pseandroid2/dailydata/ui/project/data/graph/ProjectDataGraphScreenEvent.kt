package com.pseandroid2.dailydata.ui.project.data.graph

import com.pseandroid2.dailydata.model.graph.Graph

sealed class ProjectDataGraphScreenEvent {

    data class OnCreate(val projectId: Int) : ProjectDataGraphScreenEvent()
    data class OnShowGraphDialog(val isOpen: Boolean, val graph: Graph<*, *>? = null) : ProjectDataGraphScreenEvent()
}
