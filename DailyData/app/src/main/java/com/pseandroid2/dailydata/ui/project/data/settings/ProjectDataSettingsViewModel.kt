package com.pseandroid2.dailydata.ui.project.data.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.pseandroid2.dailydata.model.graph.Graph
import com.pseandroid2.dailydata.model.notifications.Notification
import com.pseandroid2.dailydata.model.project.Project
import com.pseandroid2.dailydata.model.table.ArrayListTable
import com.pseandroid2.dailydata.model.table.ColumnData
import com.pseandroid2.dailydata.model.table.Table
import com.pseandroid2.dailydata.model.uielements.UIElement
import com.pseandroid2.dailydata.model.uielements.UIElementType
import com.pseandroid2.dailydata.model.users.User
import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.DataType
import com.pseandroid2.dailydata.ui.link.appLinks.JoinProjectLinkManager
import com.pseandroid2.dailydata.ui.project.data.DataTabs
import com.pseandroid2.dailydata.util.ui.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@InternalCoroutinesApi
@HiltViewModel
class ProjectDataSettingsScreenViewModel @Inject constructor(
    private val repository: RepositoryViewModelAPI
) : ViewModel() {

    lateinit var project: LiveData<Project>
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
    var table by mutableStateOf<Table>(ArrayListTable())
        private set
    var uiMap by mutableStateOf<MutableMap<Int, Int>>(mutableMapOf())
        private set
    var members by mutableStateOf(listOf<User>())
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

    fun initialize(projectId: Int) {
        viewModelScope.launch {
            project = repository.projectHandler.getProjectByID(projectId).asLiveData()
        }
    }


    fun onEvent(event: ProjectDataSettingsScreenEvent) {
        when (event) {
            is ProjectDataSettingsScreenEvent.OnCreate -> { //TODO this really should go
                viewModelScope.launch {
                    repository.projectHandler.initializeProjectProvider(event.projectId)
                }
                viewModelScope.launch {
                    repository.projectHandler.getProjectByID(id = event.projectId)
                        .collect { project ->
                            title = project.name
                            description = project.desc
                            wallpaper = Color(project.color)
                            table = project.table
                            members = project.users
                            notifications = project.notifications
                            graphs = project.graphs
                            isAdmin = isAdmin
                            initialViewModelProject = project
                        }
                }
            }
            is ProjectDataSettingsScreenEvent.OnTitleChange -> {
                viewModelScope.launch { project.value!!.setName(event.title) }
            }
            is ProjectDataSettingsScreenEvent.OnDescriptionChange -> {
                viewModelScope.launch { project.value!!.setDesc(event.description) }
            }
            is ProjectDataSettingsScreenEvent.OnWallpaperChange -> {
                viewModelScope.launch { project.value!!.setColor(event.wallpaper.toArgb()) }
            }
            is ProjectDataSettingsScreenEvent.OnTableAdd -> {
                viewModelScope.launch {
                    project.value!!.table.addColumn(
                        ColumnData(
                            type = event.dataType,
                            name = event.name,
                            unit = event.unit
                        )
                    )
                }
            }
            is ProjectDataSettingsScreenEvent.OnTableRemove -> {
                viewModelScope.launch {
                    project.value!!.table.deleteColumn(event.index)
                }
            }
            is ProjectDataSettingsScreenEvent.OnButtonAdd -> {
                viewModelScope.launch {
                    project.value!!.table.addUIElement(
                        event.columnId,
                        UIElement(
                            type = UIElementType.BUTTON,
                            name = event.name,
                            state = event.value.toString()
                        )
                    )
                }
            }
            is ProjectDataSettingsScreenEvent.OnButtonRemove -> {
                viewModelScope.launch {
                    table.removeUIElement(event.columnId, event.id)
                }
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