package com.pseandroid2.dailydata.ui.project.data.input

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pseandroid2.dailydata.di.Repository
import com.pseandroid2.dailydata.util.ui.ProjectMember
import com.pseandroid2.dailydata.util.ui.TableRow
import com.pseandroid2.dailydata.util.ui.TableButton
import com.pseandroid2.dailydata.util.ui.TableColumn
import com.pseandroid2.dailydata.util.ui.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProjectDataInputScreenViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    var title = ""
    var description = ""
    var wallpaper = Color.Blue
    var isDescriptionUnfolded by mutableStateOf(false)
        private set
    val members : Flow<List<ProjectMember>> = flow {
        emptyList<ProjectMember>()
    }
    var columns : List<TableColumn> = listOf()
        private set
    var insertRow by mutableStateOf("")
        private set
    var isRowDialogOpen by mutableStateOf(false)
        private set
    private var rowEdit = 0
    var buttons : List<TableButton> = listOf()
    var columnValues : List<String> = listOf()
        private set
    var table : Flow<List<TableRow>> = flow {
        emptyList<TableRow>()
    }

    init {

    }

    fun onEvent(event: ProjectDataInputScreenEvent) {
        when(event) {
            is ProjectDataInputScreenEvent.OnDescriptionClick -> {
                isDescriptionUnfolded = !isDescriptionUnfolded
            }
            is ProjectDataInputScreenEvent.OnButtonClickInc -> {
                //repository add button
            }
            is ProjectDataInputScreenEvent.OnButtonClickDec -> {
                //repository
            }
            is ProjectDataInputScreenEvent.OnButtonClickAdd -> {
                columnValues[columns.indexOfFirst { it.id == event.id }]
                //repository button(initial value)
            }
            is ProjectDataInputScreenEvent.OnColumnChange -> {
                var mutable = columnValues.toMutableList()
                mutable[event.index] = event.value
                columnValues = mutable.toList()
            }
            is ProjectDataInputScreenEvent.OnColumnInsertRowChange -> {
                insertRow = event.value
                viewModelScope.launch {
                    var lastIndex = table.flattenToList().lastIndex
                    if(isInt(insertRow) && insertRow.toInt() > lastIndex) {
                        insertRow = lastIndex.toString()
                    }
                }
            }
            is ProjectDataInputScreenEvent.OnColumnAdd -> {

                //repository add row(list<String>)
            }
            is ProjectDataInputScreenEvent.OnRowDialogShow -> {
                rowEdit = event.index
                isRowDialogOpen = true
            }
            is ProjectDataInputScreenEvent.OnCloseRowDialog -> {
                isRowDialogOpen = false
            }
            is ProjectDataInputScreenEvent.OnRowModifyClick -> {
                viewModelScope.launch {
                    var list = table.flattenToList()
                    columnValues = list[rowEdit].elements
                }

            }
            is ProjectDataInputScreenEvent.OnRowDeleteClick -> {
                //repository remove row
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