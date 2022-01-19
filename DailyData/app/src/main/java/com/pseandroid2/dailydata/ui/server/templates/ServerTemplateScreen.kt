package com.pseandroid2.dailydata.ui.server.templates

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.pseandroid2.dailydata.ui.composables.ProjectTemplateDialog
import com.pseandroid2.dailydata.ui.composables.ServerCard
import com.pseandroid2.dailydata.ui.composables.TopNavigationBar
import com.pseandroid2.dailydata.util.ui.UiEvent
import kotlinx.coroutines.flow.collect


@Composable
fun ServerTemplatesScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: ServerTemplateScreenViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when(event) {
                is UiEvent.Navigate -> onNavigate(event)
                else -> { }
            }
        }
    }

    Column() {
        TopNavigationBar(
            items = viewModel.tabs.map { it.representation },
            indexCurrentTab = viewModel.tab,
            onItemClick = {
                viewModel.onEvent(ServerTemplateScreenEvent.OnTabChange(it))
            }
        )
        when(viewModel.tabs[viewModel.tab]) {
            ServerTabs.GRAPHS -> {
                LazyColumn {
                    itemsIndexed(viewModel.graphTemplates) { index, template ->
                        ServerCard(
                            title = template.title,
                            image = painterResource(id = template.image),
                            imageClickable = false,
                            onIconClick = {
                                viewModel.onEvent(ServerTemplateScreenEvent.OnGraphTemplateDownload(index))
                            }
                        )
                    }
                }
            }
            ServerTabs.PROJECTS -> {
                ProjectTemplateDialog(
                    isOpen = viewModel.isProjectTemplateDialogOpen,
                    onDismissRequest = { viewModel.onEvent(ServerTemplateScreenEvent.OnShowProjectTemplateDialog(index = 0, isOpen = false)) },
                    onIconClick = { viewModel.onEvent(ServerTemplateScreenEvent.OnProjectGraphTemplateDownload(graphIndex = it)) },
                    template = viewModel.projectTemplates[viewModel.projectTemplateIndex]
                )
                LazyColumn {
                    itemsIndexed(viewModel.projectTemplates) { index, template ->
                        ServerCard(
                            title = template.title,
                            image = painterResource(id = template.image),
                            onIconClick = {
                                viewModel.onEvent(ServerTemplateScreenEvent.OnGraphTemplateDownload(index))
                            },
                            imageClickable = true,
                            onImageClick = {
                                viewModel.onEvent(ServerTemplateScreenEvent.OnShowProjectTemplateDialog(index = index, isOpen = true))
                            }
                        )
                    }
                }
            }
        }
    }
}