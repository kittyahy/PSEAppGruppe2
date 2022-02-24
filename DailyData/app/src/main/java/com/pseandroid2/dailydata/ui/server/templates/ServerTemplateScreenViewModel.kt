package com.pseandroid2.dailydata.ui.server.templates

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Post
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.PostPreview
import com.pseandroid2.dailydata.util.ui.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ServerTemplateScreenViewModel @Inject constructor(
    val repository: RepositoryViewModelAPI
) : ViewModel() {

    var posts = listOf<PostPreview>()
        private set
    lateinit var post: Post
        private set
    var isProjectTemplateDialogOpen by mutableStateOf(false)
        private set

    init {
        viewModelScope.launch {
            posts = repository.serverHandler.getPostPreviews().toList()
        }
    }

    fun onEvent(event: ServerTemplateScreenEvent) {
        when (event) {
            is ServerTemplateScreenEvent.OnShowDialog -> {
                viewModelScope.launch {
                    post = repository.serverHandler.getPost(posts[event.index].id)
                }
                isProjectTemplateDialogOpen = true
            }
            is ServerTemplateScreenEvent.OnCloseDialog -> {
                isProjectTemplateDialogOpen = false
            }

            is ServerTemplateScreenEvent.OnPostDownload -> {
                viewModelScope.launch {
                    val post = repository.serverHandler.getPost(posts.find { it.id == event.id }!!.id)
                    post.downloadProjectTemplate()
                    for (i in 1 until post.postEntries.size) {
                        post.downloadGraphTemplate(i)
                    }
                }

            }

            is ServerTemplateScreenEvent.OnPostEntryDownload -> {
                viewModelScope.launch {
                    val postTemp = repository.serverHandler.getPost(post.id)
                    postTemp.downloadGraphTemplate(event.id)
                }

            }
        }
    }
}