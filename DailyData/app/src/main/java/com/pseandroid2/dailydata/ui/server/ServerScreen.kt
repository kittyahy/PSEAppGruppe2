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

package com.pseandroid2.dailydata.ui.server

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.pseandroid2.dailydata.ui.server.login.LoginScreen
import com.pseandroid2.dailydata.ui.server.templates.ServerTemplatesScreen
import com.pseandroid2.dailydata.util.ui.UiEvent

@Composable
fun ServerScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: ServerScreenViewModel = hiltViewModel()
) {
    if(viewModel.loggedIn) {
        ServerTemplatesScreen(onNavigate = onNavigate)
    } else {
        LoginScreen()
    }
}