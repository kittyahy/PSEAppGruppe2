package com.pseandroid2.dailydata.ui.project.data.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pseandroid2.dailydata.di.Repository
import com.pseandroid2.dailydata.ui.project.creation.ProjectCreationEvent
import com.pseandroid2.dailydata.util.ui.DataType
import com.pseandroid2.dailydata.util.ui.Graphs
import com.pseandroid2.dailydata.util.ui.Notification
import com.pseandroid2.dailydata.util.ui.ProjectMember
import com.pseandroid2.dailydata.util.ui.Routes
import com.pseandroid2.dailydata.util.ui.TableButton
import com.pseandroid2.dailydata.util.ui.TableColumn
import com.pseandroid2.dailydata.util.ui.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProjectDataSettingsScreenViewModel @Inject constructor(
    private val repository: Repository,
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
    var table by mutableStateOf( listOf<TableColumn>() )
        private set
    var buttons by mutableStateOf( listOf<TableButton>() )
        private set
    var members by mutableStateOf( listOf<ProjectMember>() )
        private set

    var notifications by mutableStateOf( listOf<Notification>() )
        private set
    var graphs by mutableStateOf( listOf<Graphs>() )
        private set
    var currentEditMember by mutableStateOf("")
        private set
    var currentEditMemberError by mutableStateOf(false)
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
    var isMembersDialogOpen by mutableStateOf(false)
        private set

    var isBackDialogOpen by mutableStateOf(false)
        private set

    init {
        val todoId = savedStateHandle.get<Int>("projectId")!!
        if(todoId != -1) {
            viewModelScope.launch {
                //set template values
            }
        }
    }

    fun onEvent(event: ProjectDataSettingsScreenEvent) {
        when (event) {
            is ProjectDataSettingsScreenEvent.OnTitleChange -> title = event.title

            is ProjectDataSettingsScreenEvent.OnDescriptionChange -> description = event.description

            is ProjectDataSettingsScreenEvent.OnWallpaperChange -> wallpaper = event.wallpaper

            is ProjectDataSettingsScreenEvent.OnTableAdd -> {
                var id = if (table.isEmpty()) {
                    0
                } else {
                    table.last().id + 1
                }
                var mutable = table.toMutableList()
                mutable.add(TableColumn(id = id, name = event.name, unit = event.unit, dataType = event.dataType))
                table = mutable.toList()
            }

            is ProjectDataSettingsScreenEvent.OnTableRemove -> {
                var mutable = table.toMutableList()
                var removed = mutable.removeAt(index = event.index)
                var mutableButtons = buttons.toMutableList()
                buttons = mutableButtons.filter { it.column.id != removed.id}.toList()
                table = mutable.toList()
            }

            is ProjectDataSettingsScreenEvent.OnButtonAdd -> {
                var id = if (buttons.isEmpty()) {
                    0
                } else {
                    buttons.last().id + 1
                }
                var mutable = buttons.toMutableList()
                mutable.add(TableButton(id = id, name = event.name, column = table.find {event.columnId == it.id}!!, value = event.value))
                buttons = mutable.toList()
            }

            is ProjectDataSettingsScreenEvent.OnButtonRemove -> {
                var mutable = buttons.toMutableList()
                mutable.removeAt(index = event.index)
                buttons = mutable.toList()
            }

            is ProjectDataSettingsScreenEvent.OnNotificationAdd -> {
                var mutable = notifications.toMutableList()
                mutable.add(Notification(message = event.message, time = event.time))
                notifications = mutable.toList()
            }

            is ProjectDataSettingsScreenEvent.OnNotificationRemove -> {
                var mutable = notifications.toMutableList()
                mutable.removeAt(index = event.index)
                notifications = mutable.toList()
            }

            is ProjectDataSettingsScreenEvent.OnGraphAdd -> {
                var mutable = graphs.toMutableList()
                mutable.add(event.graph)
                graphs = mutable.toList()
            }

            is ProjectDataSettingsScreenEvent.OnGraphRemove -> {
                var mutable = graphs.toMutableList()
                mutable.removeAt(index = event.index)
                graphs = mutable.toList()
            }
            is ProjectDataSettingsScreenEvent.OnSaveClick -> {
                when {
                    title.isBlank() -> sendUiEvent(UiEvent.ShowToast("Please Enter a title"))
                    table.isEmpty() -> sendUiEvent(UiEvent.ShowToast("Please Enter a column"))
                    else            -> {
                        var id = 0 //id = repository.createProject(...)
                        sendUiEvent(UiEvent.PopBackStack )
                        sendUiEvent(UiEvent.Navigate(Routes.DATA + "?projectId=$id"))
                    }
                }
            }

            is ProjectDataSettingsScreenEvent.OnShowWallpaperDialog -> isWallpaperDialogOpen = event.isOpen

            is ProjectDataSettingsScreenEvent.OnShowTableDialog -> isTableDialogOpen = event.isOpen

            is ProjectDataSettingsScreenEvent.OnShowButtonsDialog -> {
                if(event.isOpen && table.none { it.dataType == DataType.WHOLE_NUMBER }) {
                    sendUiEvent(UiEvent.ShowToast("Please Enter a compatible column first"))
                } else {
                    isButtonsDialogOpen = event.isOpen
                }
            }
            is ProjectDataSettingsScreenEvent.OnShowNotificationDialog -> isNotificationDialogOpen = event.isOpen

            is ProjectDataSettingsScreenEvent.OnShowGraphDialog -> isGraphDialogOpen = event.isOpen

            is ProjectDataSettingsScreenEvent.OnShowBackDialog -> isBackDialogOpen = event.isOpen

            is ProjectDataSettingsScreenEvent.OnNavigateBack -> sendUiEvent(UiEvent.PopBackStack)

            is ProjectDataSettingsScreenEvent.OnChangeEditMember -> {
                currentEditMember = event.name
                currentEditMemberError = false
            }

            is ProjectDataSettingsScreenEvent.OnAddMember -> {
                /*
                    if is not member -> error
                    else close and add member to list
                 */

            }
            is ProjectDataSettingsScreenEvent.OnShowMemberDialog -> {
                isMembersDialogOpen = true
            }
            is ProjectDataSettingsScreenEvent.OnMemberRemove -> {
                var mutable = members.toMutableList()
                mutable.removeAt(event.index)
                members = mutable.toList()
            }
            is ProjectDataSettingsScreenEvent.OnLeaveProject -> {
                //leave
                sendUiEvent(UiEvent.PopBackStack)
            }
            is ProjectDataSettingsScreenEvent.OnDeleteProject -> {
                //delete
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