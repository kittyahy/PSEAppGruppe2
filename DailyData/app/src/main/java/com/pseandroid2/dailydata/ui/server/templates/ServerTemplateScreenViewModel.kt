package com.pseandroid2.dailydata.ui.server.templates

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pseandroid2.dailydata.R
import com.pseandroid2.dailydata.util.ui.GraphTemplate
import com.pseandroid2.dailydata.util.ui.ProjectTemplate
import com.pseandroid2.dailydata.util.ui.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ServerTemplateScreenViewModel @Inject constructor() : ViewModel() {

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    var tabs by mutableStateOf( listOf<ServerTabs>())
        private set
    var tab by mutableStateOf(0)
        private set
    var graphTemplates by mutableStateOf( listOf<GraphTemplate>() )
        private set
    var projectTemplates by mutableStateOf( listOf<ProjectTemplate>() )
        private set
    var isProjectTemplateDialogOpen by mutableStateOf(false)
        private set
    var projectTemplateIndex by mutableStateOf(0)
        private set

    init {
        tabs = ServerTabs.values().toList()
        graphTemplates = listOf(
            GraphTemplate(title = "Title 1", image = R.drawable.chart),
            GraphTemplate(title = "Title 2", image = R.drawable.chart),
            GraphTemplate(title = "Title 3", image = R.drawable.chart),
            GraphTemplate(title = "Title 4", image = R.drawable.chart),
            GraphTemplate(title = "Title 5", image = R.drawable.chart),
            GraphTemplate(title = "Title 6", image = R.drawable.chart),
            GraphTemplate(title = "Title 7", image = R.drawable.chart),
            GraphTemplate(title = "Title 8", image = R.drawable.chart),
            GraphTemplate(title = "Title 9", image = R.drawable.chart),
            GraphTemplate(title = "Title 10", image = R.drawable.chart),
            GraphTemplate(title = "Title 11", image = R.drawable.chart),
            GraphTemplate(title = "Title 12", image = R.drawable.chart),
            GraphTemplate(title = "Title 13", image = R.drawable.chart),
            GraphTemplate(title = "Title 14", image = R.drawable.chart),
            GraphTemplate(title = "Title 15", image = R.drawable.chart),
            GraphTemplate(title = "Title 16", image = R.drawable.chart),
        )
        projectTemplates = listOf(
            ProjectTemplate(title = "Title 1", image = R.drawable.chart, graphTemplates = listOf(
                GraphTemplate(title = "Title 1", image = R.drawable.chart),
                GraphTemplate(title = "Title 2", image = R.drawable.chart)
            )),
            ProjectTemplate(title = "Title 2", image = R.drawable.chart, graphTemplates = listOf(
                GraphTemplate(title = "Title 1", image = R.drawable.chart),
                GraphTemplate(title = "Title 2", image = R.drawable.chart),
                GraphTemplate(title = "Title 3", image = R.drawable.chart)
            )),
            ProjectTemplate(title = "Title 3", image = R.drawable.chart, graphTemplates = listOf(
                GraphTemplate(title = "Title 1", image = R.drawable.chart)
            )),
            ProjectTemplate(title = "Title 4", image = R.drawable.chart, graphTemplates = listOf(
                GraphTemplate(title = "Title 1", image = R.drawable.chart),
                GraphTemplate(title = "Title 2", image = R.drawable.chart),
                GraphTemplate(title = "Title 3", image = R.drawable.chart)
            )),
            ProjectTemplate(title = "Title 5", image = R.drawable.chart, graphTemplates = listOf(
                GraphTemplate(title = "Title 1", image = R.drawable.chart),
                GraphTemplate(title = "Title 3", image = R.drawable.chart)
            ))
        )
    }
    fun onEvent(event : ServerTemplateScreenEvent) {
        when (event) {
            is ServerTemplateScreenEvent.OnTabChange -> {
                tab = event.index
            }
            is ServerTemplateScreenEvent.OnGraphTemplateDownload -> {
                var mutable = graphTemplates.toMutableList()
                mutable.removeAt(index = event.index)
                graphTemplates = mutable.toList()
            }
            is ServerTemplateScreenEvent.OnProjectTemplateDownload -> {
                var mutable = projectTemplates.toMutableList()
                mutable.removeAt(index = event.index)
                projectTemplates = mutable.toList()
            }
            is ServerTemplateScreenEvent.OnShowProjectTemplateDialog -> {
                isProjectTemplateDialogOpen = event.isOpen
                projectTemplateIndex = event.index
            }
        }
    }

    private fun sendUiEvent(event : UiEvent) {
        viewModelScope.launch {
            _uiEvent.emit(event)
        }
    }
}

enum class ServerTabs(val representation : String) {
    GRAPHS("Graphs"), PROJECTS("Projects")
}