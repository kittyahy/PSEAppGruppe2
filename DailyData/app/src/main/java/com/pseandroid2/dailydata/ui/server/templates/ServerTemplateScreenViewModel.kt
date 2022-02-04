package com.pseandroid2.dailydata.ui.server.templates

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Post
import com.pseandroid2.dailydata.util.ui.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ServerTemplateScreenViewModel @Inject constructor(
    val repository : RepositoryViewModelAPI
) : ViewModel() {

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    var posts = repository.serverHandler.getPostPreviews().toList()
        private set
    lateinit var post : Post
        private set
    var isProjectTemplateDialogOpen by mutableStateOf(false)
        private set

    fun onEvent(event : ServerTemplateScreenEvent) {
        when (event) {
            is ServerTemplateScreenEvent.OnShowDialog -> {
                post = repository.serverHandler.getPost(posts[event.index].id)
                isProjectTemplateDialogOpen = true
            }
            is ServerTemplateScreenEvent.OnCloseDialog -> {
                isProjectTemplateDialogOpen = false
            }

            is ServerTemplateScreenEvent.OnPostDownload -> {
                var post = repository.serverHandler.getPost(posts.find { it.id == event.id }!!.id)
                post.downloadProjectTemplate()
                for (i in 1 until post.postEntries.size) {
                    post.downloadGraphTemplate(i)
                }
            }

            is ServerTemplateScreenEvent.OnPostEntryDownload -> {
                var postTemp = repository.serverHandler.getPost(post.id)
                postTemp.downloadGraphTemplate(event.id)
            }
        }
    }

    private fun sendUiEvent(event : UiEvent) {
        viewModelScope.launch {
            _uiEvent.emit(event)
        }
    }
}