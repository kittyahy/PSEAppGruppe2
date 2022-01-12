package com.pseandroid2.dailydata.ui.project

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.pseandroid2.dailydata.util.ui.UiEvent
import com.pseandroid2.dailydata.util.ui.ProjectNavigation

@Composable
fun ProjectScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    navState : MutableState<Bundle>,
    viewModel: ProjectScreenViewModel = hiltViewModel()
) {

    val navController = rememberNavController()
    ProjectNavigation(navController = navController)

}
