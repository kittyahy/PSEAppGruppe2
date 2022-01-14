package com.pseandroid2.dailydata.ui.project.data.input

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pseandroid2.dailydata.R
import com.pseandroid2.dailydata.util.ui.DataType
import com.pseandroid2.dailydata.util.ui.Row
import com.pseandroid2.dailydata.util.ui.TableButton
import com.pseandroid2.dailydata.util.ui.TableColumn
import com.pseandroid2.dailydata.util.ui.UiEvent

@Composable
fun ProjectDataInputScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: ProjectDataInputScreenViewModel = hiltViewModel()
) {
    var scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .verticalScroll(scrollState)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(
                        text = "Title."
                    )
                    Icon(
                        modifier = Modifier.clickable { },
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit",
                        tint = MaterialTheme.colors.onBackground,
                    )
                }
                Text("Description")
            }
        }
        Divider(modifier = Modifier.padding(10.dp))
        IncButtons(viewModel.buttons)
        Divider(modifier = Modifier.padding(10.dp))
        Columns(viewModel.columns)
        Divider(modifier = Modifier.padding(10.dp))
        Table(
            table = viewModel.columns,
            data = viewModel.table,
            onPress = {

            }
        )
    }
}

@Composable
fun Table(
    table : List<TableColumn>,
    data : List<Row>,
    onPress : (Offset) -> Unit
) {
    var scrollState = rememberScrollState()
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    Column(modifier = Modifier
        .fillMaxWidth()
        .heightIn(0.dp, screenHeight - 160.dp)) {
        LazyColumn {
            items(data) {row ->
                DisplayRow(data = row, scrollState = scrollState, onPress = onPress)
            }
        }
    }
}

@Composable
fun DisplayRow(
    data : Row,
    scrollState: ScrollState,
    onPress : (Offset) -> Unit
) {
    Row(
        modifier = Modifier
            .horizontalScroll(scrollState)
            .pointerInput(Unit) { detectTapGestures(onLongPress = onPress) }
    ) {
        //TODO("Display row")
    }
}

@Composable
fun Columns (
    table: List<TableColumn>
) {
    for (column in table) {
        com.pseandroid2.dailydata.ui.project.data.input.TableColumn(
            name = column.name,
            unit = column.unit,
            dataType = column.dataType,
            value = "",
            onValueChange = {

            }
        )
    }
}

@Composable
fun TableColumn (
    name : String,
    unit : String,
    dataType: DataType,
    value : String,
    onValueChange : () -> Unit
) {
    Column() {
        Row(
            modifier = Modifier.height(60.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "$name [$unit]:", modifier = Modifier.width(120.dp))
            when(dataType) {
                DataType.STRING -> {

                }
                DataType.TIME -> {

                }
                else -> {

                }
            }
        }
    }
}

@Composable
fun IncButtons(
    buttons : List<TableButton>
) {
    Column() {
        for(button in buttons) {
            IncButton(
                name = button.name,
                value = button.value,
                onClickInc = {

                },
                onClickDec = {

                },
                onClickAdd = {

                }
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

    Box(modifier = Modifier
        .fillMaxWidth()
        .height(80.dp)){
        Text(
            modifier = Modifier.align(Alignment.TopStart),
            text = name
        )
        Row(
            modifier = Modifier.align(Alignment.BottomStart),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(
                modifier = Modifier.size(50.dp),
                onClick = onClickDec,
                shape = CircleShape
            ){
                Icon(
                    modifier = Modifier.size(30.dp),
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_remove),
                    contentDescription = "")
            }
            Text(
                modifier = Modifier.padding(10.dp),
                text = "$value")
            OutlinedButton(
                modifier = Modifier.size(50.dp),
                onClick = onClickInc,
                shape = CircleShape
            ){
                Icon(
                    modifier = Modifier.size(30.dp),
                    imageVector = Icons.Default.Add,
                    contentDescription = "")
            }
        }
        OutlinedButton(
            modifier = Modifier.align(Alignment.BottomEnd),
            onClick = onClickAdd
        ) {
            Text(text = "Add")
        }

    }
}