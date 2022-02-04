package com.pseandroid2.dailydata.ui.project.data.graph

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.pseandroid2.dailydata.R
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Column
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.DotSize
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Graph
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.GraphTemplate
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.LineChart
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.LineType
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.PieChart
import com.pseandroid2.dailydata.ui.composables.EnumDropDownMenu
import com.pseandroid2.dailydata.ui.project.creation.AppDialog
import com.pseandroid2.dailydata.ui.project.creation.WallpaperDialog
import com.pseandroid2.dailydata.util.ui.Wallpapers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@InternalCoroutinesApi
@Composable
fun ProjectDataGraphScreen(
    projectId : Int,
    viewModel: ProjectDataGraphScreenViewModel = hiltViewModel()
) {
    var templates : List<GraphTemplate> = listOf()

    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        viewModel.onEvent(ProjectDataGraphScreenEvent.OnCreate(projectId = projectId))
    }
    if (viewModel.isDialogOpen) {
        ProjectGraphDialog(
            isOpen = viewModel.isDialogOpen,
            onDismissRequest = { viewModel.onEvent(ProjectDataGraphScreenEvent.OnCloseGraphDialog) },
            graph = viewModel.graphs[viewModel.dialogIndex],
            table = viewModel.table,
            templates = templates
        )
    }


    LazyColumn {
        itemsIndexed(viewModel.graphs) { index, graph ->
            coroutineScope.launch {
                /* TODO()
                if(graph.image == null && graph.showIsPossible().first()) {
                    graph.show(context = context)
                }

                 */
            }
            if(graph.image != null) {
                Image(
                    //useResource("image.png") { loadImageBitmap(it) }
                    bitmap = graph.image!!.asImageBitmap(),
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            viewModel.onEvent(
                                ProjectDataGraphScreenEvent.OnShowGraphDialog(
                                    index = index
                                )
                            )
                        }
                )
            }
        }
    }
}

@Composable
fun ProjectGraphDialog(
    isOpen : Boolean,
    onDismissRequest : () -> Unit,
    graph : Graph,
    table : List<Column>,
    templates : List<GraphTemplate>
) {
    var isDialogOpen by remember { mutableStateOf(false) }
    AppDialog(isOpen = isDialogOpen, onDismissRequest = onDismissRequest, padding = 0.dp) {
        Column(modifier = Modifier.width(200.dp)) {
            templates.filter { it.type == graph.typeName }.forEachIndexed { index, graphTemplate ->
                Box(modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .height(32.dp)
                        .clickable {
                            //graph.apply template
                            isDialogOpen = false
                        },
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(text = graphTemplate.title)
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
            when(graph) {
                is LineChart -> {
                    LineChartDialog(
                        graph = graph,
                        table = table
                    )
                }
                is PieChart -> {
                    PieChartDialog(
                        graph = graph,
                        table = table
                    )
                }
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                TextButton(onClick = {isDialogOpen = true}) {
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
    graph : PieChart,
    table : List<Column>
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    var colorDialogOpen by remember { mutableStateOf(false) }
    var colorIndex = 0
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        //useResource("image.png") { loadImageBitmap(it) }
        Image(
            //cannot be null cause image is already shown in preview
            bitmap = graph.image!!.asImageBitmap(),
            contentDescription = "",
            modifier = Modifier.fillMaxWidth()
        )

        if(colorDialogOpen) {
            WallpaperDialog(
                isOpen = colorDialogOpen,
                onDismissRequest = { colorDialogOpen = false },
                onWallpaperClick = {
                    graph.addMappingColor(index = colorIndex, it.value.toArgb())
                }
            )
        }
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
                                .clickable {
                                    colorDialogOpen = true
                                    colorIndex = index
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

        val suggestions = table.map { it.name }
        var col by  remember { mutableStateOf( 0 ) }
        EnumDropDownMenu(
            suggestions = suggestions,
            value = suggestions[col],
            onClick = { col = it }
        )
        TextButton(onClick = {
            coroutineScope.launch {
                if (graph.addMappingIsPossible().first()) {
                    graph.addMapping(column = table[col])
                } else {
                    Toast.makeText(context, "Could not change add mapping", Toast.LENGTH_SHORT).show()
                }
            }
        }) {
            Text(text = "Add Column")
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(checked = graph.showPercentages, onCheckedChange = {
                coroutineScope.launch {
                    if (graph.showPercentagesIsPossible().first()) {
                        graph.showPercentages(show = it)
                    } else {
                        Toast.makeText(context, "Could not edit percentages", Toast.LENGTH_SHORT).show()
                    }
                }
            })
            Text(text = "Show Percentages")
        }
    }
}

@Composable
fun LineChartDialog(
    graph : LineChart,
    table : List<Column>
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
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
                        coroutineScope.launch {
                            if (graph.deleteVerticalMappingIsPossible().first()) {
                                graph.deleteVerticalMapping(index = index)
                            } else {
                                Toast.makeText(context, "Could not delete mapping", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                )
            }
        }

        val suggestions = table.map { it.name }
        var col by  remember { mutableStateOf( 0 ) }
        EnumDropDownMenu(
            suggestions = suggestions,
            value = suggestions[col],
            onClick = { col = it }
        )
        TextButton(onClick = {
            coroutineScope.launch {
                if (graph.addVerticalMappingIsPossible().first()) {
                    graph.addVerticalMapping(column = table[col])
                } else {
                    Toast.makeText(context, "Could not add mapping", Toast.LENGTH_SHORT).show()
                }
            }
        }) {
            Text(text = "Add Column")
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Dot Size", modifier = Modifier.width(100.dp))
            val suggestions = DotSize.values().map { it.representation }
            EnumDropDownMenu(
                suggestions = suggestions,
                value = graph.dotSize.representation,
                onClick = {
                    coroutineScope.launch {
                        if (graph.changeDotColorIsPossible().first()) {
                            graph.changeDotSize(dotSize = DotSize.values()[it])
                        } else {
                            Toast.makeText(context, "Could not change dot size", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Dot Color", modifier = Modifier.width(100.dp))
            val suggestions = Wallpapers.values().map { it.representation }
            EnumDropDownMenu(
                suggestions = suggestions,
                value = graph.dotColor.toString(),
                onClick = {
                    coroutineScope.launch {
                        if (graph.changeDotColorIsPossible().first()) {
                            graph.changeDotColor(color = Wallpapers.values()[it].value.toArgb())
                        } else {
                            Toast.makeText(context, "Could not change dot color", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Line Type", modifier = Modifier.width(100.dp))
            val suggestions = LineType.values().map { it.representation }
            EnumDropDownMenu(
                suggestions = suggestions,
                value = graph.lineType.representation,
                onClick = {
                    coroutineScope.launch {
                        if (graph.changeLineTypeIsPossible().first()) {
                            graph.changeLineType(LineType.values()[it])
                        } else {
                            Toast.makeText(context, "Could not change line type", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            )
        }
    }
}