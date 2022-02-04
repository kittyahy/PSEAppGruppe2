package com.pseandroid2.dailydata.ui.link

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.util.ui.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LinkScreenViewModel @Inject constructor(
    private val repository: RepositoryViewModelAPI
) :ViewModel() {

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    @InternalCoroutinesApi
    fun onEvent(event : LinkScreenEvent) {
        when(event) {
            is LinkScreenEvent.OnButtonClick -> {
                viewModelScope.launch {
                    /* TODO()
                    if(repository.projectHandler.joinOnlineProjectIsPossible().first()) {
                        repository.projectHandler.joinOnlineProject(onlineID = event.id)
                    } else {
                        sendUiEvent(UiEvent.ShowToast("Could not join project"))
                    }

                     */
                }
            }
        }
    }

    private fun sendUiEvent(event : UiEvent) {
        viewModelScope.launch {
            _uiEvent.emit(event)
        }
    }
}