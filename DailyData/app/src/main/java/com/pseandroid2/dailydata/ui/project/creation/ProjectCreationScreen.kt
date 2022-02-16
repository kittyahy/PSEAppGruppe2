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

package com.pseandroid2.dailydata.ui.project.creation

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.pseandroid2.dailydata.R
import com.pseandroid2.dailydata.model.table.ColumnData
import com.pseandroid2.dailydata.model.uielements.UIElement
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.DataType
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Graph
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Notification
import com.pseandroid2.dailydata.ui.composables.ListInput
import com.pseandroid2.dailydata.ui.composables.SaveButton
import com.pseandroid2.dailydata.ui.composables.TextInput
import com.pseandroid2.dailydata.ui.composables.WallpaperElement
import com.pseandroid2.dailydata.util.ui.UiEvent
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
@Composable
fun ProjectCreationScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    onPopBackStack: () -> Unit,
    viewModel: ProjectCreationScreenViewModel = hiltViewModel()
) {

    val scrollState = rememberScrollState()
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowToast -> Toast.makeText(context, event.message, Toast.LENGTH_SHORT)
                    .show()
                is UiEvent.Navigate -> onNavigate(event)
                is UiEvent.PopBackStack -> onPopBackStack()
                else -> Unit
            }
        }
    }

    BackHandler(enabled = true) {
        viewModel.onEvent(ProjectCreationEvent.OnShowBackDialog(true))
    }
    BackDialog(
        isOpen = viewModel.isBackDialogOpen,
        onDismissRequest = { viewModel.onEvent(ProjectCreationEvent.OnShowBackDialog(false)) },
        onOkClick = { viewModel.onEvent(ProjectCreationEvent.OnNavigateBack) },
        onCancelClick = { viewModel.onEvent(ProjectCreationEvent.OnShowBackDialog(false)) }
    )

    Scaffold(
        floatingActionButton = {
            SaveButton(
                text = "Save",
                onClick = { viewModel.onEvent(ProjectCreationEvent.OnSaveClick) }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            TextInput(
                placeholder = "Add Title",
                value = viewModel.project.name,
                onValueChange = { viewModel.onEvent(ProjectCreationEvent.OnTitleChange(it)) }
            )
            Divider()
            TextInput(
                placeholder = "Add Description",
                value = viewModel.project.desc,
                onValueChange = { viewModel.onEvent(ProjectCreationEvent.OnDescriptionChange(it)) },
                singleLine = false,
                icon = ImageVector.vectorResource(id = R.drawable.ic_subject)
            )
            Divider()
            WallpaperDialog(
                isOpen = viewModel.isWallpaperDialogOpen,
                onDismissRequest = {
                    viewModel.onEvent(
                        ProjectCreationEvent.OnShowWallpaperDialog(
                            false
                        )
                    )
                },
                onWallpaperClick = { wallpaper ->
                    viewModel.onEvent(ProjectCreationEvent.OnWallpaperChange(wallpaper.value))
                    viewModel.onEvent(ProjectCreationEvent.OnShowWallpaperDialog(false))
                }
            )
            WallpaperElement(
                color = Color(viewModel.project.color),
                label = "Change Wallpaper",
                onClick = { viewModel.onEvent(ProjectCreationEvent.OnShowWallpaperDialog(true)) }
            )
            Divider()
            TableDialog(
                isOpen = viewModel.isTableDialogOpen,
                onDismissRequest = { viewModel.onEvent(ProjectCreationEvent.OnShowTableDialog(false)) },
                onClick = { name, unit, dataType ->
                    viewModel.onEvent(
                        ProjectCreationEvent.OnTableAdd(
                            name = name,
                            unit = unit,
                            dataType = dataType
                        )
                    )
                    viewModel.onEvent(ProjectCreationEvent.OnShowTableDialog(false))
                }
            )
            ListInput(
                label = "Add Table Column",
                mainIcon = ImageVector.vectorResource(id = R.drawable.ic_table),
                onClick = { viewModel.onEvent(ProjectCreationEvent.OnShowTableDialog(true)) },
                onClickItem = { _, col ->
                    viewModel.onEvent(
                        ProjectCreationEvent.OnTableRemove(
                            index = (col.first as ColumnData).id
                        )
                    )
                },
                elements = viewModel.project.table.layout.map {
                    Pair(
                        it,
                        "${it.name} in ${it.unit}"
                    )
                }
            )
            Divider()
            ButtonDialog(
                isOpen = viewModel.isButtonsDialogOpen,
                buttons = viewModel.project.table.layout.filter { it.type == DataType.WHOLE_NUMBER }
                    .map { Pair(it, it.name) },
                onDismissRequest = { viewModel.onEvent(ProjectCreationEvent.OnShowTableDialog(false)) },
                onClick = { name, columnData, value ->
                    viewModel.onEvent(
                        ProjectCreationEvent.OnButtonAdd(
                            name = name,
                            columnId = columnData.id,
                            value = value.toInt()
                        )
                    )
                    viewModel.onEvent(ProjectCreationEvent.OnShowButtonsDialog(false))
                }
            )
            val uiElements = mutableListOf<Pair<UIElement, Int>>()
            for (col in viewModel.project.table.layout) {
                for (uiElement in col.uiElements) {
                    uiElements.add(Pair(uiElement, col.id))
                }
            }
            ListInput(
                label = "Button",
                mainIcon = ImageVector.vectorResource(id = R.drawable.ic_button),
                onClick = {
                    viewModel.onEvent(
                        ProjectCreationEvent.OnShowButtonsDialog(true)
                    )
                },
                onClickItem = { _, element ->
                    @Suppress("Unchecked_Cast")
                    val uiElement = element as Pair<UIElement, Int>
                    viewModel.onEvent(
                        ProjectCreationEvent.OnButtonRemove(
                            uiElement.first.id,
                            uiElement.second
                        )
                    )
                },
                elements = uiElements.map { pair ->
                    Pair(
                        pair,
                        "${pair.first.name} in ${viewModel.project.table.layout[pair.second].name}"
                    )
                }
            )
            Divider()
            NotificationDialog(
                isOpen = viewModel.isNotificationDialogOpen,
                onDismissRequest = {
                    viewModel.onEvent(
                        ProjectCreationEvent.OnShowNotificationDialog(
                            false
                        )
                    )
                },
                onClick = { message, time ->
                    viewModel.onEvent(ProjectCreationEvent.OnNotificationAdd(message, time))
                    viewModel.onEvent(ProjectCreationEvent.OnShowNotificationDialog(false))
                }
            )
            ListInput(
                label = "Add Notification",
                mainIcon = Icons.Default.Notifications,
                onClick = { viewModel.onEvent(ProjectCreationEvent.OnShowNotificationDialog(true)) },
                onClickItem = { _, notif ->
                    viewModel.onEvent(
                        ProjectCreationEvent.OnNotificationRemove(
                            index = (notif.first as Notification).id
                        )
                    )
                },
                elements = viewModel.project.notifications.map { Pair(it, it.displayString) }
            )
            Divider()
            GraphDialog(
                isOpen = viewModel.isGraphDialogOpen,
                onDismissRequest = { viewModel.onEvent(ProjectCreationEvent.OnShowGraphDialog(false)) },
                onClick = { graph ->
                    viewModel.onEvent(ProjectCreationEvent.OnGraphAdd(Graph.createFromType(graph)))
                    viewModel.onEvent(ProjectCreationEvent.OnShowGraphDialog(false))
                }
            )
            ListInput(
                label = "Add Graph",
                mainIcon = ImageVector.vectorResource(id = R.drawable.ic_chart),
                onClick = { viewModel.onEvent(ProjectCreationEvent.OnShowGraphDialog(true)) },
                onClickItem = { viewModel.onEvent(ProjectCreationEvent.OnGraphRemove(index = it)) },
                elements = viewModel.project.graphs.map {
                    Pair(
                        it.getType(),
                        it.getType().representation
                    )
                }
            )
        }
    }
}