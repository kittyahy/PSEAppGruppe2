package com.pseandroid2.dailydata.ui.project.data.graph

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
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
                modifier = Modifier.fillMaxWidth().clickable {
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

}