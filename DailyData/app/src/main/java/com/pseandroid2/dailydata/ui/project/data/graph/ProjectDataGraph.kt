package com.pseandroid2.dailydata.ui.project.data.graph

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.DropdownMenu
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pseandroid2.dailydata.R
import com.pseandroid2.dailydata.ui.project.creation.AppDialog
import com.pseandroid2.dailydata.util.ui.UiEvent

@Composable
fun ProjectDataGraphScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: ProjectDataGraphScreenViewModel = hiltViewModel()
) {

    ProjectGraphDialog(
        isOpen = viewModel.isDialogOpen,
        onDismissRequest = {
            viewModel.onEvent(ProjectDataGraphScreenEvent.OnCloseGraphDialog)
        },
        onSaveClick = {

        },
        graph = viewModel.graphs[viewModel.dialogIndex]
    )

    LazyColumn {
        itemsIndexed(viewModel.graphs) { index, graph ->
            Image(
                painter = painterResource(id = graph.image),
                contentDescription = "Text 2",
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
    onSaveClick : () -> Unit,
    graph : GraphLol
) {
    AppDialog(isOpen = isOpen, onDismissRequest = onDismissRequest) {
        Column(
            modifier = Modifier.width(300.dp)
        ) {
            var type = ""
            when(type) {
                "Pie" -> PieChartDialog()
                "Bar" -> LineChartDialog()
                "Line" -> BarChartDialog()
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PieChartDialog(

) {
    Column() {
        Image(
            painter = painterResource(id = R.drawable.chart),
            contentDescription = "",
            modifier = Modifier.fillMaxWidth()
        )

        Row() {
            Text(text = "X-Axis:  ")
            DropdownMenu(expanded = false, onDismissRequest = { }) {
                //show all columns that have int and the row counter
            }
        }
        //for each column in
    }

}

@Composable
fun LineChartDialog(

) {

}

@Composable
fun BarChartDialog(

) {

}