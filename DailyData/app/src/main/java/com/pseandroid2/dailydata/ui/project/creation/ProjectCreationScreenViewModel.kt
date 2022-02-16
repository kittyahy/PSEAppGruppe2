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

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pseandroid2.dailydata.model.graph.Graph
import com.pseandroid2.dailydata.model.notifications.Notification
import com.pseandroid2.dailydata.model.notifications.TimeNotification
import com.pseandroid2.dailydata.model.table.ArrayListTable
import com.pseandroid2.dailydata.model.table.ColumnData
import com.pseandroid2.dailydata.model.table.Table
import com.pseandroid2.dailydata.model.uielements.UIElement
import com.pseandroid2.dailydata.model.uielements.UIElementType
import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.DataType
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.ViewModelProject
import com.pseandroid2.dailydata.ui.navigation.Routes
import com.pseandroid2.dailydata.util.Consts.LOG_TAG
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

    var title by mutableStateOf("")
        private set
    var description by mutableStateOf("")
        private set
    var wallpaper by mutableStateOf(Color.White)
        private set
    var table by mutableStateOf<Table>(ArrayListTable())
        private set
    var uIElements by mutableStateOf(listOf<UIElement>())
        private set
    var notifications by mutableStateOf(listOf<Notification>())
        private set
    var graphs by mutableStateOf(listOf<Graph<*, *>>())
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
        if (id != -1) {
            viewModelScope.launch {
                val template = repository.serverHandler.getProjectTemplateById(id = id).toProject()
                title = template.name
                description = template.desc
                wallpaper = Color(template.color)
                table = template.table
                val list: List<UIElement> = ArrayList()
                template.table.layout.forEach { columnData ->
                    template.table.layout.getUIElements(
                        columnData.id
                    )
                }
                uIElements = list
                notifications = template.notifications
                graphs = template.graphs
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
                viewModelScope.launch {
                    table.addColumn(
                        ColumnData(
                            type = event.dataType,
                            name = event.name,
                            unit = event.unit
                        )
                    )
                }
            }
            is ProjectCreationEvent.OnTableRemove -> {
                viewModelScope.launch { table.deleteColumn(event.index) }

            }
            is ProjectCreationEvent.OnButtonAdd -> {
                viewModelScope.launch {
                    table.addUIElement(
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
                    table.removeUIElement(event.columnID, event.id)
                }
            }
            is ProjectCreationEvent.OnNotificationAdd -> {
                val mutable = notifications.toMutableList()
                mutable.add(
                    TimeNotification(
                        initId = 0,
                        messageString = event.message,
                        send = event.time
                    )
                )
                notifications = mutable.toList()
            }
            is ProjectCreationEvent.OnNotificationRemove -> {
                val mutable = notifications.toMutableList()
                mutable.removeAt(index = event.index)
                notifications = mutable.toList()
            }
            is ProjectCreationEvent.OnGraphAdd -> {
                val mutable = graphs.toMutableList()
                mutable.add(event.graph)
                graphs = mutable.toList()
            }
            is ProjectCreationEvent.OnGraphRemove -> {
                val mutable = graphs.toMutableList()
                mutable.removeAt(index = event.index)
                graphs = mutable.toList()
            }
            is ProjectCreationEvent.OnSaveClick -> {
                when {
                    title.isBlank() -> sendUiEvent(UiEvent.ShowToast("Please Enter a title"))
                    table.layout.count() == 0 -> sendUiEvent(UiEvent.ShowToast("Please Enter a column"))
                    else -> {
                        viewModelScope.launch {
                            val newProject = repository.projectHandler.newProjectAsync(

                                ViewModelProject(
                                    name = title,
                                    desc = description,
                                    color = wallpaper.toArgb(),
                                    table = table,
                                    notifications = notifications.toMutableList(),
                                    graphs = graphs.toMutableList(),
                                    admin = repository.remoteDataSourceAPI.getUser(),
                                    repositoryViewModelAPI = repository
                                )

                            )
                            val id = newProject.await()
                            Log.d(LOG_TAG, "Test: after new Project $id")
                            sendUiEvent(UiEvent.PopBackStack)
                            sendUiEvent(UiEvent.Navigate(Routes.DATA + "?projectId=$id"))
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
                if (event.isOpen && table.layout.none { it.type == DataType.WHOLE_NUMBER }) {
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