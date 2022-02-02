package com.pseandroid2.dailydata.ui.project.data.graph

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Column
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Graph
import com.pseandroid2.dailydata.util.ui.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProjectDataGraphScreenViewModel @Inject constructor(
    private val repository: RepositoryViewModelAPI
    ) : ViewModel() {

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    var graphs by mutableStateOf( listOf<Graph>() )
        private set
    var table = listOf<Column>()
        private set

    var isDialogOpen by mutableStateOf(false)
        private set
    var dialogIndex by mutableStateOf(-1)
        private set


    @InternalCoroutinesApi
    fun onEvent(event : ProjectDataGraphScreenEvent) {
        when (event) {
            is ProjectDataGraphScreenEvent.OnCreate -> {
                viewModelScope.launch {
                    repository.projectHandler.getProjectByID(event.projectId).collect {
                        graphs = it.graphs
                        table = it.table
                    }
                }
            }
            is ProjectDataGraphScreenEvent.OnShowGraphDialog -> {
                isDialogOpen = true
                dialogIndex = event.index
            }
            is ProjectDataGraphScreenEvent.OnCloseGraphDialog -> {
                isDialogOpen = false
            }
        }
    }

    private fun sendUiEvent(event : UiEvent) {
        viewModelScope.launch {
            _uiEvent.emit(event)
        }
    }
}