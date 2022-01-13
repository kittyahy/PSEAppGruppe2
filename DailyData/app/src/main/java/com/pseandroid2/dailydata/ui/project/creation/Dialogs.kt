package com.pseandroid2.dailydata.ui.project.creation

import android.app.TimePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.pseandroid2.dailydata.ui.composables.EnumDropDownMenu
import com.pseandroid2.dailydata.util.ui.DataType
import com.pseandroid2.dailydata.util.ui.Graphs
import com.pseandroid2.dailydata.util.ui.Wallpapers
import java.lang.NumberFormatException
import java.util.Calendar

@Composable
fun WallpaperDialog(
    isOpen : Boolean,
    onDismissRequest: () -> Unit,
    onWallpaperClick : (Wallpapers) -> Unit) {
    if(isOpen) {
        Dialog(
            onDismissRequest = onDismissRequest
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
                                .clickable { onWallpaperClick(wallpaper) },
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
fun TableDialog(
    isOpen : Boolean,
    onDismissRequest: () -> Unit,
    onClick : (String, String, DataType) -> Unit,
) {
    if(isOpen) {
        var name by remember { mutableStateOf("") }
        var nameError by remember { mutableStateOf(false) }
        var unit by remember { mutableStateOf("") }
        var unitError by remember { mutableStateOf(false) }
        val suggestions = DataType.values().toList().map { it.representation }
        var dataType by remember { mutableStateOf(suggestions.first()) }
        Dialog(
            onDismissRequest = onDismissRequest
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
                                onClick(name, unit, DataType.fromString(dataType))
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
fun ButtonDialog(
    isOpen: Boolean,
    buttons : List<String>,
    onDismissRequest: () -> Unit,
    onClick: (String, Int, String) -> Unit
) {
    if(isOpen) {
        var name by remember { mutableStateOf("") }
        var nameError by remember { mutableStateOf(false) }
        var value by remember { mutableStateOf("") }
        var valueError by remember { mutableStateOf(false) }

        val suggestions = buttons
        var column by remember { mutableStateOf(0) }

        Dialog(
            onDismissRequest = onDismissRequest
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
                                onClick(name, column, value)
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
fun NotificationDialog(
    isOpen: Boolean,
    onDismissRequest: () -> Unit,
    onClick: (String, String) -> Unit
) {
    if(isOpen) {
        var message by remember { mutableStateOf("") }
        var messageError by remember { mutableStateOf(false) }

        val context = LocalContext.current
        val calendar = Calendar.getInstance()
        val hour = calendar[Calendar.HOUR_OF_DAY]
        val minute = calendar[Calendar.MINUTE]
        var time by remember { mutableStateOf("$hour:$minute") }
        val timePickerDialog = TimePickerDialog( context,
            {_, hour : Int, minute: Int ->
                time = "$hour:$minute"
            }, hour, minute, true
        )

        Dialog(
            onDismissRequest = onDismissRequest
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
                        value = message,
                        isError = messageError,
                        onValueChange = {
                            message = it
                            messageError = false
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.padding(10.dp))
                    Button(onClick = { timePickerDialog.show() }) {
                        Text(text = "Selected Time: $time")
                    }
                    Button(
                        onClick = {
                            if(message.isBlank()) {
                                messageError = true
                            } else {
                                onClick(message, time)
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
fun GraphDialog(
    isOpen: Boolean,
    onDismissRequest: () -> Unit,
    onClick: (Graphs) -> Unit
) {
    if(isOpen) {
        Dialog(
            onDismissRequest = onDismissRequest
        ) {
            Surface(
                shape = RoundedCornerShape(5.dp),
                color = MaterialTheme.colors.background,
                modifier = Modifier.padding(10.dp)
            ) {
                Column(modifier = Modifier.width(200.dp)) {
                    for (graph in Graphs.values()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp)
                                .clickable {
                                    onClick(graph)
                                },
                        ) {
                            Text(text = graph.representation)
                        }
                    }
                }
            }
        }
    }
}