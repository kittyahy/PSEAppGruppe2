package com.pseandroid2.dailydata.ui.project.creation

import android.app.TimePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.pseandroid2.dailydata.model.graph.GraphType
import com.pseandroid2.dailydata.model.table.ColumnData
import com.pseandroid2.dailydata.model.table.TableLayout
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.DataType
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Graph
import com.pseandroid2.dailydata.ui.composables.EnumDropDownMenu
import com.pseandroid2.dailydata.util.ui.Wallpapers
import java.time.LocalTime
import java.util.Calendar

@Composable
fun AppDialog(
    isOpen: Boolean,
    onDismissRequest: () -> Unit,
    padding: Dp = 16.dp,
    Content: @Composable () -> Unit
) {
    if (isOpen) {
        Dialog(
            onDismissRequest = onDismissRequest
        ) {
            Card(
                shape = MaterialTheme.shapes.medium,
                elevation = 8.dp
            ) {
                Surface(modifier = Modifier.padding(padding)) {
                    Content()
                }
            }
        }
    }
}

@Composable
fun WallpaperDialog(
    isOpen: Boolean,
    onDismissRequest: () -> Unit,
    onWallpaperClick: (Wallpapers) -> Unit
) {
    AppDialog(
        isOpen = isOpen,
        onDismissRequest = onDismissRequest
    ) {
        Column(modifier = Modifier.width(200.dp)) {
            for (wallpaper in Wallpapers.values()) {
                Card(modifier = Modifier
                    .clickable { onWallpaperClick(wallpaper) }) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier
                                .size(40.dp)
                                .wrapContentSize(Alignment.CenterStart)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size((25.dp))
                                    .clip(CircleShape)
                                    .background(color = wallpaper.value)
                            )
                        }
                        Text(text = wallpaper.representation)
                    }
                }
            }
        }
    }
}

@Composable
fun TableDialog(
    isOpen: Boolean,
    onDismissRequest: () -> Unit,
    onClick: (String, String, DataType) -> Unit,
) {
    AppDialog(isOpen = isOpen, onDismissRequest = onDismissRequest) {

        var name by remember { mutableStateOf("") }
        var nameError by remember { mutableStateOf(false) }
        var unit by remember { mutableStateOf("") }
        var unitError by remember { mutableStateOf(false) }
        val suggestions = DataType.values().toList()
        var dataType by remember { mutableStateOf(suggestions.first()) }

        Column(
            modifier = Modifier
                .width(300.dp)
        ) {
            OutlinedTextField(
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
            OutlinedTextField(
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
                suggestions = suggestions.map { Pair(it, it.representation) },
                value = dataType.representation,
                onClick = { _, type ->
                    dataType = type.first as DataType
                }
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton(onClick = onDismissRequest) {
                    Text(text = "Cancel")
                }
                TextButton(onClick = {
                    if (unit.isBlank()) {
                        unitError = true
                    }
                    if (name.isBlank()) {
                        nameError = true
                    }
                    if (!unitError && !nameError) {
                        onClick(name, unit, dataType)
                    }
                }) {
                    Text(text = "OK")
                }
            }
        }
    }
}


@Composable
fun ButtonDialog(
    isOpen: Boolean,
    buttons: List<Pair<Any, String>>,
    onDismissRequest: () -> Unit,
    onClick: (String, Any, String) -> Unit
) {
    AppDialog(isOpen = isOpen, onDismissRequest = onDismissRequest) {
        var name by remember { mutableStateOf("") }
        var nameError by remember { mutableStateOf(false) }
        var value by remember { mutableStateOf("") }
        var valueError by remember { mutableStateOf(false) }

        var column by remember { mutableStateOf(0) }
        var selected by remember { mutableStateOf<Any?>(null) }

        Column(
            modifier = Modifier
                .width(300.dp)
        ) {
            OutlinedTextField(
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
            OutlinedTextField(
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
                suggestions = buttons,
                value = buttons[column].second,
                onClick = { col, value ->
                    column = col
                    selected = value
                }
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton(onClick = onDismissRequest) {
                    Text(text = "Cancel")
                }
                TextButton(onClick = {
                    try {
                        value.toInt()
                    } catch (e: NumberFormatException) {
                        valueError = true
                    }
                    if (name.isBlank()) {
                        nameError = true
                    }
                    if (!valueError && !nameError && selected != null) {
                        onClick(name, selected!!, value)
                    }
                }) {
                    Text(text = "OK")
                }
            }
        }
    }
}

@Composable
fun NotificationDialog(
    isOpen: Boolean,
    onDismissRequest: () -> Unit,
    onClick: (String, LocalTime) -> Unit
) {
    AppDialog(isOpen = isOpen, onDismissRequest = onDismissRequest) {
        var message by remember { mutableStateOf("") }
        var messageError by remember { mutableStateOf(false) }

        val currentTime = LocalTime.now()
        val context = LocalContext.current
        val calendar = Calendar.getInstance()
        val hour = calendar[Calendar.HOUR_OF_DAY]
        val minute = calendar[Calendar.MINUTE]
        var time by remember { mutableStateOf(LocalTime.of(currentTime.hour, currentTime.minute)) }
        val timePickerDialog = TimePickerDialog(
            context,
            { _, hour: Int, minute: Int ->
                time = LocalTime.of(hour, minute)
            }, hour, minute, true
        )
        Column(
            modifier = Modifier.width(300.dp)
        ) {
            OutlinedTextField(
                label = { Text("Name") },
                value = message,
                isError = messageError,
                onValueChange = {
                    message = it
                    messageError = false
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.padding(10.dp))
            OutlinedButton(
                onClick = { timePickerDialog.show() },
                modifier = Modifier.fillMaxWidth(),

                ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Left,
                    text = "Selected Time: $time"
                )
            }
            Spacer(modifier = Modifier.padding(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton(onClick = onDismissRequest) {
                    Text(text = "Cancel")
                }
                TextButton(onClick = {
                    if (message.isBlank()) {
                        messageError = true
                    } else {
                        onClick(message, time)
                    }
                }) {
                    Text(text = "OK")
                }
            }
        }
    }
}

@Composable
fun GraphDialog(
    isOpen: Boolean,
    onDismissRequest: () -> Unit,
    onClick: (String) -> Unit
) {
    AppDialog(isOpen = isOpen, onDismissRequest = onDismissRequest, padding = 0.dp) {
        Column(modifier = Modifier.width(200.dp)) {
            Graph.availableGraphs.forEachIndexed { index, graph ->
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .height(32.dp)
                        .clickable { onClick(graph) },
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(text = graph)
                }
                if (index < Graph.availableGraphs.lastIndex) {
                    Divider()
                }
            }
        }
    }
}

@Composable
fun XAxisSelectionDialog(
    isOpen: Boolean,
    onDismissRequest: () -> Unit,
    onClick: (Int) -> Unit,
    columns: TableLayout
) {
    AppDialog(isOpen = isOpen, onDismissRequest = onDismissRequest) {
        Column(modifier = Modifier.width(200.dp)) {
            columns.forEachIndexed { index, columnData ->
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .height(32.dp)
                        .clickable { onClick(columnData.id) }
                ) {
                    Text(text = columnData.name)
                }
                if (index < columns.size) {
                    Divider()
                }
            }
        }
    }
}

@Composable
fun MappingDialog(
    isOpen: Boolean,
    onDismissRequest: () -> Unit,
    onClick: (List<Int>) -> Unit,
    layout: TableLayout
) {
    val selected: MutableList<Int> = mutableListOf()
    AppDialog(isOpen = isOpen, onDismissRequest = { onDismissRequest }) {
        Column(modifier = Modifier.width(200.dp)) {
            layout.filter { columnData ->
                columnData.type == DataType.WHOLE_NUMBER
                        || columnData.type == DataType.FLOATING_POINT_NUMBER
            }.forEachIndexed { _, columnData ->
                Box(modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .height(32.dp)
                    .clickable { }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                    ) {
                        Checkbox(checked = false, onCheckedChange = { wasSelected ->
                            if (wasSelected) {
                                selected.add(columnData.id)
                            }
                        })
                        Text(text = columnData.name)
                    }
                }
            }
            Button(onClick = { onClick(selected) }) {

            }
        }
    }
}

@Composable
fun GraphNameDialog(
    isOpen: Boolean,
    onDismissRequest: () -> Unit,
    onClick: (String) -> Unit
) {
    var nameError by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }

    AppDialog(isOpen = isOpen, onDismissRequest = { onDismissRequest }) {
        Column(modifier = Modifier.width(200.dp)) {
            OutlinedTextField(
                label = { Text("Name") },
                value = "",
                isError = nameError,
                onValueChange = {
                    if (it != "") {
                        name = it
                        nameError = false
                    } else {
                        nameError = true
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton(onClick = onDismissRequest) {
                    Text(text = "Cancel")
                }
                TextButton(onClick = {
                    if (name.isBlank()) {
                        nameError = true
                    }
                    if (!nameError) {
                        onClick(name)
                    }
                }) {
                    Text(text = "OK")
                }
            }
        }
    }
}

@Composable
fun BackDialog(
    isOpen: Boolean,
    onDismissRequest: () -> Unit,
    onOkClick: () -> Unit,
    onCancelClick: () -> Unit,
) {
    AppDialog(isOpen = isOpen, onDismissRequest = onDismissRequest) {
        Column(
            modifier = Modifier.width(300.dp)
        ) {
            Text(
                modifier = Modifier.padding(16.dp),
                text = "All edited data will be lost"
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton(
                    onClick = onOkClick
                ) {
                    Text(text = "OK")
                }
                TextButton(
                    onClick = onCancelClick
                ) {
                    Text(text = "Cancel")
                }
            }
        }
    }
}