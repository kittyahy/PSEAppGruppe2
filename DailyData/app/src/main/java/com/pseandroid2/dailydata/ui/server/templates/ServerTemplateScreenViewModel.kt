package com.pseandroid2.dailydata.ui.server.templates

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.util.ui.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ServerTemplateScreenViewModel @Inject constructor(
    val repository : RepositoryViewModelAPI
) : ViewModel() {

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    var posts = repository.serverHandler.getPostPreviews()
        private set
    var isProjectTemplateDialogOpen by mutableStateOf(false)
        private set
    var dialogTemplateIndex by mutableStateOf(0 )
        private set

    fun onEvent(event : ServerTemplateScreenEvent) {
        when (event) {
            is ServerTemplateScreenEvent.OnGraphTemplateDownload -> {
                repository.serverHandler.downloadGraphTemplate(event.projectId, event.graphId)
                isProjectTemplateDialogOpen = false
                dialogTemplateIndex = 0
            }
            is ServerTemplateScreenEvent.OnCloseDialog -> {
                isProjectTemplateDialogOpen = false
            }
            is ServerTemplateScreenEvent.OnShowDialog -> {
                isProjectTemplateDialogOpen = true
                dialogTemplateIndex = event.index
            }
            is ServerTemplateScreenEvent.OnTemplateDownload -> {
                repository.serverHandler.downloadProjectTemplate(id = event.id)
            }
        }
    }

    private fun sendUiEvent(event : UiEvent) {
        viewModelScope.launch {
            _uiEvent.emit(event)
        }
    }
}