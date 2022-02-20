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

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.ui.navigation.Routes
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.ProjectPreview
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.ProjectTemplatePreview
import com.pseandroid2.dailydata.util.ui.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProjectOverviewViewModel @Inject constructor(
    val repository: RepositoryViewModelAPI
) : ViewModel() {

    val projects = getProjectPreviews()
    val templates = getTemplatePreviews()

    var isTemplateDialogOpen by mutableStateOf(false)
        private set

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun onEvent(event: ProjectOverviewEvent) {
        when (event) {
            is ProjectOverviewEvent.OnNewProjectClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.CREATION))
            }
            is ProjectOverviewEvent.OnTemplateProjectClick -> {
                isTemplateDialogOpen = event.isOpen
            }
            is ProjectOverviewEvent.OnProjectClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.DATA + "?projectId=${event.id}"))
            }
            is ProjectOverviewEvent.OnTemplateClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.CREATION + "?projectTemplateId=${event.id}"))
            }

        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.emit(event)
        }
    }

    private fun getProjectPreviews(): Flow<List<ProjectPreview>> {
        if (!repository.projectHandler.previewsInitialized) {
            viewModelScope.launch {
                repository.projectHandler.initializePreviews()
            }
        }
        return repository.projectHandler.getProjectPreviews()
    }

    private fun getTemplatePreviews(): Flow<List<ProjectTemplatePreview>> {
        if (!repository.projectHandler.previewsInitialized) {
            viewModelScope.launch {
                repository.projectHandler.initializePreviews()
            }
        }
        return repository.projectHandler.getProjectTemplatePreviews()
    }
}