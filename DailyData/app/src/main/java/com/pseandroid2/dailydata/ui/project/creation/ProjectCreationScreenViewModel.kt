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
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pseandroid2.dailydata.model.notifications.TimeNotification
import com.pseandroid2.dailydata.model.project.InMemoryProject
import com.pseandroid2.dailydata.model.table.ColumnData
import com.pseandroid2.dailydata.model.uielements.UIElement
import com.pseandroid2.dailydata.model.uielements.UIElementType
import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.DataType
import com.pseandroid2.dailydata.util.ui.UiEvent
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

    var project by mutableStateOf(InMemoryProject(0))
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
        assert(-1 != savedStateHandle.get<Int>("projectTemplateId")!!)
    }

    @InternalCoroutinesApi
    fun onEvent(event: ProjectCreationEvent) {
        when (event) {
            is ProjectCreationEvent.OnTitleChange -> {
                viewModelScope.launch { project.setName(event.title) }
            }
            is ProjectCreationEvent.OnDescriptionChange -> {
                viewModelScope.launch { project.setDesc(event.description) }
            }
            is ProjectCreationEvent.OnWallpaperChange -> {
                viewModelScope.launch { project.setColor(event.wallpaper.toArgb()) }
            }
            is ProjectCreationEvent.OnTableAdd -> {
                viewModelScope.launch {
                    project.table.addColumn(
                        ColumnData(
                            type = event.dataType,
                            name = event.name,
                            unit = event.unit
                        )
                    )
                }
            }
            is ProjectCreationEvent.OnTableRemove -> {//TODO Rename
                viewModelScope.launch { project.table.deleteColumn(event.index) }

            }
            is ProjectCreationEvent.OnButtonAdd -> {
                viewModelScope.launch {
                    project.table.addUIElement(
                        event.columnId,
                        UIElement(
                            name = event.name,
                            type = UIElementType.BUTTON,
                            state = event.value.toString()
                        )
                    )
                }
            }
            is ProjectCreationEvent.OnButtonRemove -> {
                viewModelScope.launch {
                    project.table.removeUIElement(event.columnID, event.id)
                }
            }
            is ProjectCreationEvent.OnNotificationAdd -> {
                viewModelScope.launch {
                    project.addNotification(
                        TimeNotification(
                            initId = 0,
                            messageString = event.message,
                            send = event.time
                        )
                    )
                }
            }
            is ProjectCreationEvent.OnNotificationRemove -> {
                viewModelScope.launch { project.removeNotification(event.index) }
            }
            is ProjectCreationEvent.OnGraphAdd -> {
                TODO("CacheOnlyProject AddGraph")
            }
            is ProjectCreationEvent.OnGraphRemove -> {
                TODO("CacheOnlyProject RemoveGraph")
            }
            is ProjectCreationEvent.OnSaveClick -> {
                val default = InMemoryProject(0)
                when {
                    default.name == project.name -> sendUiEvent(UiEvent.ShowToast("Please Enter a title"))
                    project.table.layout.size == 0 -> sendUiEvent(UiEvent.ShowToast("Please Enter a column"))
                    else -> {
                        viewModelScope.launch {
                            project.writeToDBAsync(repository.projectHandler)
                        }
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
                if (event.isOpen && project.table.layout.none { it.type == DataType.WHOLE_NUMBER }) {
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

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.emit(event)
        }
    }
}