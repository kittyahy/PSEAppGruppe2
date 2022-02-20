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

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.asImageBitmap
import androidx.hilt.navigation.compose.hiltViewModel
import com.pseandroid2.dailydata.ui.composables.PreviewCard
import com.pseandroid2.dailydata.ui.composables.TopNavigationBar
import kotlinx.coroutines.InternalCoroutinesApi

@Composable
fun TemplatesScreen(
    viewModel: TemplatesScreenViewModel = hiltViewModel()
) {
    var graphTemplates = viewModel.graphTemplates.collectAsState(initial = listOf())
    var projectTemplates = viewModel.projectTemplates.collectAsState(initial = listOf())

    Column {
        TopNavigationBar(
            items = viewModel.tabs.map { it.representation },
            indexCurrentTab = viewModel.tab,
            onItemClick = {
                viewModel.onEvent(TemplatesScreenEvent.OnTabChange(it))
            }
        )
        when (viewModel.tabs[viewModel.tab]) {
            TemplateTabs.GRAPHS -> {
                LazyColumn {
                    items(graphTemplates.value) { template ->
                        val image = template.image
                            if (image != null) {
                                PreviewCard(
                                    title = template.title,
                                    image = image.asImageBitmap(),
                                    imageClickable = false,
                                    onIconClick = { viewModel.onEvent(TemplatesScreenEvent.OnDeleteGraphTemplate(template)) },
                                    icon = Icons.Default.Delete
                                )
                            }


                    }
                }
            }
            TemplateTabs.PROJECTS -> {
                LazyColumn {
                    items(projectTemplates.value) { template ->
                        template.image?.let {
                            PreviewCard(
                                title = template.name,
                                image = it.asImageBitmap(),
                                imageClickable = false,
                                onIconClick = { viewModel.onEvent(TemplatesScreenEvent.OnDeleteProjectTemplate(template)) },
                                icon = Icons.Default.Delete
                            )
                        }
                    }
                }
            }
        }
    }
}