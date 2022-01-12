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

package com.pseandroid2.dailydata.util.ui

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.pseandroid2.dailydata.ui.server.ServerScreen
import com.pseandroid2.dailydata.ui.project.ProjectScreen
import com.pseandroid2.dailydata.ui.project.creation.ProjectCreationScreen
import com.pseandroid2.dailydata.ui.project.data.ProjectDataScreen
import com.pseandroid2.dailydata.ui.project.overview.ProjectOverviewScreen
import com.pseandroid2.dailydata.ui.templates.TemplatesScreen

@Composable
fun Navigation(navController: NavHostController) {

    val projectNavState = rememberSaveable(saver = navStateSaver()) { mutableStateOf(Bundle()) }

    NavHost(
        navController = navController,
        startDestination = Routes.PROJECT
    ) {
        composable(Routes.PROJECT) {
            ProjectScreen(
                onNavigate = {
                    navController.navigate(it.route)
                },
                navState = projectNavState
            )
        }
        composable(Routes.TEMPLATES) {
            TemplatesScreen(
                onNavigate = {
                    navController.navigate(it.route)
                }
            )
        }
        composable(Routes.SERVER) {
            ServerScreen(
                onNavigate = {
                    navController.navigate(it.route)
                }
            )
        }
    }
}

@Composable
fun ProjectNavigation(
    navController: NavHostController,
    startDestination : String = Routes.OVERVIEW
) {

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Routes.CREATION) {
            ProjectCreationScreen(
                onNavigate = {
                    navController.navigate(it.route)
                }
            )
        }
        composable(Routes.OVERVIEW) {
            ProjectOverviewScreen(
                onNavigate = {
                    navController.navigate(it.route)
                }
            )
        }
        composable(
            route = Routes.DATA + "?projectId={projectId}",
            arguments = listOf(
                navArgument(name = "projectId") {
                    type = NavType.IntType
                    defaultValue = -1;
                }
            )
        ) {
            ProjectDataScreen(
                onNavigate = {
                    navController.navigate(it.route)
                }
            )
        }
    }
}

fun navStateSaver(): Saver<MutableState<Bundle>, out Any> = Saver(
    save = { it.value },
    restore = { mutableStateOf(it) }
)
