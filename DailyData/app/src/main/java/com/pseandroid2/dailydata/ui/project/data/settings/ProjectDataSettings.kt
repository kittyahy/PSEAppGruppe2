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

package com.pseandroid2.dailydata.ui.project.data.settings

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.hilt.navigation.compose.hiltViewModel
import com.pseandroid2.dailydata.R
import com.pseandroid2.dailydata.model.graph.Graph.Companion.LINE_CHART_STR
import com.pseandroid2.dailydata.model.graph.GraphType
import com.pseandroid2.dailydata.model.table.ColumnData
import com.pseandroid2.dailydata.model.uielements.UIElement
import com.pseandroid2.dailydata.model.users.User
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.DataType
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Notification
import com.pseandroid2.dailydata.ui.composables.ButtonElement
import com.pseandroid2.dailydata.ui.composables.ListInput
import com.pseandroid2.dailydata.ui.composables.SaveButton
import com.pseandroid2.dailydata.ui.composables.TextInput
import com.pseandroid2.dailydata.ui.composables.WallpaperElement
import com.pseandroid2.dailydata.ui.project.creation.BackDialog
import com.pseandroid2.dailydata.ui.project.creation.ButtonDialog
import com.pseandroid2.dailydata.ui.project.creation.GraphDialog
import com.pseandroid2.dailydata.ui.project.creation.MappingDialog
import com.pseandroid2.dailydata.ui.project.creation.NotificationDialog
import com.pseandroid2.dailydata.ui.project.creation.TableDialog
import com.pseandroid2.dailydata.ui.project.creation.WallpaperDialog
import com.pseandroid2.dailydata.ui.project.creation.XAxisSelectionDialog
import com.pseandroid2.dailydata.util.ui.UiEvent
import kotlinx.coroutines.InternalCoroutinesApi

@SuppressLint("ServiceCast")
@InternalCoroutinesApi
@Composable
fun ProjectDataSettingsScreen(
    projectId: Int,
    onNavigate: (UiEvent.Navigate) -> Unit,
    onPopBackStack: () -> Unit,
    viewModel: ProjectDataSettingsScreenViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        //This screen can only be opened for projects that have been created and saved to the database already
        viewModel.initialize(projectId)
        //viewModel.onEvent(ProjectDataSettingsScreenEvent.OnCreate(projectId = projectId))
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowToast -> Toast.makeText(context, event.message, Toast.LENGTH_SHORT)
                    .show()
                is UiEvent.Navigate -> onNavigate(event)
                is UiEvent.PopBackStack -> onPopBackStack()
                is UiEvent.CopyToClipboard -> {
                    val clipboardManager =
                        context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    clipboardManager.setText(AnnotatedString(text = event.message))
                    Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    BackHandler(enabled = true) {
        viewModel.onEvent(ProjectDataSettingsScreenEvent.OnShowBackDialog(true))
    }
    BackDialog(
        isOpen = viewModel.isBackDialogOpen,
        onDismissRequest = { viewModel.onEvent(ProjectDataSettingsScreenEvent.OnShowBackDialog(false)) },
        onOkClick = { viewModel.onEvent(ProjectDataSettingsScreenEvent.OnNavigateBack) },
        onCancelClick = { viewModel.onEvent(ProjectDataSettingsScreenEvent.OnShowBackDialog(false)) }
    )

    Scaffold(
        floatingActionButton = {
            SaveButton(
                text = "Save",
                onClick = { viewModel.onEvent(ProjectDataSettingsScreenEvent.OnSaveClick) }
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
                value = viewModel.title,
                onValueChange = { viewModel.onEvent(ProjectDataSettingsScreenEvent.OnTitleChange(it)) }

            )
            Divider()
            TextInput(
                placeholder = "Add Description",
                value = viewModel.description,
                onValueChange = {
                    viewModel.onEvent(
                        ProjectDataSettingsScreenEvent.OnDescriptionChange(
                            it
                        )
                    )
                },
                singleLine = false,
                icon = ImageVector.vectorResource(id = R.drawable.ic_subject)
            )
            Divider()
            WallpaperDialog(
                isOpen = viewModel.isWallpaperDialogOpen,
                onDismissRequest = {
                    viewModel.onEvent(
                        ProjectDataSettingsScreenEvent.OnShowWallpaperDialog(
                            false
                        )
                    )
                },
                onWallpaperClick = { wallpaper ->
                    viewModel.onEvent(ProjectDataSettingsScreenEvent.OnWallpaperChange(wallpaper.value))
                    viewModel.onEvent(ProjectDataSettingsScreenEvent.OnShowWallpaperDialog(false))
                }
            )
            WallpaperElement(
                color = viewModel.wallpaper,
                label = "Change Wallpaper",
                onClick = {
                    viewModel.onEvent(
                        ProjectDataSettingsScreenEvent.OnShowWallpaperDialog(
                            true
                        )
                    )
                }
            )
            if (viewModel.isAdmin) {
                ListInput(
                    label = "Create Link",
                    mainIcon = Icons.Default.AccountCircle,
                    onClick = { viewModel.onEvent(ProjectDataSettingsScreenEvent.OnCreateLink) },
                    onClickItem = { _, user ->
                        viewModel.onEvent(
                            ProjectDataSettingsScreenEvent.OnMemberRemove((user.first as User).getId())
                        )
                    },
                    elements = viewModel.project.value!!.users.map {
                        Pair(it, "${it.getName()} #${it.getId()}")
                    } //TODO("Repository if the user is listed : make function to get user")
                )
            }
            Divider()
            TableDialog(
                isOpen = viewModel.isTableDialogOpen,
                onDismissRequest = {
                    viewModel.onEvent(
                        ProjectDataSettingsScreenEvent.OnShowTableDialog(
                            false
                        )
                    )
                },
                onClick = { name, unit, dataType ->
                    viewModel.onEvent(
                        ProjectDataSettingsScreenEvent.OnTableAdd(
                            name = name,
                            unit = unit,
                            dataType = dataType
                        )
                    )
                    viewModel.onEvent(ProjectDataSettingsScreenEvent.OnShowTableDialog(false))
                }
            )
            if (viewModel.isAdmin) {
                ListInput(
                    label = "Add Table Column",
                    mainIcon = ImageVector.vectorResource(id = R.drawable.ic_table),
                    onClick = {
                        viewModel.onEvent(
                            ProjectDataSettingsScreenEvent.OnShowTableDialog(
                                true
                            )
                        )
                    },
                    onClickItem = { _, col ->
                        @Suppress("Unchecked_Cast")
                        val columnData = col.first as ColumnData
                        viewModel.onEvent(
                            ProjectDataSettingsScreenEvent.OnTableRemove(columnData.id)
                        )
                    },
                    elements = viewModel.project.value!!.table.layout.map {
                        Pair(it, "${it.name} in ${it.unit}")
                    }
                )
            }
            Divider()
            ButtonDialog(
                isOpen = viewModel.isButtonsDialogOpen,
                buttons = viewModel.project.value!!.table.layout.filter { it.type == DataType.WHOLE_NUMBER }
                    .map { Pair(it, it.name) },
                onDismissRequest = {
                    viewModel.onEvent(
                        ProjectDataSettingsScreenEvent.OnShowTableDialog(false)
                    )
                }
            ) { name, column, value ->
                viewModel.onEvent(
                    ProjectDataSettingsScreenEvent.OnButtonAdd(
                        name = name,
                        columnId = column.id,
                        value = value.toInt()
                    )
                )
                viewModel.onEvent(ProjectDataSettingsScreenEvent.OnShowButtonsDialog(false))
            }
            val uiElements = mutableListOf<Pair<UIElement, Int>>()
            for (col in viewModel.project.value!!.table.layout) {
                for (uiElement in col.uiElements) {
                    uiElements.add(Pair(uiElement, col.id))
                }
            }
            ListInput(
                label = "Button",
                mainIcon = ImageVector.vectorResource(id = R.drawable.ic_button),
                onClick = {
                    viewModel.onEvent(
                        ProjectDataSettingsScreenEvent.OnShowButtonsDialog(true)
                    )
                },
                onClickItem = { _, element ->
                    @Suppress("Unchecked_Cast")
                    val uiElement = element as Pair<UIElement, Int>
                    viewModel.onEvent(
                        ProjectDataSettingsScreenEvent.OnButtonRemove(
                            uiElement.first.id,
                            uiElement.second
                        )
                    )
                },
                elements = uiElements.map { pair ->
                    Pair(
                        pair,
                        "${pair.first.name} in ${viewModel.project.value!!.table.layout[pair.second].name}"
                    )
                }
            )
            Divider()
            NotificationDialog(
                isOpen = viewModel.isNotificationDialogOpen,
                onDismissRequest = {
                    viewModel.onEvent(
                        ProjectDataSettingsScreenEvent.OnShowNotificationDialog(
                            false
                        )
                    )
                },
                onClick = { message, time ->
                    viewModel.onEvent(
                        ProjectDataSettingsScreenEvent.OnNotificationAdd(
                            message,
                            time
                        )
                    )
                    viewModel.onEvent(
                        ProjectDataSettingsScreenEvent.OnShowNotificationDialog(
                            false
                        )
                    )
                }
            )
            ListInput(
                label = "Add Notification",
                mainIcon = Icons.Default.Notifications,
                onClick = {
                    viewModel.onEvent(
                        ProjectDataSettingsScreenEvent.OnShowNotificationDialog(
                            true
                        )
                    )
                },
                onClickItem = { _, notif ->
                    viewModel.onEvent(
                        ProjectDataSettingsScreenEvent.OnNotificationRemove(
                            id = (notif.first as Notification).id
                        )
                    )
                },
                elements = viewModel.notifications.map { Pair(it, it.displayString) }
            )
            Divider()
            XAxisSelectionDialog(
                isOpen = viewModel.isXAxisDialogOpen,
                onDismissRequest = {
                    viewModel.onEvent(
                        ProjectDataSettingsScreenEvent.OnShowXAxisDialog(false)
                    )
                },
                onClick = { col ->
                    viewModel.onEvent(
                        ProjectDataSettingsScreenEvent.OnChoseXAxis(col)
                    )
                    viewModel.onEvent(ProjectDataSettingsScreenEvent.OnShowXAxisDialog(false))
                },
                columns = viewModel.project.value!!.table.layout
            )
            MappingDialog(
                isOpen = viewModel.isMappingDialogOpen,
                onDismissRequest = {
                    viewModel.onEvent(
                        ProjectDataSettingsScreenEvent.OnShowMappingDialog(false)
                    )
                },
                onClick = { mapping ->
                    viewModel.onEvent(ProjectDataSettingsScreenEvent.OnChoseMapping(mapping))
                    viewModel.onEvent(
                        ProjectDataSettingsScreenEvent.OnShowMappingDialog(
                            isOpen = false,
                            hasSuccessfullyChosen = true
                        )
                    )
                },
                layout = viewModel.project.value!!.table.layout
            )
            GraphDialog(
                isOpen = viewModel.isGraphDialogOpen,
                onDismissRequest = {
                    viewModel.onEvent(
                        ProjectDataSettingsScreenEvent.OnShowGraphDialog(false)
                    )
                },
                onClick = { graphType ->
                    viewModel.onEvent(ProjectDataSettingsScreenEvent.OnChoseGraphType(graphType))
                    if (graphType == LINE_CHART_STR) {
                        viewModel.onEvent(ProjectDataSettingsScreenEvent.OnShowXAxisDialog(true))
                    } else {
                        viewModel.onEvent(
                            ProjectDataSettingsScreenEvent.OnShowMappingDialog(true)
                        )
                    }
                    viewModel.onEvent(
                        ProjectDataSettingsScreenEvent.OnGraphAdd(
                            GraphType.getTypeForRep(graphType)
                        )
                    )
                    viewModel.onEvent(ProjectDataSettingsScreenEvent.OnShowGraphDialog(false))
                }
            )
            ListInput(
                label = "Add Graph",
                mainIcon = ImageVector.vectorResource(id = R.drawable.ic_chart),
                onClick = {
                    viewModel.onEvent(
                        ProjectDataSettingsScreenEvent.OnShowGraphDialog(
                            true
                        )
                    )
                },
                onClickItem = {
                    viewModel.onEvent(
                        ProjectDataSettingsScreenEvent.OnGraphRemove(
                            index = it
                        )
                    )
                },
                elements = viewModel.graphs.map { it.typeName }
            )
            ButtonElement(
                icon = Icons.Default.ExitToApp,
                label = "Leave Project",
                onClick = { viewModel.onEvent(ProjectDataSettingsScreenEvent.OnLeaveProject) }
            )
            ButtonElement(
                icon = Icons.Default.Delete,
                label = "Delete Project",
                onClick = { viewModel.onEvent(ProjectDataSettingsScreenEvent.OnDeleteProject) }
            )
        }
    }
}
}
