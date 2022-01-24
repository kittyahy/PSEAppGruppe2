/*

    DailyData is an android app to easily create diagrams from data one has collected
    Copyright (C) 2022  Antonia Heiming, Anton Kadelbach, Arne Kuchenbecker, Merlin Opp, Robin Amman

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.

*/

package com.pseandroid2.dailydata.ui.templates

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pseandroid2.dailydata.R
import com.pseandroid2.dailydata.model.Graph
import com.pseandroid2.dailydata.model.Project
import com.pseandroid2.dailydata.util.ui.GraphTemplate
import com.pseandroid2.dailydata.util.ui.ProjectTemplate
import com.pseandroid2.dailydata.util.ui.TableButton
import com.pseandroid2.dailydata.util.ui.TableColumn
import com.pseandroid2.dailydata.util.ui.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TemplatesScreenViewModel @Inject constructor() : ViewModel() {

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    var tabs by mutableStateOf( listOf<TemplateTabs>())
        private set
    var tab by mutableStateOf(0)
        private set
    var graphTemplates by mutableStateOf( listOf<GraphTemplate>() )
        private set
    var projectTemplates by mutableStateOf( listOf<ProjectTemplate>() )
        private set

    init {
        tabs = TemplateTabs.values().toList()
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

    fun onEvent(event : TemplatesScreenEvent) {
        when (event) {
            is TemplatesScreenEvent.OnTabChange -> {
                tab = event.index
            }
            is TemplatesScreenEvent.OnGraphTemplateDelete -> {
                var mutable = graphTemplates.toMutableList()
                mutable.removeAt(index = event.index)
                graphTemplates = mutable.toList()
            }
            is TemplatesScreenEvent.OnProjectTemplateDelete -> {
                var mutable = projectTemplates.toMutableList()
                mutable.removeAt(index = event.index)
                projectTemplates = mutable.toList()
            }
        }
    }

    private fun sendUiEvent(event : UiEvent) {
        viewModelScope.launch {
            _uiEvent.emit(event)
        }
    }
}

enum class TemplateTabs(val representation : String) {
    GRAPHS("Graphs"), PROJECTS("Projects")
}