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

package com.pseandroid2.dailydata.ui.project.data.graph

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Checkbox
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.pseandroid2.dailydata.R
import com.pseandroid2.dailydata.model.graph.Generator
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Column
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.DotSize
import com.pseandroid2.dailydata.model.graph.Graph
import com.pseandroid2.dailydata.model.graph.GraphTemplate
import com.pseandroid2.dailydata.model.graph.GraphType
import com.pseandroid2.dailydata.model.graph.LineChart
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.LineType
import com.pseandroid2.dailydata.model.graph.PieChart
import com.pseandroid2.dailydata.model.table.ColumnData
import com.pseandroid2.dailydata.model.table.Table
import com.pseandroid2.dailydata.ui.composables.EnumDropDownMenu
import com.pseandroid2.dailydata.ui.project.creation.AppDialog
import com.pseandroid2.dailydata.ui.project.creation.ColorDialog
import com.pseandroid2.dailydata.util.ui.Wallpapers
import kotlinx.coroutines.launch

@Composable
fun ProjectDataGraphScreen(
    projectId: Int,
    viewModel: ProjectDataGraphScreenViewModel = hiltViewModel()
) {
    val templates: List<GraphTemplate> = listOf()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.initialize(projectId)
    }
    if (viewModel.isGraphDialogOpen) {
        ProjectGraphDialog(
            isOpen = viewModel.isGraphDialogOpen,
            onDismissRequest = {
                viewModel.onEvent(
                    ProjectDataGraphScreenEvent.OnShowGraphDialog(false)
                )
            },
            graph = viewModel.currentGraph!!,
            table = viewModel.project.value!!.table,
            templates = templates
        )
    }


    LazyColumn {
        itemsIndexed(viewModel.project.value!!.graphs) { _, graph ->
            GraphImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        viewModel.onEvent(
                            ProjectDataGraphScreenEvent.OnShowGraphDialog(true, graph)
                        )
                    },
                graph = graph
            )
        }
    }
}

@Composable
fun ProjectGraphDialog(
    isOpen: Boolean,
    onDismissRequest: () -> Unit,
    graph: Graph<*, *>,
    table: Table,
    templates: List<GraphTemplate>
) {
    var isTemplateDialogOpen by remember { mutableStateOf(false) }
    AppDialog(isOpen = isTemplateDialogOpen, onDismissRequest = onDismissRequest, padding = 0.dp) {
        Column(modifier = Modifier.width(200.dp)) {
            templates.filter { it.type.representation == graph.getType().representation }
                .forEachIndexed { index, graphTemplate ->
                    Box(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                            .height(32.dp)
                            .clickable {
                                graph.applyTemplateSettings(graphTemplate)
                                isTemplateDialogOpen = false
                            },
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(text = graphTemplate.name)
                    }
                    if (index < templates.lastIndex)
                        Divider()
                }
        }
    }


    AppDialog(isOpen = isOpen, onDismissRequest = onDismissRequest) {
        Column(
            modifier = Modifier.width(300.dp)
        ) {
            when (graph.getType()) {
                GraphType.FLOAT_LINE_CHART, GraphType.TIME_LINE_CHART, GraphType.INT_LINE_CHART -> {
                    LineChartDialog(
                        graph = graph as LineChart<*>,
                        table = table
                    )
                }
                GraphType.PIE_CHART -> {
                    PieChartDialog(
                        graph = graph as PieChart,
                        table = table
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton(onClick = { isTemplateDialogOpen = true }) {
                    Text(text = "Apply Template")
                }
                TextButton(onClick = onDismissRequest) {
                    Text(text = "OK")
                }
            }
        }
    }
}

@Composable
fun PieChartDialog(
    graph: PieChart,
    table: Table
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    var isColorDialogOpen by remember { mutableStateOf(false) }
    var columnIndex = -1
    var currentColor by remember { mutableStateOf(Color.Black) }
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        GraphImage(modifier = Modifier.fillMaxWidth().fillMaxHeight(0.5f), graph = graph)

        /*if (isColorDialogOpen) {
            WallpaperDialog(
                isOpen = isColorDialogOpen,
                onDismissRequest = { isColorDialogOpen = false },
                onWallpaperClick = {
                    graph.addMappingColor(index = colorIndex, it.value.toArgb())
                }
            )

        }*/
        ColorDialog(
            isOpen = isColorDialogOpen,
            onDismissRequest = { isColorDialogOpen = false },
            onColor = { color ->
                currentColor = color
            }
        )
        graph.getCalculationFunction().columns.forEachIndexed { _, column ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
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
                                .background(
                                    color = graph.primaryColors[column.id] ?: Color.Transparent
                                )
                                .clickable {
                                    isColorDialogOpen = true
                                    columnIndex = column.id
                                }
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

        val suggestions = table.layout.map { Pair(it, it.name) }
        var col by remember { mutableStateOf(table.layout.first()) }
        EnumDropDownMenu(
            suggestions = suggestions,
            value = col.name,
            onClick = { _, column ->
                col = column.first as ColumnData
            }
        )
        TextButton(onClick = {
            coroutineScope.launch {
                graph.getCalculationFunction().addColumn(col)
            }
        }) {
            Text(text = "Add Column")
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(checked = graph.showPercentages, onCheckedChange = {
                coroutineScope.launch {
                    graph.setShowPercentage(it)
                }
            })
            Text(text = "Show Percentages")
        }
    }
}

@Composable
fun LineChartDialog(
    graph: LineChart<*>,
    table: Table
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        GraphImage(modifier = Modifier.fillMaxWidth(), graph = graph)

        graph.getCalculationFunction().columns.forEachIndexed { index, column ->
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
                        coroutineScope.launch {
                            /* if (graph.deleteVerticalMappingIsPossible().first()) {
                                 graph.deleteVerticalMapping(index = index)
                             } else {
                                 Toast.makeText(
                                     context,
                                     "Could not delete mapping",
                                     Toast.LENGTH_SHORT
                                 ).show()
                             }*/
                        }
                    }
                )
            }
        }

        val suggestions = table.layout.map { Pair(it, it.name) }
        var col by remember { mutableStateOf(table.layout.first()) }
        EnumDropDownMenu(
            suggestions = suggestions,
            value = col.name,
            onClick = { _, column ->
                col = column.first as ColumnData
            }
        )
        TextButton(onClick = {
            coroutineScope.launch {
                /*if (graph.addVerticalMappingIsPossible().first()) {
                    graph.addVerticalMapping(column = table[col])
                } else {
                    Toast.makeText(context, "Could not add mapping", Toast.LENGTH_SHORT).show()
                }*/
            }
        }) {
            Text(text = "Add Column")
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Dot Size", modifier = Modifier.width(100.dp))
            val suggestions = DotSize.values().map { it.representation }
            /*EnumDropDownMenu(
                suggestions = suggestions,
                value = graph.dotSize.representation,
                onClick = {
                    coroutineScope.launch {
                        if (graph.changeDotColorIsPossible().first()) {
                            graph.changeDotSize(dotSize = DotSize.values()[it])
                        } else {
                            Toast.makeText(context, "Could not change dot size", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            )*/
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Dot Color", modifier = Modifier.width(100.dp))
            val suggestions = Wallpapers.values().map { it.representation }
            /*EnumDropDownMenu(
                suggestions = suggestions,
                value = graph.dotColor.toString(),
                onClick = {
                    coroutineScope.launch {
                        if (graph.changeDotColorIsPossible().first()) {
                            graph.changeDotColor(color = Wallpapers.values()[it].value.toArgb())
                        } else {
                            Toast.makeText(
                                context,
                                "Could not change dot color",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            )*/
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Line Type", modifier = Modifier.width(100.dp))
            val suggestions = LineType.values().map { it.representation }
            /*EnumDropDownMenu(
                suggestions = suggestions,
                value = graph.lineType.representation,
                onClick = {
                    coroutineScope.launch {
                        if (graph.changeLineTypeIsPossible().first()) {
                            graph.changeLineType(LineType.values()[it])
                        } else {
                            Toast.makeText(
                                context,
                                "Could not change line type",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            )*/
        }
    }
}

@Composable
fun GraphImage(modifier: Modifier, graph: Graph<*, *>) {
    val bitmap = graph.getImage()
    if (bitmap != null) {
        Image(
            bitmap = bitmap.asImageBitmap(),
            contentDescription = graph.getCustomizing()[Generator.GRAPH_NAME_KEY],
            modifier = modifier
        )
    } else {
        AndroidView(
            factory = {
                Generator.generateChart(graph, it)
            },
            modifier = modifier
        )
    }
}