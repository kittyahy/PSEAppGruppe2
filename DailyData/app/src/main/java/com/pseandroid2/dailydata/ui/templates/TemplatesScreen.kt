package com.pseandroid2.dailydata.ui.templates

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pse.util.ui.UiEvent

@Composable
fun TemplatesScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: TemplatesScreenViewModel = hiltViewModel()
) {
    Text("Templates")
}