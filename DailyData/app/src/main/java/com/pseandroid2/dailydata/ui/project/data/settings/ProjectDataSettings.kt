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
import android.content.ClipData
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.hilt.navigation.compose.hiltViewModel
import com.pseandroid2.dailydata.util.ui.UiEvent
import com.pseandroid2.dailydata.R
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.DataType
import com.pseandroid2.dailydata.ui.composables.ButtonElement
import com.pseandroid2.dailydata.ui.composables.ListInput
import com.pseandroid2.dailydata.ui.composables.SaveButton
import com.pseandroid2.dailydata.ui.composables.TextInput
import com.pseandroid2.dailydata.ui.composables.WallpaperElement
import com.pseandroid2.dailydata.ui.project.creation.BackDialog
import com.pseandroid2.dailydata.ui.project.creation.ButtonDialog
import com.pseandroid2.dailydata.ui.project.creation.GraphDialog
import com.pseandroid2.dailydata.ui.project.creation.NotificationDialog
import com.pseandroid2.dailydata.ui.project.creation.TableDialog
import com.pseandroid2.dailydata.ui.project.creation.WallpaperDialog
import kotlinx.coroutines.InternalCoroutinesApi

@SuppressLint("ServiceCast")
@InternalCoroutinesApi
@Composable
fun ProjectDataSettingsScreen(
    projectId : Int,
    onNavigate: (UiEvent.Navigate) -> Unit,
    onPopBackStack : () -> Unit,
    viewModel: ProjectDataSettingsScreenViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.onEvent(ProjectDataSettingsScreenEvent.OnCreate(projectId = projectId))
        viewModel.uiEvent.collect { event ->
            when(event) {
                is UiEvent.ShowToast -> Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                is UiEvent.Navigate -> onNavigate(event)
                is UiEvent.PopBackStack -> onPopBackStack()
                is UiEvent.CopyToClipboard -> {
                    val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
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
                onValueChange = { viewModel.onEvent(ProjectDataSettingsScreenEvent.OnDescriptionChange(it)) },
                singleLine = false,
                icon = ImageVector.vectorResource(id = R.drawable.ic_subject)
            )
            Divider()
            WallpaperDialog(
                isOpen = viewModel.isWallpaperDialogOpen,
                onDismissRequest = { viewModel.onEvent(ProjectDataSettingsScreenEvent.OnShowWallpaperDialog(false)) },
                onWallpaperClick = { wallpaper ->
                    viewModel.onEvent(ProjectDataSettingsScreenEvent.OnWallpaperChange(wallpaper.value))
                    viewModel.onEvent(ProjectDataSettingsScreenEvent.OnShowWallpaperDialog(false))
                }
            )
            WallpaperElement(
                color = viewModel.wallpaper,
                label = "Change Wallpaper",
                onClick = { viewModel.onEvent(ProjectDataSettingsScreenEvent.OnShowWallpaperDialog(true)) }
            )
            if(viewModel.isAdmin) {
                ListInput(
                    label = "Create Link",
                    mainIcon = Icons.Default.AccountCircle,
                    onClick = { viewModel.onEvent(ProjectDataSettingsScreenEvent.OnCreateLink) },
                    onClickItem = { viewModel.onEvent(ProjectDataSettingsScreenEvent.OnMemberRemove(index = it)) },
                    elements = viewModel.members.map { "${it.name} #${it.id}" } //TODO("Repository if the user is listed : make function to get user")
                )
            }
            Divider()
            TableDialog(
                isOpen = viewModel.isTableDialogOpen,
                onDismissRequest = { viewModel.onEvent(ProjectDataSettingsScreenEvent.OnShowTableDialog(false)) },
                onClick = { name, unit, dataType ->
                    viewModel.onEvent(ProjectDataSettingsScreenEvent.OnTableAdd(name = name, unit = unit, dataType = dataType))
                    viewModel.onEvent(ProjectDataSettingsScreenEvent.OnShowTableDialog(false))
                }
            )
            if(viewModel.isAdmin) {
                ListInput(
                    label = "Add Table Column",
                    mainIcon = ImageVector.vectorResource(id = R.drawable.ic_table),
                    onClick = { viewModel.onEvent(ProjectDataSettingsScreenEvent.OnShowTableDialog(true)) },
                    onClickItem = { viewModel.onEvent(ProjectDataSettingsScreenEvent.OnTableRemove(index = it)) },
                    elements = viewModel.table.map { "${it.name} in ${it.unit}" }
                )
            }
            Divider()
            ButtonDialog(
                isOpen = viewModel.isButtonsDialogOpen,
                buttons = viewModel.table.filter { it.dataType == DataType.WHOLE_NUMBER }.map { it.name },
                onDismissRequest = { viewModel.onEvent(ProjectDataSettingsScreenEvent.OnShowTableDialog(false)) },
                onClick = { name, column, value ->
                    viewModel.onEvent(
                        ProjectDataSettingsScreenEvent.OnButtonAdd(
                            name = name,
                            columnId = viewModel.table.filter { it.dataType == DataType.WHOLE_NUMBER }[column].id,  //TODO(vielleicht nochmal ueberarbeiten)
                            value = value.toInt()
                        )
                    )
                    viewModel.onEvent(ProjectDataSettingsScreenEvent.OnShowButtonsDialog(false))
                }
            )
            ListInput(
                label = "Button",
                mainIcon = ImageVector.vectorResource(id = R.drawable.ic_button),
                onClick = { viewModel.onEvent(ProjectDataSettingsScreenEvent.OnShowButtonsDialog(true)) },
                onClickItem = { viewModel.onEvent(ProjectDataSettingsScreenEvent.OnButtonRemove(index = it)) },
                elements = viewModel.buttons.map { button ->
                    var columnName = viewModel.table.find { it.id == button.columnId} ?: ""
                    "${button.name} in ${columnName}"
                }
            )
            Divider()
            NotificationDialog(
                isOpen = viewModel.isNotificationDialogOpen,
                onDismissRequest = { viewModel.onEvent(ProjectDataSettingsScreenEvent.OnShowNotificationDialog(false)) },
                onClick = { message, time ->
                    viewModel.onEvent(ProjectDataSettingsScreenEvent.OnNotificationAdd(message, time))
                    viewModel.onEvent(ProjectDataSettingsScreenEvent.OnShowNotificationDialog(false))
                }
            )
            ListInput(
                label = "Add Notification",
                mainIcon = Icons.Default.Notifications,
                onClick = { viewModel.onEvent(ProjectDataSettingsScreenEvent.OnShowNotificationDialog(true)) },
                onClickItem = { viewModel.onEvent(ProjectDataSettingsScreenEvent.OnNotificationRemove(index = it)) },
                elements = viewModel.notifications.map { it.time.toString() }
            )
            Divider()
            GraphDialog(
                isOpen = viewModel.isGraphDialogOpen,
                onDismissRequest = { viewModel.onEvent(ProjectDataSettingsScreenEvent.OnShowGraphDialog(false)) },
                onClick = { graph ->
                  //  viewModel.onEvent(ProjectDataSettingsScreenEvent.OnGraphAdd(Graph.create(graph))) TODO(Repository create function)
                    viewModel.onEvent(ProjectDataSettingsScreenEvent.OnShowGraphDialog(false))
                }
            )
            ListInput(
                label = "Add Graph",
                mainIcon = ImageVector.vectorResource(id = R.drawable.ic_chart),
                onClick = { viewModel.onEvent(ProjectDataSettingsScreenEvent.OnShowGraphDialog(true)) },
                onClickItem = { viewModel.onEvent(ProjectDataSettingsScreenEvent.OnButtonRemove(index = it)) },
                elements = viewModel.graphs.map { TODO("it.representation") }
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