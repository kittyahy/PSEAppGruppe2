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

package com.pseandroid2.dailydata.ui.project.data

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.pseandroid2.dailydata.ui.composables.TopNavigationBar
import com.pseandroid2.dailydata.ui.project.data.graph.ProjectDataGraphScreen
import com.pseandroid2.dailydata.ui.project.data.input.ProjectDataInputScreen
import com.pseandroid2.dailydata.ui.project.data.settings.ProjectDataSettingsScreen
import com.pseandroid2.dailydata.util.ui.UiEvent
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
@Composable
fun ProjectDataScreen(
    onPopBackStack : () -> Unit,
    viewModel: ProjectDataScreenViewModel = hiltViewModel()
) {

    Scaffold(
        topBar = {
            TopNavigationBar(
                items = viewModel.tabs.map { it.representation },
                indexCurrentTab = viewModel.tab,
                onItemClick = {
                    viewModel.onEvent(ProjectDataScreenEvent.OnTabChange(it))
                }
            )
        }
    ) {
        Box(
            modifier = Modifier.padding(it)
        ) {
            val onNavigate : (UiEvent.Navigate) -> Unit = { tab ->
                val index = DataTabs.values().indexOf(DataTabs.valueOf(tab.route))
                viewModel.onEvent(ProjectDataScreenEvent.OnTabChange(index))
            }

            when(viewModel.tabs[viewModel.tab]) {
                DataTabs.GRAPHS     -> ProjectDataGraphScreen(projectId = viewModel.projectId)
                DataTabs.INPUT      -> ProjectDataInputScreen(projectId = viewModel.projectId, onNavigate = onNavigate)
                DataTabs.SETTINGS   -> ProjectDataSettingsScreen(
                    projectId = viewModel.projectId,
                    onNavigate = onNavigate,
                    onPopBackStack = onPopBackStack
                )
            }
        }
    }
}