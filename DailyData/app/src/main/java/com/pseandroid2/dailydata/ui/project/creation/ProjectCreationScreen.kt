package com.pseandroid2.dailydata.ui.project.creation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pse.util.ui.UiEvent
import com.example.pse.util.ui.Wallpapers
import com.pseandroid2.dailydata.R
import com.pseandroid2.dailydata.ui.composables.EnumDropDownMenu
import com.pseandroid2.dailydata.ui.composables.ListInput
import com.pseandroid2.dailydata.ui.composables.SaveButton
import com.pseandroid2.dailydata.ui.composables.TextInput
import com.pseandroid2.dailydata.ui.composables.WallpaperElement
import com.pseandroid2.dailydata.util.ui.DataType
import kotlinx.coroutines.flow.collect
import java.lang.NumberFormatException

@Composable
fun ProjectCreationScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: ProjectCreationScreenViewModel = hiltViewModel()
) {

    val scrollState = rememberScrollState()

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when(event) {
                is UiEvent.Navigate -> {
                    
                }
                else -> { }
            }
        }
    }
    Scaffold(
        floatingActionButton = {
            SaveButton(
                text = "Save",
                onClick = { }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(0.dp)
                .verticalScroll(scrollState)
        ) {
            TextInput(
                placeholder = "Add Title",
                value = viewModel.title,
                onValueChange = { viewModel.onEvent(ProjectCreationEvent.OnTitleChange(it)) }

            )
            Divider()
            TextInput(
                placeholder = "Add Description",
                value = viewModel.description,
                onValueChange = { viewModel.onEvent(ProjectCreationEvent.OnDescriptionChange(it)) },
                singleLine = false,
                icon = ImageVector.vectorResource(id = R.drawable.ic_subject)
            )
            Divider()
            WallpaperDialog(viewModel = viewModel)
            WallpaperElement(
                color = viewModel.wallpaper,
                label = "Change Wallpaper",
                onClick = { viewModel.onEvent(ProjectCreationEvent.OnShowWallpaperDialog(true)) }
            )
            Divider()
            TableDialog(viewModel = viewModel)
            ListInput(
                label = "Add Table Column",
                mainIcon = ImageVector.vectorResource(id = R.drawable.ic_table),
                onClick = { viewModel.onEvent(ProjectCreationEvent.OnShowTableDialog(true)) },
                onClickItem = { viewModel.onEvent(ProjectCreationEvent.OnTableRemove(index = it)) },
                elements = viewModel.table.map { "${it.name} in ${it.unit}" }
            )
            Divider()
            ButtonDialog(viewModel = viewModel)
            ListInput(
                label = "Button",
                mainIcon = ImageVector.vectorResource(id = R.drawable.ic_button),
                onClick = { viewModel.onEvent(ProjectCreationEvent.OnShowButtonsDialog(true)) },
                onClickItem = { viewModel.onEvent(ProjectCreationEvent.OnButtonRemove(index = it)) },
                elements = viewModel.buttons.map { "${it.name} in ${it.column.name}" }
            )
            Divider()
            NotificationDialog(viewModel = viewModel)
            ListInput(
                label = "Add Notification",
                mainIcon = Icons.Default.Notifications,
                onClick = { viewModel.onEvent(ProjectCreationEvent.OnShowNotificationDialog(true)) },
                onClickItem = { },
                elements = listOf("07:30", "09:00")
            )
            Divider()
            GraphDialog(viewModel = viewModel)
            ListInput(
                label = "Add Graph",
                mainIcon = ImageVector.vectorResource(id = R.drawable.ic_chart),
                onClick = { viewModel.onEvent(ProjectCreationEvent.OnShowGraphDialog(true)) },
                onClickItem = {},
                elements = listOf("Pie chart", "line chart")
            )
        }
    }
}

@Composable
fun WallpaperDialog(viewModel: ProjectCreationScreenViewModel) {
    if(viewModel.isWallpaperDialogOpen) {
        Dialog(
            onDismissRequest = { viewModel.onEvent(ProjectCreationEvent.OnShowWallpaperDialog(false)) }
        ) {
            Surface(
                shape = RoundedCornerShape(5.dp),
                color = MaterialTheme.colors.background,
                modifier = Modifier.padding(10.dp)
            ) {
                Column(modifier = Modifier.width(200.dp)) {
                    for (wallpaper in Wallpapers.values()) {
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp)
                                .clickable {
                                    viewModel.onEvent(
                                        ProjectCreationEvent.OnWallpaperChange(
                                            wallpaper.value
                                        )
                                    )
                                    viewModel.onEvent(
                                        ProjectCreationEvent.OnShowWallpaperDialog(
                                            false
                                        )
                                    )
                                },
                        color = wallpaper.value
                        ) {

                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TableDialog(viewModel: ProjectCreationScreenViewModel) {

    if(viewModel.isTableDialogOpen) {
        var name by remember { mutableStateOf("") }
        var nameError by remember { mutableStateOf(false) }
        var unit by remember { mutableStateOf("") }
        var unitError by remember { mutableStateOf(false) }

        val suggestions = DataType.values().toList().map { it.representation }
        var dataType by remember { mutableStateOf(suggestions.first()) }

        Dialog(
            onDismissRequest = { viewModel.onEvent(ProjectCreationEvent.OnShowTableDialog(false)) }
        ) {
            Surface(
                shape = RoundedCornerShape(5.dp),
                color = MaterialTheme.colors.background,
                modifier = Modifier.padding(10.dp)
            ) {
                Column(
                    modifier = Modifier
                        .width(300.dp),
                    horizontalAlignment = Alignment.End
                ) {
                    TextField(
                        label = { Text("Name") },
                        value = name,
                        isError = nameError,
                        onValueChange = {
                            name = it
                            nameError = false
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.padding(10.dp))
                    TextField(
                        label = { Text("Unit") },
                        value = unit,
                        isError = unitError,
                        onValueChange = {
                            unit = it
                            unitError = false
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.padding(10.dp))
                    EnumDropDownMenu(
                        suggestions = suggestions,
                        value = dataType,
                        onClick = { dataType = suggestions[it] }
                    )
                    Spacer(modifier = Modifier.padding(10.dp))
                    Button(
                        onClick = {
                            if(unit.isBlank()) {
                                unitError = true
                            }
                            if(name.isBlank()) {
                                nameError = true
                            }
                            if(!unitError && !nameError) {
                                viewModel.onEvent(ProjectCreationEvent.OnTableAdd(name = name, unit = unit, dataType = DataType.fromString(dataType)))
                                viewModel.onEvent(ProjectCreationEvent.OnShowTableDialog(false))
                            }
                        }
                    ) {
                        Text("Add")
                    }
                }
            }
        }
    }
}


@Composable
fun ButtonDialog(viewModel: ProjectCreationScreenViewModel) {
    if(viewModel.isButtonsDialogOpen) {
        var name by remember { mutableStateOf("") }
        var nameError by remember { mutableStateOf(false) }
        var value by remember { mutableStateOf("") }
        var valueError by remember { mutableStateOf(false) }

        val suggestions = viewModel.table.filter { it.dataType == DataType.WHOLE_NUMBER }.map { it.name }
        var column by remember { mutableStateOf(0) }
        Dialog(
            onDismissRequest = { viewModel.onEvent(ProjectCreationEvent.OnShowTableDialog(false)) }
        ) {
            Surface(
                shape = RoundedCornerShape(5.dp),
                color = MaterialTheme.colors.background,
                modifier = Modifier.padding(10.dp)
            ) {
                Column(
                    modifier = Modifier
                        .width(300.dp),
                    horizontalAlignment = Alignment.End
                ) {
                    TextField(
                        label = { Text("Name") },
                        value = name,
                        isError = nameError,
                        onValueChange = {
                            name = it
                            nameError = false
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.padding(10.dp))
                    TextField(
                        label = { Text("Unit") },
                        value = value,
                        isError = valueError,
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        onValueChange = {
                            value = it
                            valueError = false
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.padding(10.dp))
                    EnumDropDownMenu(
                        suggestions = suggestions,
                        value = suggestions[column],
                        onClick = {
                            column = it
                        }
                    )
                    Spacer(modifier = Modifier.padding(10.dp))
                    Button(
                        onClick = {
                            try {
                                value.toInt()
                            } catch (e : NumberFormatException) {
                                valueError = true
                            }
                            if(name.isBlank()) {
                                nameError = true
                            }
                            if(!valueError && !nameError) {
                                viewModel.onEvent(
                                    ProjectCreationEvent.OnButtonAdd(
                                    name = name,
                                    columnId = viewModel.table.filter { it.dataType == DataType.WHOLE_NUMBER }[column].id,
                                    value = value.toInt()
                                ))
                                viewModel.onEvent(ProjectCreationEvent.OnShowButtonsDialog(false))
                            }
                        }
                    ) {
                        Text("Add")
                    }
                }
            }
        }
    }
}

@Composable
fun NotificationDialog(viewModel: ProjectCreationScreenViewModel) {

}

@Composable
fun GraphDialog(viewModel: ProjectCreationScreenViewModel) {

}