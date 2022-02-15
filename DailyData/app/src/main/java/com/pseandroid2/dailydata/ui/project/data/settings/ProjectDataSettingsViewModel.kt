package com.pseandroid2.dailydata.ui.project.data.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Button
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Column
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.DataType
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Graph
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Member
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Notification
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.ViewModelProject
import com.pseandroid2.dailydata.ui.link.appLinks.JoinProjectLinkManager
import com.pseandroid2.dailydata.ui.project.data.DataTabs
import com.pseandroid2.dailydata.util.ui.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@InternalCoroutinesApi
@HiltViewModel
class ProjectDataSettingsScreenViewModel @Inject constructor(
    private val repository: RepositoryViewModelAPI
) : ViewModel() {

    private lateinit var initialViewModelProject: ViewModelProject
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
    var table by mutableStateOf(listOf<Column>())
        private set
    var buttons by mutableStateOf(listOf<Button>())
        private set
    var members by mutableStateOf(listOf<Member>())
        private set

    var notifications by mutableStateOf(listOf<Notification>())
        private set
    var graphs by mutableStateOf(listOf<Graph>())
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
                    repository.projectHandler.initializeProjectProvider(event.projectId)
                }
                viewModelScope.launch {
                    repository.projectHandler.getProjectByID(id = event.projectId)
                        .collect { project ->
                            title = project.title
                            description = project.desc
                            wallpaper = Color(project.wallpaper)
                            table = project.table
                            buttons = project.buttons
                            members = project.members
                            notifications = project.notifications
                            graphs = project.graphs
                            isAdmin = isAdmin
                            initialViewModelProject = project
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
                val id = if (table.isEmpty()) {
                    0
                } else {
                    table.last().id + 1
                }
                val mutable = table.toMutableList()
                mutable.add(
                    Column(
                        id = id,
                        name = event.name,
                        unit = event.unit,
                        dataType = event.dataType
                    )
                )
                table = mutable.toList()
            }
            is ProjectDataSettingsScreenEvent.OnTableRemove -> {
                val mutable = table.toMutableList()
                val removed = mutable.removeAt(index = event.index)
                val mutableButtons = buttons.toMutableList()
                buttons = mutableButtons.filter { it.columnId != removed.id }.toList()
                table = mutable.toList()
            }
            is ProjectDataSettingsScreenEvent.OnButtonAdd -> {
                val id = if (buttons.isEmpty()) {
                    0
                } else {
                    buttons.last().id + 1
                }
                val mutable = buttons.toMutableList()
                mutable.add(
                    Button(
                        id = id,
                        name = event.name,
                        columnId = event.columnId,
                        value = event.value
                    )
                )
                buttons = mutable.toList()
            }
            is ProjectDataSettingsScreenEvent.OnButtonRemove -> {
                val mutable = buttons.toMutableList()
                mutable.removeAt(index = event.index)
                buttons = mutable.toList()
            }
            is ProjectDataSettingsScreenEvent.OnNotificationAdd -> {
                val mutable = notifications.toMutableList()
                mutable.add(Notification(id = 0, message = event.message, time = event.time))
                notifications = mutable.toList()
            }
            is ProjectDataSettingsScreenEvent.OnNotificationRemove -> {
                val mutable = notifications.toMutableList()
                mutable.removeAt(index = event.index)
                notifications = mutable.toList()
            }
            is ProjectDataSettingsScreenEvent.OnCreateLink -> {
                val manager = JoinProjectLinkManager()
                val link = manager.createLink(initialViewModelProject.id.toLong())
                sendUiEvent(UiEvent.CopyToClipboard(link))
            }
            is ProjectDataSettingsScreenEvent.OnGraphAdd -> {
                val mutable = graphs.toMutableList()
                mutable.add(event.graph)
                graphs = mutable.toList()
            }
            is ProjectDataSettingsScreenEvent.OnGraphRemove -> {
                val mutable = graphs.toMutableList()
                mutable.removeAt(index = event.index)
                graphs = mutable.toList()
            }
            is ProjectDataSettingsScreenEvent.OnMemberRemove -> {
                val mutable = members.toMutableList()
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
                if (event.isOpen && table.none { it.dataType == DataType.WHOLE_NUMBER }) {
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
                viewModelScope.launch {
                    if (initialViewModelProject.leaveOnlineProjectIsPossible().first()) {
                        initialViewModelProject.leaveOnlineProject()
                        sendUiEvent(UiEvent.PopBackStack)
                    }
                }
            }
            is ProjectDataSettingsScreenEvent.OnDeleteProject -> {
                viewModelScope.launch {
                    if (initialViewModelProject.deleteIsPossible().first()) {
                        initialViewModelProject.delete()
                        sendUiEvent(UiEvent.PopBackStack)
                    }
                }
            }

            is ProjectDataSettingsScreenEvent.OnSaveClick -> {
                when {
                    title.isBlank() -> sendUiEvent(UiEvent.ShowToast("Please Enter a title"))
                    table.isEmpty() -> sendUiEvent(UiEvent.ShowToast("Please Enter a column"))
                    else -> {
                        viewModelScope.launch {
                            if (initialViewModelProject.setNameIsPossible().first()) {
                                initialViewModelProject.setName(name = title)
                            } else {
                                sendUiEvent(UiEvent.ShowToast("Could not change title"))
                            }
                            if (initialViewModelProject.setDescriptionIsPossible().first()) {
                                initialViewModelProject.setDescription(description = description)
                            } else {
                                sendUiEvent(UiEvent.ShowToast("Could not change description"))
                            }
                            if (initialViewModelProject.changeWallpaperIsPossible().first()) {
                                initialViewModelProject.setColor(image = wallpaper.toArgb())
                            } else {
                                sendUiEvent(UiEvent.ShowToast("Could not change wallpaper"))
                            }
                            for (column in initialViewModelProject.table) {
                                if (!table.contains(column) && initialViewModelProject.deleteColumnIsPossible(
                                        column = column
                                    ).first()
                                ) {
                                    initialViewModelProject.deleteColumn(column = column)
                                }
                            }
                            for (column in table) {                                                                                                     //i assume there is a value
                                if (!initialViewModelProject.table.contains(column) && initialViewModelProject.addColumnIsPossible()[column.dataType]!!.first()) {
                                    initialViewModelProject.addColumn(column = column)
                                }
                            }
                            for (button in initialViewModelProject.buttons) {
                                if (!buttons.contains(button) && initialViewModelProject.deleteButtonIsPossible(
                                        button = button
                                    ).first()
                                ) {
                                    initialViewModelProject.deleteUIElement(button = button)
                                }
                            }
                            for (button in buttons) {
                                if (!initialViewModelProject.buttons.contains(button) && initialViewModelProject.addButtonIsPossible()
                                        .first()
                                ) {
                                    initialViewModelProject.addUIElement(button = button)
                                }
                            }
                            for (member in initialViewModelProject.members) {
                                if (!members.contains(member) && initialViewModelProject.deleteMemberIsPossible(
                                        member = member
                                    ).first()
                                ) {
                                    initialViewModelProject.removeUser(member = member)
                                }
                            }
                            for (notification in initialViewModelProject.notifications) {
                                if (!notifications.contains(notification) && initialViewModelProject.deleteNotificationIsPossible(
                                        notification = notification
                                    ).first()
                                ) {
                                    initialViewModelProject.deleteNotification(notification = notification)
                                }
                            }
                            for (notification in notifications) {
                                if (!initialViewModelProject.notifications.contains(notification) && initialViewModelProject.addNotificationIsPossible()
                                        .first()
                                ) {
                                    initialViewModelProject.addNotification(notification = notification)
                                }
                            }
                            for (graph in initialViewModelProject.graphs) {
                                if (!graphs.contains(graph)) {
                                    viewModelScope.launch {
                                        if (graph.deleteIsPossible()
                                                .first() && graph.deleteIsPossible().first()
                                        ) {
                                            graph.delete()
                                        }
                                    }
                                }
                            }
                            for (graph in graphs) {
                                if (!initialViewModelProject.graphs.contains(graph) && initialViewModelProject.addGraphIsPossible()
                                        .first()
                                ) {
                                    initialViewModelProject.addGraph(graph = graph)
                                }
                            }
                        }
                        sendUiEvent(UiEvent.Navigate(DataTabs.INPUT.toString()))
                    }
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