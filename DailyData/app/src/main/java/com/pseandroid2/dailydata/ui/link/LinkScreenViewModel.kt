package com.pseandroid2.dailydata.ui.link

import androidx.lifecycle.ViewModel
import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.InternalCoroutinesApi
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