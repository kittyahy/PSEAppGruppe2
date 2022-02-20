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

package com.pseandroid2.dailydata.ui.project.data.graph

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.pseandroid2.dailydata.model.project.Project
import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Column
import com.pseandroid2.dailydata.model.graph.Graph
import com.pseandroid2.dailydata.util.ui.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProjectDataGraphScreenViewModel @Inject constructor(
    private val repository: RepositoryViewModelAPI
) : ViewModel() {

    lateinit var project: LiveData<Project>
    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    var table = listOf<Column>()
        private set

    var isGraphDialogOpen by mutableStateOf(false)
        private set
    var currentGraph by mutableStateOf<Graph<*, *>?>(null)
        private set

    fun initialize(projectId: Int) {
        viewModelScope.launch {
            repository.projectHandler.initializeProjectProvider(projectId)
        }
        viewModelScope.launch {
            project = repository.projectHandler.getProjectByID(projectId).asLiveData()
        }
    }

    fun onEvent(event: ProjectDataGraphScreenEvent) {
        when (event) {
            /*is ProjectDataGraphScreenEvent.OnCreate -> {
                viewModelScope.launch {
                    repository.projectHandler.initializeProjectProvider(event.projectId)
                }
                viewModelScope.launch {
                    repository.projectHandler.getProjectByID(event.projectId).collect {
                        graphs = it.graphs
                        table = it.table
                    }
                }
            }*/
            is ProjectDataGraphScreenEvent.OnShowGraphDialog -> {
                if (event.graph != null) {
                    isGraphDialogOpen = event.isOpen
                    currentGraph = event.graph
                } else {
                    isGraphDialogOpen = false
                }
            }
        }
    }
}