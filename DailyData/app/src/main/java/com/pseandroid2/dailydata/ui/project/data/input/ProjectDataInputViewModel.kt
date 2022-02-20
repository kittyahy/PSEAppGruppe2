package com.pseandroid2.dailydata.ui.project.data.input

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Button
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Column
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Member
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.PersistentProject
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Row
import com.pseandroid2.dailydata.util.ui.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProjectDataInputScreenViewModel @Inject constructor(
    private val repository: RepositoryViewModelAPI
) : ViewModel() {

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    private lateinit var initialViewModelProject: PersistentProject
    var isOnlineProject = false
        private set

    var title = ""
        private set
    var description = ""
        private set
    var wallpaper = Color.Blue
        private set
    var members = listOf<Member>()
        private set
    var columns = listOf<Column>()
        private set
    var buttons: List<Button> = listOf()
    var table = listOf<Row>()

    var isDescriptionUnfolded by mutableStateOf(false)
        private set
    var isRowDialogOpen by mutableStateOf(false)
        private set
    var columnValues: List<String> = listOf()
        private set
    private var rowEdit = 0

    fun onEvent(event: ProjectDataInputScreenEvent) {
        when (event) {
            is ProjectDataInputScreenEvent.OnCreate -> {
                viewModelScope.launch {
                    repository.projectHandler.initializeProjectProvider(event.projectId)
                }
                viewModelScope.launch {
                    /*repository.projectHandler.getProjectByID(id = event.projectId)
                        .collect { project ->
                            title = project.title
                            description = project.desc
                            wallpaper = Color(project.wallpaper)
                            table = project.data
                            buttons = project.buttons
                            members = project.members
                            isOnlineProject = project.isOnlineProject
                            initialViewModelProject = project
                        }*/
                }

            }
            is ProjectDataInputScreenEvent.OnDescriptionClick -> {
                isDescriptionUnfolded = !isDescriptionUnfolded
            }
            is ProjectDataInputScreenEvent.OnButtonClickInc -> {
                val button = buttons.find { it.id == event.id }!!
                viewModelScope.launch {
                    if (button.increaseValueIsPossible().first()) {
                        button.increaseValue()
                    } else {
                        sendUiEvent(UiEvent.ShowToast("Could not increase value"))
                    }
                }

            }
            is ProjectDataInputScreenEvent.OnButtonClickDec -> {
                val button = buttons.find { it.id == event.id }!!
                viewModelScope.launch {
                    if (button.decreaseValueIsPossible().first()) {
                        button.decreaseValue()
                    }else {
                        sendUiEvent(UiEvent.ShowToast("Could not decrease value"))
                    }
                }
            }
            is ProjectDataInputScreenEvent.OnButtonClickAdd -> {
                val button = buttons.find { it.id == event.id }!!
                val mutable = columnValues.toMutableList()
                mutable[columns.indexOfFirst { it.id == event.id }] = button.value.toString()
                columnValues = mutable.toList()
                viewModelScope.launch {
                    if (button.setValueIsPossible().first()) {
                        button.setValue(0)
                    }else {
                        sendUiEvent(UiEvent.ShowToast("Could not reset button value"))
                    }
                }
            }
            is ProjectDataInputScreenEvent.OnColumnChange -> {
                val mutable = columnValues.toMutableList()
                mutable[event.index] = event.value
                columnValues = mutable.toList()
            }
            is ProjectDataInputScreenEvent.OnColumnAdd -> {
                columns.forEachIndexed { index, column ->
                    if (!column.dataType.regex.toRegex().matches(columnValues[index])) {
                        sendUiEvent(UiEvent.ShowToast(message = "Invalid Input"))
                        return
                    }
                }
                viewModelScope.launch {
                    /*if (initialViewModelProject.addRowIsPossible().first()) {
                        initialViewModelProject.addRow(Row(id = 0, elements = columnValues))
                        val mutable = columnValues.toMutableList()
                        val currentTime = LocalTime.now()
                        for (index in mutable.indices) {
                            if(columns[index].dataType != DataType.TIME) {
                                mutable[index] = columns[index].dataType.initialValue
                            } else {
                                mutable[index] = LocalTime.of(currentTime.hour, currentTime.minute).toString()
                            }
                        }
                        columnValues = mutable.toList()
                    }else {
                        sendUiEvent(UiEvent.ShowToast("Could not add row"))
                    }*/
                }
            }
            is ProjectDataInputScreenEvent.OnRowDialogShow -> {
                if (isOnlineProject) {
                    rowEdit = event.index
                    isRowDialogOpen = true
                }
            }
            is ProjectDataInputScreenEvent.OnCloseRowDialog -> {
                isRowDialogOpen = false
            }
            is ProjectDataInputScreenEvent.OnRowModifyClick -> {
                columnValues = table[rowEdit].elements
                isRowDialogOpen = false
            }
            is ProjectDataInputScreenEvent.OnRowDeleteClick -> {
                viewModelScope.launch {
                    /*if (initialViewModelProject.deleteRowIsPossible(table[rowEdit]).first()) {
                        initialViewModelProject.deleteRow(table[rowEdit])
                        isRowDialogOpen = false
                    } else {
                        sendUiEvent(UiEvent.ShowToast("Could not delete row"))
                    }*/
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