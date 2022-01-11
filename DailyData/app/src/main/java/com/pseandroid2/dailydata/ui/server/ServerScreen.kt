package com.example.pse.ui.server

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pse.util.ui.UiEvent

@Composable
fun ServerScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: ServerScreenViewModel = hiltViewModel()
) {
    Text("Server")
}