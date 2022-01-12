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

import android.media.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pseandroid2.dailydata.util.ui.UiEvent
import kotlinx.coroutines.flow.collect

@Composable
fun ProjectOverviewScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: ProjectOverviewViewModel = hiltViewModel()
) {

    val projects = viewModel.projects.collectAsState(initial = emptyList())

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when(event) {
                is UiEvent.Navigate -> onNavigate(event)
                is UiEvent.PopBackStack -> {

                }
            }
        }
    }

    Column(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        ProjectRepresentationButton(
            text = "Add new Project",
            icon = Icons.Default.Add,
            onClick = {
                viewModel.onEvent(ProjectOverviewEvent.OnNewProjectClick)
            }
        )

        for(project in projects.value) {
            ProjectRepresentationButton(
                text = "Project 1",
                onClick = {                                         //project.id
                    viewModel.onEvent(ProjectOverviewEvent.OnProjectClick(1))
                }
            )
        }
        ProjectRepresentationButton(
            text = "Project 1",
            onClick = {
                viewModel.onEvent(ProjectOverviewEvent.OnProjectClick(1))
            }
        )
    }
}

@Composable
fun ProjectRepresentationButton(
    text: String,
    background: Image? = null,
    icon : ImageVector = Icons.Default.List,
    onClick : () -> Unit
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.primary,
            contentColor = MaterialTheme.colors.onPrimary
        ),
        onClick = onClick
    ) {
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = text, fontSize = 18.sp)
            Icon(
                imageVector = icon,
                contentDescription = "",
                tint = MaterialTheme.colors.onPrimary
            )
        }
    }
}