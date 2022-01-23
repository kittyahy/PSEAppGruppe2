package com.pseandroid2.dailydata.ui.project.data.graph

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pseandroid2.dailydata.util.ui.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProjectDataGraphScreenViewModel @Inject constructor() : ViewModel() {

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    var graphs by mutableStateOf( listOf<GraphLol>())
        private set
    var isDialogOpen by mutableStateOf(false)
        private set
    var dialogIndex by mutableStateOf(-1)
        private set

    fun onEvent(event : ProjectDataGraphScreenEvent) {
        when (event) {
            is ProjectDataGraphScreenEvent.OnShowGraphDialog -> {

            }
        }
    }

    private fun sendUiEvent(event : UiEvent) {
        viewModelScope.launch {
            _uiEvent.emit(event)
        }
    }
}

data class GraphLol(val image : Int)