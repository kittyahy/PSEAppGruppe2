package com.pseandroid2.dailydata.ui.project.data.input

import android.app.TimePickerDialog
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pseandroid2.dailydata.R
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Button
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Column
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.DataType
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Row
import com.pseandroid2.dailydata.ui.project.creation.AppDialog
import com.pseandroid2.dailydata.util.ui.UiEvent
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import java.util.Calendar

@InternalCoroutinesApi
@Composable
fun ProjectDataInputScreen(
    projectId : Int,
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: ProjectDataInputScreenViewModel = hiltViewModel()
) {

    var scrollState = rememberScrollState()
    val context = LocalContext.current
    var members = viewModel.members
    var table = viewModel.table

    LaunchedEffect(key1 = true) {
        viewModel.onEvent(ProjectDataInputScreenEvent.OnCreate(projectId = projectId))
        viewModel.uiEvent.collect { event ->
            when(event) {
                is UiEvent.ShowToast -> Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                is UiEvent.Navigate -> onNavigate(event)
                else -> { }
            }
        }
    }

    RowDialog(
        isOpen = viewModel.isRowDialogOpen,
        onDismissRequest = { viewModel.onEvent(ProjectDataInputScreenEvent.OnCloseRowDialog) },
        onEditClick = { viewModel.onEvent(ProjectDataInputScreenEvent.OnRowModifyClick) },
        onDeleteClick = { viewModel.onEvent(ProjectDataInputScreenEvent.OnRowDeleteClick) }
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        ProjectHeader(
            title = viewModel.title,
            description = viewModel.description,
            background = viewModel.wallpaper,
            descriptionUnfolded = viewModel.isDescriptionUnfolded,
            onDescriptionClick = { viewModel.onEvent(ProjectDataInputScreenEvent.OnDescriptionClick) },
            isOnlineProject = viewModel.isOnlineProject,
            members = members.map { it.name.first().toString() },
            memberColor = Color.Magenta
        )
        IncButtons(
            buttons = viewModel.buttons,
            onClickInc = { viewModel.onEvent(ProjectDataInputScreenEvent.OnButtonClickInc(id = it)) },
            onClickDec = { viewModel.onEvent(ProjectDataInputScreenEvent.OnButtonClickDec(id = it)) },
            onClickAdd = { viewModel.onEvent(ProjectDataInputScreenEvent.OnButtonClickAdd(id = it)) }
        )
        Divider(modifier = Modifier.padding(vertical = 10.dp))
        Columns(
            table = viewModel.columns,
            values = viewModel.columnValues,
            onValueChange = { index, value ->
                viewModel.onEvent(ProjectDataInputScreenEvent.OnColumnChange(index, value))
            },
            onClickAdd = { viewModel.onEvent(ProjectDataInputScreenEvent.OnColumnAdd)}
        )
        Divider(modifier = Modifier.padding(vertical = 10.dp))
        Table(
            header = viewModel.columns,
            data = table,
            onPress = { viewModel.onEvent(ProjectDataInputScreenEvent.OnRowDialogShow(index = it)) }
        )
    }
}

@Composable
fun ProjectHeader(
    title : String,
    description: String,
    background : Color,
    descriptionUnfolded : Boolean,
    onDescriptionClick : () -> Unit,
    isOnlineProject : Boolean,
    members : List<String>,
    memberColor : Color
) {
    Column() {
        Surface(
            modifier = Modifier
                .fillMaxWidth(),
            color = background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = title, fontSize = 24.sp)
                if(descriptionUnfolded) {
                    Text(
                        modifier = Modifier.clickable { onDescriptionClick() },
                        text = description
                    )
                } else {
                    Text(
                        modifier = Modifier.clickable { onDescriptionClick() },
                        text = description,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                if (isOnlineProject) {
                    Spacer(modifier = Modifier.padding(4.dp))
                    Row() {
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                            items(members) { member ->
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clip(CircleShape)
                                        .background(memberColor),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(text = member, fontSize = 20.sp)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun IncButtons(
    buttons : List<Button>,
    onClickInc : (id : Int) -> Unit,
    onClickDec : (id : Int) -> Unit,
    onClickAdd : (id : Int) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
    ) {
        for(button in buttons) {
            IncButton(
                name = button.name,
                value = button.value,
                onClickInc = { onClickInc(button.id) },
                onClickDec = { onClickDec(button.id) },
                onClickAdd = { onClickAdd(button.id) }
            )
        }
    }
}

@Composable
fun IncButton(
    name : String,
    value : Int,
    onClickInc : () -> Unit,
    onClickDec : () -> Unit,
    onClickAdd : () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .padding(vertical = 8.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text =name)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(
                    modifier = Modifier.size(50.dp),
                    onClick = onClickDec,
                    shape = CircleShape
                ) {
                    Icon(
                        modifier = Modifier.size(30.dp),
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_remove),
                        contentDescription = ""
                    )
                }
                Text(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    text = "$value",
                    fontSize = 30.sp
                )
                OutlinedButton(
                    modifier = Modifier.size(50.dp),
                    onClick = onClickInc,
                    shape = CircleShape
                ) {
                    Icon(
                        modifier = Modifier.size(30.dp),
                        imageVector = Icons.Default.Add,
                        contentDescription = ""
                    )
                }
            }
            OutlinedButton(
                onClick = onClickAdd
            ) {
                Text(text = "Add")
            }
        }
    }
}

@Composable
fun Columns (
    table: List<Column>,
    values : List<String>,
    onValueChange: (index : Int, value : String) -> Unit,
    onClickAdd: () -> Unit
) {
    Column(modifier = Modifier
        .padding(horizontal = 16.dp)
        .fillMaxWidth()
    ) {
        for (i in table.indices) {
            TableColumn(
                name = table[i].name,
                dataType = table[i].dataType,
                value = values[i],
                placeholder = "",
                onValueChange = {
                    onValueChange(i, it)
                }
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(onClick = onClickAdd) {
                Text(text = "Add")
            }
        }
    }

}

@Composable
fun TableColumn (
    name : String,
    dataType: DataType,
    value : String,
    placeholder: String,
    onValueChange : (String) -> Unit
) {
    var numberError = false
    val scrollState = rememberScrollState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .width(100.dp)
                .horizontalScroll(scrollState),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "$name :")
        }
        when(dataType) {
            DataType.STRING -> {
                OutlinedTextField(
                    value = value,
                    onValueChange = onValueChange,
                    placeholder = { Text(placeholder) }
                )
            }
            DataType.TIME -> {
                val context = LocalContext.current
                val calendar = Calendar.getInstance()
                val hour = calendar[Calendar.HOUR_OF_DAY]
                val minute = calendar[Calendar.MINUTE]
                var time by remember { mutableStateOf("$hour:$minute") }
                val timePickerDialog = TimePickerDialog( context,
                    {_, hour : Int, minute: Int ->
                        time = "$hour:$minute"
                        onValueChange(time)
                    }, hour, minute, true
                )
                OutlinedButton(
                    onClick = { timePickerDialog.show() }
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Left,
                        text = time
                    )
                }
            }
            else -> {
                OutlinedTextField(
                    label = { Text("Unit") },
                    value = value,
                    isError = numberError,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    onValueChange = onValueChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                )
            }
        }
    }
}

@Composable
fun Table(
    header : List<Column>,
    data : List<Row>,
    onPress : (index : Int) -> Unit
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val state = rememberScrollState()
    val width = 100.dp
    Column(modifier = Modifier
        .fillMaxWidth()
        .heightIn(0.dp, screenHeight - 160.dp)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .horizontalScroll(state = state)
                    .height(IntrinsicSize.Min),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                for (i in header.indices) {
                    Column(
                        modifier = Modifier
                            .width(width)
                            .padding(vertical = 4.dp)
                    ) {
                        Text(text = header[i].name)
                        Text(text = "(${header[i].unit})", fontSize = 10.sp)
                    }
                    if (i < header.lastIndex) {
                        Divider(modifier = Modifier
                            .fillMaxHeight()
                            .width(1.dp)
                        )
                    }
                }
            }
            Divider()
        }
        LazyColumn {
            itemsIndexed(data) { index, row ->
                Row(
                    modifier = Modifier
                        .horizontalScroll(state = state)
                        .height(IntrinsicSize.Min)
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onLongPress = {
                                    onPress(index)
                                }
                            )
                        },
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    for(i in row.elements.indices) {
                        Text(text = row.elements[i], modifier = Modifier
                            .width(width)
                            .padding(vertical = 4.dp))
                        if(i < row.elements.lastIndex) {
                            Divider(modifier = Modifier
                                .fillMaxHeight()
                                .width(1.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RowDialog(
    isOpen : Boolean,
    onDismissRequest : () -> Unit,
    onEditClick : () -> Unit,
    onDeleteClick : () -> Unit
) {
    AppDialog(isOpen = isOpen, onDismissRequest = onDismissRequest) {
        Column() {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .clickable { onEditClick() },
                contentAlignment = Alignment.CenterStart

            ) {
                Text(text = "Edit")
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .clickable { onDeleteClick() },
                contentAlignment = Alignment.CenterStart

            ) {
                Text(text = "Delete")
            }
        }
    }
}