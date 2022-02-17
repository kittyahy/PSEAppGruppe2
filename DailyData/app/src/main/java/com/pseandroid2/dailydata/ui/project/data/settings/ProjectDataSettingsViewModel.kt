package com.pseandroid2.dailydata.ui.project.data.settings

import android.util.Log
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
import com.pseandroid2.dailydata.model.notifications.TimeNotification
import com.pseandroid2.dailydata.model.project.Project
import com.pseandroid2.dailydata.model.table.ArrayListTable
import com.pseandroid2.dailydata.model.table.ColumnData
import com.pseandroid2.dailydata.model.table.Table
import com.pseandroid2.dailydata.model.uielements.UIElement
import com.pseandroid2.dailydata.model.uielements.UIElementType
import com.pseandroid2.dailydata.model.users.User
import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.DataType
import com.pseandroid2.dailydata.ui.grapthstrategy.FloatLineChartStrategy
import com.pseandroid2.dailydata.ui.grapthstrategy.GraphCreationStrategy
import com.pseandroid2.dailydata.ui.grapthstrategy.IntLineChartStrategy
import com.pseandroid2.dailydata.ui.grapthstrategy.PieChartStrategy
import com.pseandroid2.dailydata.ui.grapthstrategy.TimeLineChartStrategy
import com.pseandroid2.dailydata.ui.link.appLinks.JoinProjectLinkManager
import com.pseandroid2.dailydata.util.Consts.LOG_TAG
import com.pseandroid2.dailydata.util.ui.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
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

    var currentGraphType by mutableStateOf<String?>(null)
        private set
    var xAxis by mutableStateOf<Int?>(null)
        private set
    var mapping by mutableStateOf<List<Int>?>(null)
        private set
    var graphName by mutableStateOf<String?>(null)
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
    var isXAxisDialogOpen by mutableStateOf(false)
        private set
    var isMappingDialogOpen by mutableStateOf(false)
        private set
    var isGraphNameDialogOpen by mutableStateOf(false)
        private set

    var isBackDialogOpen by mutableStateOf(false)
        private set

    fun initialize(projectId: Int) {
        viewModelScope.launch {
            repository.projectHandler.initializeProjectProvider(projectId)
        }
        viewModelScope.launch {
            project = repository.projectHandler.getProjectByID(projectId).asLiveData()
        }
    }


    fun onEvent(event: ProjectDataSettingsScreenEvent) {
        when (event) {
            /*is ProjectDataSettingsScreenEvent.OnCreate -> { //TODO this really should go
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
            }*/
            is ProjectDataSettingsScreenEvent.OnTitleChange -> {
                viewModelScope.launch {
                    if (event.title.isNotBlank()) {
                        project.value!!.setName(event.title)
                    } else {
                        sendUiEvent(UiEvent.ShowToast("Project title must not be empty"))
                    }
                }
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
            is ProjectDataSettingsScreenEvent.OnColumnRemove -> {
                viewModelScope.launch {
                    if (project.value!!.table.layout.size > 1) {
                        project.value!!.deleteColumn(event.columnId)
                    } else {
                        sendUiEvent(UiEvent.ShowToast("Project Table must always have at least one column"))
                    }
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
                    project.value!!.table.removeUIElement(event.columnId, event.id)
                }
            }
            is ProjectDataSettingsScreenEvent.OnNotificationAdd -> {
                viewModelScope.launch {
                    project.value!!.addNotification(TimeNotification(event.message, event.time))
                }
            }
            is ProjectDataSettingsScreenEvent.OnNotificationRemove -> {
                viewModelScope.launch {
                    project.value!!.removeNotification(event.id)
                }
            }
            is ProjectDataSettingsScreenEvent.OnCreateLink -> {
                val manager = JoinProjectLinkManager()
                val link = manager.createLink(project.value!!.id.toLong())
                sendUiEvent(UiEvent.CopyToClipboard(link))
            }
            is ProjectDataSettingsScreenEvent.OnGraphAdd -> {
                viewModelScope.launch {
                    if (currentGraphType == null || mapping == null) {
                        Log.e(
                            LOG_TAG,
                            "Could not create Graph because graph type or mapping have not" +
                                    "been set"
                        )
                        return@launch
                    }
                    var graphStrategy: GraphCreationStrategy? = null
                    when (currentGraphType) {
                        Graph.LINE_CHART_STR -> {
                            if (xAxis != null) {
                                when (project.value!!.table.layout[xAxis!!].type) {
                                    DataType.TIME -> graphStrategy = TimeLineChartStrategy()
                                    DataType.WHOLE_NUMBER -> graphStrategy = IntLineChartStrategy()
                                    DataType.FLOATING_POINT_NUMBER -> graphStrategy =
                                        FloatLineChartStrategy()
                                    else -> {
                                        /*Nothing to do here*/
                                    }
                                }
                            }
                        }
                        Graph.PIE_CHART_STR -> graphStrategy = PieChartStrategy()
                    }
                    if (graphStrategy == null) {
                        Log.e(
                            LOG_TAG, "Could not create Graph because the chosen graph type was" +
                                    "not known or no xAxis value was given for a LineChart"
                        )
                        return@launch

                    }
                    val transformation = try {
                        graphStrategy.createTransformation(xAxis)
                    } catch (ex: IllegalArgumentException) {
                        Log.e(LOG_TAG, ex.message ?: "Could not create TransformationFunction")
                        return@launch
                    }
                    project.value!!.addGraph(
                        graphStrategy.createGraph(
                            project.value!!.createDataTransformation(transformation, mapping!!),
                            graphStrategy.createBaseSettings(graphName)
                        )
                    )
                }
            }
            is ProjectDataSettingsScreenEvent.OnGraphRemove -> {
                viewModelScope.launch {
                    project.value!!.removeGraph(event.id)
                }
            }
            is ProjectDataSettingsScreenEvent.OnUserRemove -> {
                viewModelScope.launch {
                    if (event.user != repository.serverHandler.loggedIn) {
                        project.value!!.removeUser(event.user)
                    } else {
                        sendUiEvent(UiEvent.ShowToast("If you want to leave this project, choose \"Leave Project\""))
                    }
                }
            }

            is ProjectDataSettingsScreenEvent.OnChoseGraphType -> {
                currentGraphType = event.graphType
            }
            is ProjectDataSettingsScreenEvent.OnChoseXAxis -> {
                xAxis = event.col
            }
            is ProjectDataSettingsScreenEvent.OnChoseMapping -> {
                mapping = event.mapping
            }

            is ProjectDataSettingsScreenEvent.OnShowWallpaperDialog -> {
                isWallpaperDialogOpen = event.isOpen
            }
            is ProjectDataSettingsScreenEvent.OnShowTableDialog -> {
                isTableDialogOpen = event.isOpen
            }
            is ProjectDataSettingsScreenEvent.OnShowButtonsDialog -> {
                if (event.isOpen && project.value!!.table.layout.none { it.type == DataType.WHOLE_NUMBER }) {
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
            is ProjectDataSettingsScreenEvent.OnShowXAxisDialog -> {
                isXAxisDialogOpen = event.isOpen
                if (!event.isOpen && event.hasSuccessfullyChosen) {
                    onEvent(ProjectDataSettingsScreenEvent.OnShowMappingDialog(true))
                }
            }
            is ProjectDataSettingsScreenEvent.OnShowMappingDialog -> {
                isMappingDialogOpen = event.isOpen
                if (!event.isOpen && event.hasSuccessfullyChosen) {
                    onEvent(ProjectDataSettingsScreenEvent.OnShowGraphNameDialog(true))
                }
            }
            is ProjectDataSettingsScreenEvent.OnShowGraphNameDialog -> {
                isGraphNameDialogOpen = event.isOpen
            }

            is ProjectDataSettingsScreenEvent.OnNavigateBack -> {
                sendUiEvent(UiEvent.PopBackStack)
            }

            is ProjectDataSettingsScreenEvent.OnLeaveProject -> {
                viewModelScope.launch {
                    project.value!!.removeUser(repository.serverHandler.loggedIn)
                    project.value!!.unsubscribe()
                    sendUiEvent(UiEvent.PopBackStack)
                }
            }
            is ProjectDataSettingsScreenEvent.OnDeleteProject -> {
                viewModelScope.launch {
                    project.value!!.delete()
                    sendUiEvent(UiEvent.PopBackStack)
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