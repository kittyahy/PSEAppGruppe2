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

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pseandroid2.dailydata.ui.project.creation.AppDialog
import com.pseandroid2.dailydata.util.ui.UiEvent
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
@Composable
fun ProjectOverviewScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: ProjectOverviewViewModel = hiltViewModel()
) {

    val projects by viewModel.projects.collectAsState(initial = emptyList())
    val templates by viewModel.templates.collectAsState(initial = emptyList())

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when(event) {
                is UiEvent.Navigate -> onNavigate(event)
                else -> { }
            }
        }
    }

    ProjectTemplateDialog(
        isOpen = viewModel.isTemplateDialogOpen,
        onDismissRequest = { viewModel.onEvent(ProjectOverviewEvent.OnTemplateProjectClick(isOpen = false)) },
        templates = templates.map { TODO("Get Template Names") },
        onClick = {
            TODO("Get template id")
            viewModel.onEvent(ProjectOverviewEvent.OnTemplateClick(id = it))
            viewModel.onEvent(ProjectOverviewEvent.OnTemplateProjectClick(isOpen = false))
        }
    )
    Column(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(18.dp, alignment = Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProjectRepresentationButton(
            text = "Add new Project",
            icon = Icons.Default.Add,
            onClick = { viewModel.onEvent(ProjectOverviewEvent.OnNewProjectClick) }
        )

        ProjectRepresentationButton(
            text = "Project from Template",
            icon = Icons.Default.Add,
            onClick = { viewModel.onEvent(ProjectOverviewEvent.OnTemplateProjectClick(isOpen = true)) }
        )

        for(project in projects) {
            ProjectRepresentationButton(
                text = project.name,
                onClick = { viewModel.onEvent(ProjectOverviewEvent.OnProjectClick(project.id)) }
            )
        }
    }
}

@Composable
fun ProjectTemplateDialog(
    isOpen : Boolean,
    onDismissRequest : () -> Unit,
    templates : List<String>,
    onClick: (Int) -> Unit
) {
    AppDialog(isOpen = isOpen, onDismissRequest = onDismissRequest) {
        LazyColumn(
            modifier = Modifier
                .sizeIn(
                    minHeight = 0.dp,
                    maxHeight = 1000.dp,
                    minWidth = 0.dp,
                    maxWidth = 200.dp
                )
                .padding(8.dp)
        ) {
            itemsIndexed(templates) { index, template ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .clickable { onClick(index) },
                    contentAlignment = Alignment.CenterStart

                ) {
                    Text(text = template)
                }
                if (index < templates.lastIndex)
                    Divider()
            }
        }
    }
}

@Composable
fun ProjectRepresentationButton(
    text: String,
    icon : ImageVector = Icons.Default.List,
    onClick : () -> Unit
) {
    OutlinedButton(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick,
        border = BorderStroke(width = 1.dp, SolidColor(MaterialTheme.colors.primary)),
        shape = RectangleShape,
        contentPadding = PaddingValues(16.dp)
    ) {
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text, 
                fontSize = 20.sp
            )
            Icon(
                modifier = Modifier.size(30.dp),
                imageVector = icon,
                contentDescription = "",
                tint = MaterialTheme.colors.primaryVariant
            )
        }
    }
}