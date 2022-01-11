package com.pseandroid2.dailydata.ui.project.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pse.util.ui.UiEvent
import com.pseandroid2.dailydata.di.Repository
import com.pseandroid2.dailydata.util.ui.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProjectOverviewViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {

    //repository.getProjects()
    val projects : Flow<List<String>> = flow {
        emptyList<String>()
    }

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun onEvent(event: ProjectOverviewEvent) {
        when(event) {
            is ProjectOverviewEvent.OnNewProjectClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.CREATION))
            }
            is ProjectOverviewEvent.OnProjectClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.DATA + "?projectId=${event.id}" ))
            }
        }
    }

    private fun sendUiEvent(event : UiEvent) {
        viewModelScope.launch {
            _uiEvent.emit(event)
        }
    }

}