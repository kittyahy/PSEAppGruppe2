package com.pseandroid2.dailydata.ui.project.data.settings

import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.ClipboardManager
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Button
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Column
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.DataType
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Graph
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Member
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Notification
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Project
import com.pseandroid2.dailydata.ui.link.appLinks.JoinProjectLinkManager
import com.pseandroid2.dailydata.ui.project.data.DataTabs
import com.pseandroid2.dailydata.util.ui.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@InternalCoroutinesApi
@HiltViewModel
class ProjectDataSettingsScreenViewModel @Inject constructor(
    private val repository: RepositoryViewModelAPI
) : ViewModel() {

    private lateinit var initialProject : Project
    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    var isAdmin = false
        private set
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
    var members by mutableStateOf( listOf<Member>() )
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

    fun onEvent(event: ProjectDataSettingsScreenEvent) {
        when (event) {
            is ProjectDataSettingsScreenEvent.OnCreate -> {
                viewModelScope.launch {
                    repository.projectHandler.getProjectByID(id = event.projectId).collect { project ->
                        title = project.title
                        description = project.description
                        wallpaper = Color(project.wallpaper.toInt())
                        table = project.table
                        buttons = project.buttons
                        members = project.members
                        notifications = project.notifications
                        graphs = project.graphs
                        isAdmin = isAdmin
                        initialProject = project
                    }
                }
            }
            is ProjectDataSettingsScreenEvent.OnTitleChange -> {
                title = event.title
            }
            is ProjectDataSettingsScreenEvent.OnDescriptionChange -> {
                description = event.description
            }
            is ProjectDataSettingsScreenEvent.OnWallpaperChange -> {
                wallpaper = event.wallpaper
            }
            is ProjectDataSettingsScreenEvent.OnTableAdd -> {
                var id = if (table.isEmpty()) {
                    0
                } else {
                    table.last().id + 1
                }
                var mutable = table.toMutableList()
                mutable.add(Column(id = id, name = event.name, unit = event.unit, dataType = event.dataType))
                table = mutable.toList()
            }
            is ProjectDataSettingsScreenEvent.OnTableRemove -> {
                var mutable = table.toMutableList()
                var removed = mutable.removeAt(index = event.index)
                var mutableButtons = buttons.toMutableList()
                buttons = mutableButtons.filter { it.columnId != removed.id}.toList()
                table = mutable.toList()
            }
            is ProjectDataSettingsScreenEvent.OnButtonAdd -> {
                var id = if (buttons.isEmpty()) {
                    0
                } else {
                    buttons.last().id + 1
                }
                var mutable = buttons.toMutableList()
                mutable.add(Button(id = id, name = event.name, columnId = event.columnId, value = event.value))
                buttons = mutable.toList()
            }
            is ProjectDataSettingsScreenEvent.OnButtonRemove -> {
                var mutable = buttons.toMutableList()
                mutable.removeAt(index = event.index)
                buttons = mutable.toList()
            }
            is ProjectDataSettingsScreenEvent.OnNotificationAdd -> {
                var mutable = notifications.toMutableList()
                mutable.add(Notification(id = 0, message = event.message, time = event.time))
                notifications = mutable.toList()
            }
            is ProjectDataSettingsScreenEvent.OnNotificationRemove -> {
                var mutable = notifications.toMutableList()
                mutable.removeAt(index = event.index)
                notifications = mutable.toList()
            }
            is ProjectDataSettingsScreenEvent.OnCreateLink -> {
                var manager = JoinProjectLinkManager()
                var link = manager.createLink(initialProject.id.toLong())
                sendUiEvent(UiEvent.CopyToClipboard(link))
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
            is ProjectDataSettingsScreenEvent.OnMemberRemove -> {
                var mutable = members.toMutableList()
                mutable.removeAt(event.index)
                members = mutable.toList()
            }
            is ProjectDataSettingsScreenEvent.OnShowWallpaperDialog -> {
                isWallpaperDialogOpen = event.isOpen
            }
            is ProjectDataSettingsScreenEvent.OnShowTableDialog -> {
                isTableDialogOpen = event.isOpen
            }
            is ProjectDataSettingsScreenEvent.OnShowButtonsDialog -> {
                if(event.isOpen && table.none { it.dataType == DataType.WHOLE_NUMBER }) {
                    sendUiEvent(UiEvent.ShowToast("Please Enter a compatible column first"))
                } else {
                    isButtonsDialogOpen = event.isOpen
                }
            }
            is ProjectDataSettingsScreenEvent.OnShowNotificationDialog -> {
                isNotificationDialogOpen = event.isOpen
            }
            is ProjectDataSettingsScreenEvent.OnShowGraphDialog -> {
                isGraphDialogOpen = event.isOpen
            }
            is ProjectDataSettingsScreenEvent.OnShowBackDialog -> {
                isBackDialogOpen = event.isOpen
            }

            is ProjectDataSettingsScreenEvent.OnNavigateBack -> {
                sendUiEvent(UiEvent.PopBackStack)
            }

            is ProjectDataSettingsScreenEvent.OnLeaveProject -> {
                initialProject.leaveOnlineProject()
                sendUiEvent(UiEvent.PopBackStack)
            }
            is ProjectDataSettingsScreenEvent.OnDeleteProject -> {
                viewModelScope.launch {
                    initialProject.delete()
                    sendUiEvent(UiEvent.PopBackStack)
                }
            }

            is ProjectDataSettingsScreenEvent.OnSaveClick -> {
                when {
                    title.isBlank() -> sendUiEvent(UiEvent.ShowToast("Please Enter a title"))
                    table.isEmpty() -> sendUiEvent(UiEvent.ShowToast("Please Enter a column"))
                    else            -> {
                        var id = 0
                        initialProject.setName(name = title)
                        initialProject.setDescription(description = description)
                        initialProject.changeWallpaper(image = wallpaper.toArgb())
                        for (column in initialProject.table) {
                            if(!table.contains(column)) {
                                initialProject.deleteColumn(column = column)
                            }
                        }
                        for (column in table) {
                            if(!initialProject.table.contains(column)) {
                                initialProject.addColumn(column = column)
                            }
                        }
                        for (button in initialProject.buttons) {
                            if(!buttons.contains(button)) {
                                initialProject.deleteButton(button = button)
                            }
                        }
                        for (button in buttons) {
                            if(!initialProject.buttons.contains(button)) {
                                initialProject.addButton(button = button)
                            }
                        }
                        for (member in initialProject.members) {
                            if(!members.contains(member)) {
                                initialProject.deleteMember(member = member)
                            }
                        }
                        for (notification in initialProject.notifications) {
                            if(!notifications.contains(notification)) {
                                initialProject.deleteNotification(notification = notification)
                            }
                        }
                        for (notification in notifications) {
                            if(!initialProject.notifications.contains(notification)) {
                                initialProject.addNotification(notification = notification)
                            }
                        }
                        for (graph in initialProject.graphs) {
                            if(!graphs.contains(graph)) {
                                viewModelScope.launch {
                                    graph.delete()
                                }
                            }
                        }
                        for (graph in graphs) {
                            if(!initialProject.graphs.contains(graph)) {
                                initialProject.addGraph(graph = graph)
                            }
                        }
                        sendUiEvent(UiEvent.Navigate(DataTabs.INPUT.toString()))
                    }
                }
            }
        }
    }

    private fun sendUiEvent(event : UiEvent) {
        viewModelScope.launch {
            _uiEvent.emit(event)
        }
    }
}