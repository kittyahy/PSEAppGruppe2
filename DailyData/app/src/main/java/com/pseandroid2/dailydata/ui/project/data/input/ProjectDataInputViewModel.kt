package com.pseandroid2.dailydata.ui.project.data.input

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Button
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Column
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Member
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Project
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Row
import com.pseandroid2.dailydata.util.ui.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import javax.inject.Inject

@InternalCoroutinesApi
@HiltViewModel
class ProjectDataInputScreenViewModel @Inject constructor(
    private val repository: RepositoryViewModelAPI
) : ViewModel() {

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    private lateinit var initialProject : Project
    var isOnlineProject = false
        private set
    var isAdmin = false
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
    var buttons : List<Button> = listOf()
    var table = listOf<Row>()

    var isDescriptionUnfolded by mutableStateOf(false)
        private set
    var isRowDialogOpen by mutableStateOf(false)
        private set
    var columnValues : List<String> = listOf()
        private set
    private var rowEdit = 0

    fun onEvent(event: ProjectDataInputScreenEvent) {
        when(event) {
            is ProjectDataInputScreenEvent.OnCreate -> {
                viewModelScope.launch {
                    repository.projectHandler.getProjectByID(id = event.projectId).collect { project ->
                        title = project.title
                        description = project.description
                        wallpaper = Color(project.wallpaper)
                        table = project.data
                        buttons = project.buttons
                        members = project.members
                        isAdmin = project.isAdmin
                        isOnlineProject = project.isOnlineProject
                        initialProject = project
                    }
                }
            }
            is ProjectDataInputScreenEvent.OnDescriptionClick -> {
                isDescriptionUnfolded = !isDescriptionUnfolded
            }
            is ProjectDataInputScreenEvent.OnButtonClickInc -> {
                var button = buttons.find { it.id == event.id }!!
                button.increaseValue()
            }
            is ProjectDataInputScreenEvent.OnButtonClickDec -> {
                var button = buttons.find { it.id == event.id }!!
                button.decreaseValue()
            }
            is ProjectDataInputScreenEvent.OnButtonClickAdd -> {
                var button = buttons.find { it.id == event.id }!!
                var mutable = columnValues.toMutableList()
                mutable[columns.indexOfFirst { it.id == event.id }] = button.value.toString()
                columnValues = mutable.toList()
                button.setValue(0)
            }
            is ProjectDataInputScreenEvent.OnColumnChange -> {
                var mutable = columnValues.toMutableList()
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
                initialProject.addRow(Row(id = 0, elements = columnValues))
            }
            is ProjectDataInputScreenEvent.OnRowDialogShow -> {
                if(isOnlineProject) {
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
                initialProject.deleteRow(table[rowEdit])
            }
        }
    }

    private suspend fun <T> Flow<List<T>>.flattenToList() =
        flatMapConcat { it.asFlow() }.toList()

    private fun sendUiEvent(event : UiEvent) {
        viewModelScope.launch {
            _uiEvent.emit(event)
        }
    }
}