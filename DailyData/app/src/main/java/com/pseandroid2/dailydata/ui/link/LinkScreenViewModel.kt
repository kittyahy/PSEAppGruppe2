package com.pseandroid2.dailydata.ui.link

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.util.ui.Routes
import com.pseandroid2.dailydata.util.ui.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LinkScreenViewModel @Inject constructor(
    private val repository: RepositoryViewModelAPI
) :ViewModel() {

    @InternalCoroutinesApi
    fun onEvent(event : LinkScreenEvent) {
        when(event) {
            is LinkScreenEvent.OnButtonClick -> {
                repository.projectHandler.joinOnlineProject(onlineID = event.id)
            }
        }
    }
}