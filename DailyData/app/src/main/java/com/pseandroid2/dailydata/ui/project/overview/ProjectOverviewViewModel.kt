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

package com.pseandroid2.dailydata.ui.project.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pseandroid2.dailydata.util.ui.UiEvent
import com.pseandroid2.dailydata.di.Repository
import com.pseandroid2.dailydata.util.ui.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProjectOverviewViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {

    //repository.getProjects()
    val projects : Flow<List<String>> = flow {
        emptyList<String>()
    }

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun onEvent(event: ProjectOverviewEvent) {
        when(event) {
            is ProjectOverviewEvent.OnNewProjectClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.CREATION))
            }
            is ProjectOverviewEvent.OnProjectClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.DATA + "?projectId=${event.id}" ))
            }
        }
    }

    private fun sendUiEvent(event : UiEvent) {
        viewModelScope.launch {
            _uiEvent.emit(event)
        }
    }

}