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

package com.pseandroid2.dailydata.ui.templates

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pseandroid2.dailydata.ui.composables.TopNavigationBar
import com.pseandroid2.dailydata.util.ui.UiEvent
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
@Composable
fun TemplatesScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: TemplatesScreenViewModel = hiltViewModel()
) {
    val graphTemplates = viewModel.graphTemplates
    val projectTemplates = viewModel.projectTemplates.collectAsState(initial = listOf())

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when(event) {
                is UiEvent.Navigate -> onNavigate(event)
                else -> { }
            }
        }
    }
    Column {
        TopNavigationBar(
            items = viewModel.tabs.map { it.representation },
            indexCurrentTab = viewModel.tab,
            onItemClick = {
                viewModel.onEvent(TemplatesScreenEvent.OnTabChange(it))
            }
        )
        when(viewModel.tabs[viewModel.tab]) {
            TemplateTabs.GRAPHS -> {
                LazyColumn {
                    items(graphTemplates) { template ->
                        TemplatesCard(
                            title = template.title,
                            image = template.image.asImageBitmap(),
                            onIconClick = {
                                viewModel.onEvent(TemplatesScreenEvent.OnGraphTemplateDelete(template.id))
                            }
                        )
                    }
                }
            }
            TemplateTabs.PROJECTS -> {
                LazyColumn {
                    items(projectTemplates.value) { template ->
                        TemplatesCard(
                            title = template.titel,
                            image = template.image.asImageBitmap(),
                            onIconClick = {
                                viewModel.onEvent(TemplatesScreenEvent.OnProjectTemplateDelete(template.id))
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TemplatesCard(
    title : String,
    image : ImageBitmap,
    onIconClick : () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(20.dp)
            .width(400.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = 10.dp
    ) {
        Column (
            modifier = Modifier.padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ){

            Image(
                bitmap = image,
                contentDescription = "Text 2",
                modifier = Modifier
                    .fillMaxWidth()
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = title)
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "",
                    modifier = Modifier.clickable { onIconClick() }
                )
            }
        }
    }
}