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

package com.pseandroid2.dailydata.ui.project.creation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Button
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Column
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.DataType
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Graph
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Notification
import com.pseandroid2.dailydata.util.ui.UiEvent
import com.pseandroid2.dailydata.util.ui.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProjectCreationScreenViewModel @Inject constructor(
    private val repository: RepositoryViewModelAPI,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    var title by mutableStateOf("")
        private set
    var description by mutableStateOf("")
        private set
    var wallpaper by mutableStateOf(Color.White)
        private set
    var table by mutableStateOf( listOf<Column>() )
        private set
    var buttons by mutableStateOf( listOf<Button>() )
        private set
    var notifications by mutableStateOf( listOf<Notification>() )
        private set
    var graphs by mutableStateOf( listOf<Graph>() )
        private set

    var isWallpaperDialogOpen by mutableStateOf(false)
        private set
    var isTableDialogOpen by mutableStateOf(false)
        private set
    var isButtonsDialogOpen by mutableStateOf(false)
        private set
    var isNotificationDialogOpen by mutableStateOf(false)
        private set
    var isGraphDialogOpen by mutableStateOf(false)
        private set

    var isBackDialogOpen by mutableStateOf(false)
        private set

    init {
        val id = savedStateHandle.get<Int>("projectTemplateId")!!
        if(id != -1) {
            viewModelScope.launch {
                var template = repository.serverHandler.getProjectTemplate(id = id)
                //TODO(get values of template)
            }
        }
    }

    @InternalCoroutinesApi
    fun onEvent(event: ProjectCreationEvent) {
        when (event) {
            is ProjectCreationEvent.OnTitleChange -> {
                title = event.title
            }
            is ProjectCreationEvent.OnDescriptionChange -> {
                description = event.description
            }
            is ProjectCreationEvent.OnWallpaperChange -> {
                wallpaper = event.wallpaper
            }
            is ProjectCreationEvent.OnTableAdd -> {
                var id = if (table.isEmpty()) {
                    0
                } else {
                    table.last().id + 1
                }
                var mutable = table.toMutableList()
                mutable.add(Column(id = id, name = event.name, unit = event.unit, dataType = event.dataType))
                table = mutable.toList()
            }
            is ProjectCreationEvent.OnTableRemove -> {
                var mutable = table.toMutableList()
                var removed = mutable.removeAt(index = event.index)
                var mutableButtons = buttons.toMutableList()
                buttons = mutableButtons.filter { it.columnId != removed.id}.toList()
                table = mutable.toList()
            }
            is ProjectCreationEvent.OnButtonAdd -> {
                var id = if (buttons.isEmpty()) {
                    0
                } else {
                    buttons.last().id + 1
                }
                var mutable = buttons.toMutableList()
                mutable.add(Button(id = id, name = event.name, columnId = table.find {event.columnId == it.id}!!.id, value = event.value))
                buttons = mutable.toList()
            }
            is ProjectCreationEvent.OnButtonRemove -> {
                var mutable = buttons.toMutableList()
                mutable.removeAt(index = event.index)
                buttons = mutable.toList()
            }
            is ProjectCreationEvent.OnNotificationAdd -> {
                var mutable = notifications.toMutableList()
                mutable.add(Notification(id = 0, message = event.message, time = event.time, notificationsDAO = TODO("Anton das ist Müll"), projectID = 0))
                notifications = mutable.toList()
            }
            is ProjectCreationEvent.OnNotificationRemove -> {
                var mutable = notifications.toMutableList()
                mutable.removeAt(index = event.index)
                notifications = mutable.toList()
            }
            is ProjectCreationEvent.OnGraphAdd -> {
                var mutable = graphs.toMutableList()
                mutable.add(event.graph)
                graphs = mutable.toList()
            }
            is ProjectCreationEvent.OnGraphRemove -> {
                var mutable = graphs.toMutableList()
                mutable.removeAt(index = event.index)
                graphs = mutable.toList()
            }
            is ProjectCreationEvent.OnSaveClick -> {
                when {
                    title.isBlank() -> sendUiEvent(UiEvent.ShowToast("Please Enter a title"))
                    table.isEmpty() -> sendUiEvent(UiEvent.ShowToast("Please Enter a column"))
                    else            -> {
                        var newProject = repository.projectHandler.newProject(
                            name = title,
                            description = description,
                            wallpaper = wallpaper.hashCode(),
                            table = table,
                            buttons = buttons,
                            notification = notifications,
                            graphs = graphs
                        )
                        sendUiEvent(UiEvent.PopBackStack)
                        sendUiEvent(UiEvent.Navigate(Routes.DATA + "?projectId=${newProject.id}"))
                    }
                }
            }

            is ProjectCreationEvent.OnShowWallpaperDialog -> {
                isWallpaperDialogOpen = event.isOpen
            }
            is ProjectCreationEvent.OnShowTableDialog -> {
                isTableDialogOpen = event.isOpen
            }
            is ProjectCreationEvent.OnShowButtonsDialog -> {
                if(event.isOpen && table.none { it.dataType == DataType.WHOLE_NUMBER }) {
                    sendUiEvent(UiEvent.ShowToast("Please Enter a compatible column first"))
                } else {
                    isButtonsDialogOpen = event.isOpen
                }
            }
            is ProjectCreationEvent.OnShowNotificationDialog -> {
                isNotificationDialogOpen = event.isOpen
            }
            is ProjectCreationEvent.OnShowGraphDialog -> {
                isGraphDialogOpen = event.isOpen
            }
            is ProjectCreationEvent.OnShowBackDialog -> {
                isBackDialogOpen = event.isOpen
            }
            is ProjectCreationEvent.OnNavigateBack -> {
                sendUiEvent(UiEvent.PopBackStack)
            }
        }
    }

    private fun sendUiEvent(event : UiEvent) {
        viewModelScope.launch {
            _uiEvent.emit(event)
        }
    }
}