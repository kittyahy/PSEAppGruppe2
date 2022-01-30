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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.GraphTemplate
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.ProjectTemplate
import com.pseandroid2.dailydata.util.ui.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@InternalCoroutinesApi
@HiltViewModel
class TemplatesScreenViewModel @Inject constructor(
    val repository: RepositoryViewModelAPI
) : ViewModel() {

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    var tabs by mutableStateOf( listOf<TemplateTabs>())
        private set
    var tab by mutableStateOf(0)
        private set

    var graphTemplates = repository.projectHandler.graphTemplateFlow
        private set
    var projectTemplates = repository.projectHandler.projectTemplateFlow
        private set

    init {
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