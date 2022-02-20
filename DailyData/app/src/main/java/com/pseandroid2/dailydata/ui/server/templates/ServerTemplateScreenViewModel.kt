/*

    DailyData is an android app to easily create diagrams from data one has collected
    Copyright (C) 2022  Antonia Heiming, Anton Kadelbach, Arne Kuchenbecker, Merlin Opp, Robin Amman

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.

*/

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

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

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
                    var post =
                        repository.serverHandler.getPost(posts.find { it.id == event.id }!!.id)
                    post.downloadProjectTemplate()
                    for (i in 1 until post.postEntries.size) {
                        post.downloadGraphTemplate(i)
                    }
                }

            }

            is ServerTemplateScreenEvent.OnPostEntryDownload -> {
                viewModelScope.launch {
                    var postTemp = repository.serverHandler.getPost(post.id)
                    postTemp.downloadGraphTemplate(event.id)
                }

            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.emit(event)
        }
    }
}