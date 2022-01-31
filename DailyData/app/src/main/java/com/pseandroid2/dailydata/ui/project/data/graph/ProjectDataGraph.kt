package com.pseandroid2.dailydata.ui.project.data.graph

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Checkbox
import androidx.compose.material.DropdownMenu
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pseandroid2.dailydata.R
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Column
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.DataType
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.DotSize
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Graph
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.LineChart
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.LineType
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.PieChart
import com.pseandroid2.dailydata.ui.composables.EnumDropDownMenu
import com.pseandroid2.dailydata.ui.project.creation.AppDialog
import com.pseandroid2.dailydata.util.ui.UiEvent
import com.pseandroid2.dailydata.util.ui.Wallpapers
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
@Composable
fun ProjectDataGraphScreen(
    projectId : Int,
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: ProjectDataGraphScreenViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        viewModel.onEvent(ProjectDataGraphScreenEvent.OnCreate(projectId = projectId))
    }
    ProjectGraphDialog(
        isOpen = viewModel.isDialogOpen,
        onDismissRequest = { viewModel.onEvent(ProjectDataGraphScreenEvent.OnCloseGraphDialog) },
        graph = viewModel.graphs[viewModel.dialogIndex],
        table = viewModel.table
    )

    LazyColumn {
        itemsIndexed(viewModel.graphs) { index, graph ->
            Image(
                //useResource("image.png") { loadImageBitmap(it) }
                bitmap = graph.image.asImageBitmap(),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        viewModel.onEvent(ProjectDataGraphScreenEvent.OnShowGraphDialog(index = index))
                    }
            )
        }
    }
}

@Composable
fun ProjectGraphDialog(
    isOpen : Boolean,
    onDismissRequest : () -> Unit,
    graph : Graph,
    table : List<Column>
) {
    AppDialog(isOpen = isOpen, onDismissRequest = onDismissRequest) {
        Column(
            modifier = Modifier.width(300.dp)
        ) {
           when(graph) {
                is LineChart -> {
                    LineChartDialog(
                        onDismissRequest = onDismissRequest,
                        graph = graph,
                        table = table
                    )
                }
                is PieChart -> {
                    PieChartDialog(
                        onDismissRequest = onDismissRequest,
                        graph = graph,
                        table = table
                    )
                }
            }
        }
    }
}

@Composable
fun PieChartDialog(
    onDismissRequest: () -> Unit,
    graph : PieChart,
    table : List<Column>
) {
    Column() {
        //useResource("image.png") { loadImageBitmap(it) }
        Image(
            bitmap = graph.image.asImageBitmap(),
            contentDescription = "",
            modifier = Modifier.fillMaxWidth()
        )

        graph.mapping.forEachIndexed { index, column ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier
                        .size(40.dp)
                        .wrapContentSize(Alignment.CenterStart)
                    ) {
                        Box(
                            modifier = Modifier
                                .size((25.dp))
                                .clip(CircleShape)
                                .background(color = Color(graph.color[index]))
                        )
                    }
                    Text(text = column.name)
                }
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "",
                    tint = MaterialTheme.colors.onBackground,
                )
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            var suggestions = table.map { it.name }
            var col by  remember { mutableStateOf( 0 ) }
            EnumDropDownMenu(
                suggestions = suggestions,
                value = suggestions[col],
                onClick = { col = it }
            )
            TextButton(onClick = {
                graph.addMapping(column = table[col])
            }) {
                Text(text = "Add Column")
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(checked = graph.showPercentages, onCheckedChange = {
                graph.showPercentages(show = it)
            })
            Text(text = "Show Percentages")
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            TextButton(onClick = onDismissRequest) {
                Text(text = "OK")
            }
        }
    }
}

@Composable
fun LineChartDialog(
    onDismissRequest: () -> Unit,
    graph : LineChart,
    table : List<Column>
) {
    Column() {
        Image(
            painter = painterResource(id = R.drawable.chart),
            contentDescription = "",
            modifier = Modifier.fillMaxWidth()
        )

        graph.mappingVertical.forEachIndexed { index, column ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = column.name)
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "",
                    tint = MaterialTheme.colors.onBackground,
                    modifier = Modifier.clickable {
                        graph.deleteVerticalMapping(index = index)
                    }
                )
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            var suggestions = table.map { it.name }
            var col by  remember { mutableStateOf( 0 ) }
            EnumDropDownMenu(
                suggestions = suggestions,
                value = suggestions[col],
                onClick = { col = it }
            )
            TextButton(onClick = {
                graph.addVerticalMapping(column = table[col])
            }) {
                Text(text = "Add Column")
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Dot Size")
            var suggestions = DotSize.values().map { it.representation }
            EnumDropDownMenu(
                suggestions = suggestions,
                value = graph.dotSize.representation,
                onClick = { graph.changeDotSize(dotSize = DotSize.values()[it]) }
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Dot Color")
            var suggestions = Wallpapers.values().map { it.representation }
            EnumDropDownMenu(
                suggestions = suggestions,
                value = graph.dotColor.toString(),
                onClick = { graph.changeDotColor(color = Wallpapers.values()[it].value.toArgb()) }
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Line Type")
            var suggestions = LineType.values().map { it.representation }
            EnumDropDownMenu(
                suggestions = suggestions,
                value = graph.lineType.representation,
                onClick = { graph.changeLineType(LineType.values()[it]) }
            )
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            TextButton(onClick = onDismissRequest) {
                Text(text = "OK")
            }
        }
    }
}