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
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pseandroid2.dailydata.model.graph.Graph
import com.pseandroid2.dailydata.model.graph.GraphType
import com.pseandroid2.dailydata.model.notifications.TimeNotification
import com.pseandroid2.dailydata.model.project.InMemoryProject
import com.pseandroid2.dailydata.model.table.ColumnData
import com.pseandroid2.dailydata.model.uielements.UIElement
import com.pseandroid2.dailydata.model.uielements.UIElementType
import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.DataType
import com.pseandroid2.dailydata.ui.grapthstrategy.FloatLineChartStrategy
import com.pseandroid2.dailydata.ui.grapthstrategy.GraphCreationStrategy
import com.pseandroid2.dailydata.ui.grapthstrategy.IntLineChartStrategy
import com.pseandroid2.dailydata.ui.grapthstrategy.PieChartStrategy
import com.pseandroid2.dailydata.ui.grapthstrategy.TimeLineChartStrategy
import com.pseandroid2.dailydata.ui.navigation.Routes
import com.pseandroid2.dailydata.ui.project.data.settings.ProjectDataSettingsScreenEvent
import com.pseandroid2.dailydata.util.Consts.LOG_TAG
import com.pseandroid2.dailydata.util.ui.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
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
    var isXAxisDialogOpen by mutableStateOf(false)
        private set
    var isMappingDialogOpen by mutableStateOf(false)
        private set
    var isGraphNameDialogOpen by mutableStateOf(false)
        private set

    var currentGraphType by mutableStateOf<String?>(null)
        private set
    var xAxis by mutableStateOf<Int?>(null)
        private set
    var mapping by mutableStateOf<List<ColumnData>?>(null)
        private set
    var graphName by mutableStateOf<String?>(null)
        private set

    var isBackDialogOpen by mutableStateOf(false)
        private set

    init {
        //assert(-1 != savedStateHandle.get<Int>("projectTemplateId")!!)
    }

    //Most events will recreate the Project Object as MutableState won't recognize it as a new value
    //otherwise
    fun onEvent(event: ProjectCreationEvent) {
        when (event) {
            is ProjectCreationEvent.OnTitleChange -> {
                viewModelScope.launch {
                    val tmp = InMemoryProject(project)
                    tmp.setName(event.title)
                    project = tmp
                }
            }
            is ProjectCreationEvent.OnDescriptionChange -> {
                viewModelScope.launch {
                    val tmp = InMemoryProject(project)
                    tmp.setDesc(event.description)
                    project = tmp
                }
            }
            is ProjectCreationEvent.OnWallpaperChange -> {
                viewModelScope.launch {
                    val tmp = InMemoryProject(project)
                    tmp.setColor(event.wallpaper.toArgb())
                    project = tmp
                }
            }
            is ProjectCreationEvent.OnTableAdd -> {
                viewModelScope.launch {
                    val tmp = InMemoryProject(project)
                    tmp.addColumn(
                        ColumnData(
                            type = event.dataType,
                            name = event.name,
                            unit = event.unit
                        ),
                        event.dataType.initialValue
                    )
                    Log.d(LOG_TAG, "Layout-Size pre-Update: ${project.table.layout.size}")
                    project = tmp
                    Log.d(LOG_TAG, "Layout-Size post-Update: ${project.table.layout.size}")
                }
            }
            is ProjectCreationEvent.OnTableRemove -> {//TODO Rename
                viewModelScope.launch {
                    val tmp = InMemoryProject(project)
                    tmp.deleteColumn(event.index)
                    project = tmp
                }

            }
            is ProjectCreationEvent.OnButtonAdd -> {
                viewModelScope.launch {
                    val tmp = InMemoryProject(project)
                    tmp.addUIElement(
                        event.columnId,
                        UIElement(
                            name = event.name,
                            type = UIElementType.BUTTON,
                            state = event.value.toString()
                        )
                    )
                    project = tmp
                }
            }
            is ProjectCreationEvent.OnButtonRemove -> {
                viewModelScope.launch {
                    val tmp = InMemoryProject(project)
                    tmp.removeUIElement(event.columnID, event.id)
                    project = tmp
                }
            }
            is ProjectCreationEvent.OnNotificationAdd -> {
                viewModelScope.launch {
                    val tmp = InMemoryProject(project)
                    tmp.addNotification(
                        TimeNotification(
                            initId = 0,
                            messageString = event.message,
                            send = event.time
                        )
                    )
                    project = tmp
                }
            }
            is ProjectCreationEvent.OnNotificationRemove -> {
                viewModelScope.launch {
                    val tmp = InMemoryProject(project)
                    tmp.removeNotification(event.index)
                    project = tmp
                }
            }
            is ProjectCreationEvent.OnGraphAdd -> {
                viewModelScope.launch {
                    if (currentGraphType == null || mapping == null) {
                        Log.e(
                            LOG_TAG,
                            "Could not create Graph because graph type or mapping have not " +
                                    "been set"
                        )
                        return@launch
                    }
                    var graphStrategy: GraphCreationStrategy? = null
                    when (currentGraphType) {
                        Graph.LINE_CHART_STR -> {
                            if (xAxis != null) {
                                when (project.table.layout[xAxis!!].type) {
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
                    val tmp = InMemoryProject(project)
                    tmp.addGraph(
                        graphStrategy.createGraph(
                            tmp.createDataTransformation(transformation, mapping!!),
                            graphStrategy.createBaseSettings(graphName)
                        )
                    )
                    project = tmp
                }
            }
            is ProjectCreationEvent.OnGraphRemove -> {
                viewModelScope.launch {
                    val tmp = InMemoryProject(project)
                    tmp.removeGraph(event.id)
                    project = tmp
                }
            }

            is ProjectCreationEvent.OnChoseGraphType -> {
                currentGraphType = event.graphType
            }
            is ProjectCreationEvent.OnChoseXAxis -> {
                xAxis = event.col
                onEvent(ProjectCreationEvent.OnShowMappingDialog(true))
            }
            is ProjectCreationEvent.OnChoseMapping -> {
                mapping = event.mapping
            }
            is ProjectCreationEvent.OnChoseGraphName -> {
                graphName = event.name
                onEvent(ProjectCreationEvent.OnGraphAdd)
            }

            is ProjectCreationEvent.OnSaveClick -> {
                val default = InMemoryProject(0)
                when {
                    default.name == project.name -> sendUiEvent(UiEvent.ShowToast("Please Enter a title"))
                    project.table.layout.size == 0 -> sendUiEvent(UiEvent.ShowToast("Please Enter a column"))
                    else -> {
                        viewModelScope.launch {
                            val newProject = project.writeToDBAsync(repository.projectHandler)
                            val id = newProject.await()
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
                if (event.isOpen && project.table.layout.none { it.type == DataType.WHOLE_NUMBER }) {
                    sendUiEvent(UiEvent.ShowToast("Please Enter a compatible column first"))
                } else {
                    isButtonsDialogOpen = event.isOpen
                }
            }
            is ProjectCreationEvent.OnShowXAxisDialog -> {
                Log.d(LOG_TAG, event.isOpen.toString())
                isXAxisDialogOpen = event.isOpen
                if (!event.isOpen && event.hasSuccessfullyChosen) {
                    onEvent(ProjectCreationEvent.OnShowMappingDialog(true))
                }
            }
            is ProjectCreationEvent.OnShowMappingDialog -> {
                isMappingDialogOpen = event.isOpen
                if (!event.isOpen && event.hasSuccessfullyChosen) {
                    onEvent(ProjectCreationEvent.OnShowGraphNameDialog(true))
                }
            }
            is ProjectCreationEvent.OnShowGraphNameDialog -> {
                isGraphNameDialogOpen = event.isOpen
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