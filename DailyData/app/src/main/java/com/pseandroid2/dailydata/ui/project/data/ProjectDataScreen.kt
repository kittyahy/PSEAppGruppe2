package com.pseandroid2.dailydata.ui.project.data

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pse.util.ui.UiEvent

@Composable
fun ProjectDataScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: ProjectDataScreenViewModel = hiltViewModel()
) {

    Text("Project Data")
}