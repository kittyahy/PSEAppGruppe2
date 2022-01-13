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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pseandroid2.dailydata.util.ui.UiEvent
import com.pseandroid2.dailydata.di.Repository
import com.pseandroid2.dailydata.util.ui.Routes
import com.pseandroid2.dailydata.util.ui.TableButton
import com.pseandroid2.dailydata.util.ui.TableColumn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProjectCreationScreenViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    var title by mutableStateOf("")
        private set
    var description by mutableStateOf("")
        private set
    var wallpaper by mutableStateOf(Color.White)
        private set
    var table by mutableStateOf( listOf<TableColumn>() )
        private set
    var buttons by mutableStateOf( listOf<TableButton>() )
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
                mutable.add(TableColumn(id = id, name = event.name, unit = event.unit, dataType = event.dataType))
                table = mutable.toList()
            }
            is ProjectCreationEvent.OnTableRemove -> {
                var mutable = table.toMutableList()
                var removed = mutable.removeAt(index = event.index)
                var mutableButtons = buttons.toMutableList()
                buttons = mutableButtons.filter { it.column.id != removed.id}.toList()
                table = mutable.toList()
            }
            is ProjectCreationEvent.OnButtonAdd -> {
                var id = if (buttons.isEmpty()) {
                    0
                } else {
                    buttons.last().id + 1
                }
                var mutable = buttons.toMutableList()
                mutable.add(TableButton(id = id, name = event.name, column = table.find {event.columnId == it.id}!!, value = event.value))
                buttons = mutable.toList()
            }
            is ProjectCreationEvent.OnButtonRemove -> {
                var mutable = buttons.toMutableList()
                mutable.removeAt(index = event.index)
                buttons = mutable.toList()
            }

            is ProjectCreationEvent.OnSaveClick -> {
                TODO("Collect project data and send to repository")
                sendUiEvent(UiEvent.Navigate(Routes.DATA))
            }
            is ProjectCreationEvent.OnShowWallpaperDialog -> {
                isWallpaperDialogOpen = event.isOpen
            }
            is ProjectCreationEvent.OnShowTableDialog -> {
                isTableDialogOpen = event.isOpen
            }
            is ProjectCreationEvent.OnShowButtonsDialog -> {
                isButtonsDialogOpen = event.isOpen
            }
            is ProjectCreationEvent.OnShowNotificationDialog -> {
                isNotificationDialogOpen = event.isOpen
            }
            is ProjectCreationEvent.OnShowGraphDialog -> {
                isGraphDialogOpen = event.isOpen
            }
        }
    }

    private fun sendUiEvent(event : UiEvent) {
        viewModelScope.launch {
            _uiEvent.emit(event)
        }
    }
}