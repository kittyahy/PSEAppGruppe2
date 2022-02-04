package com.pseandroid2.dailydata.ui.project.data.input

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Button
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Column
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Member
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Project
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Row
import com.pseandroid2.dailydata.util.ui.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@InternalCoroutinesApi
@HiltViewModel
class ProjectDataInputScreenViewModel @Inject constructor(
    private val repository: RepositoryViewModelAPI
) : ViewModel() {

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    private lateinit var initialProject: Project
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
                    repository.projectHandler.getProjectByID(id = event.projectId)
                        .collect { project ->
                            title = project.title
                            description = project.description
                            wallpaper = Color(project.wallpaper)
                            table = project.data
                            buttons = project.buttons
                            members = project.members
                            isOnlineProject = project.isOnlineProject
                            initialProject = project
                        }
                }
            }
            is ProjectDataInputScreenEvent.OnDescriptionClick -> {
                isDescriptionUnfolded = !isDescriptionUnfolded
            }
            is ProjectDataInputScreenEvent.OnButtonClickInc -> {
                val button = buttons.find { it.id == event.id }!!
                viewModelScope.launch {
                    button.increaseValue()
                }

            }
            is ProjectDataInputScreenEvent.OnButtonClickDec -> {
                val button = buttons.find { it.id == event.id }!!
                viewModelScope.launch {
                    button.decreaseValue()
                }
            }
            is ProjectDataInputScreenEvent.OnButtonClickAdd -> {
                val button = buttons.find { it.id == event.id }!!
                val mutable = columnValues.toMutableList()
                mutable[columns.indexOfFirst { it.id == event.id }] = button.value.toString()
                columnValues = mutable.toList()
                viewModelScope.launch {
                    button.setValue(0)
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
                    initialProject.addRow(Row(id = 0, elements = columnValues))
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
            }
            is ProjectDataInputScreenEvent.OnRowDeleteClick -> {
                viewModelScope.launch {
                    initialProject.deleteRow(table[rowEdit])
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