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

    var isDialogOpen by mutableStateOf(false)
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
                    isDialogOpen = event.isOpen
                    currentGraph = event.graph
                } else {
                    isDialogOpen = false
                }
            }
        }
    }
}