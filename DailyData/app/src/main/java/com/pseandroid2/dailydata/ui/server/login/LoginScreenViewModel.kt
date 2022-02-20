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

package com.pseandroid2.dailydata.ui.server.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.util.ui.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    val repository : RepositoryViewModelAPI
) : ViewModel() {

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    var isSignUpDialogOpen by mutableStateOf(false)
        private set
    var userEmail by mutableStateOf("")
        private set
    var userPassword by mutableStateOf("")
        private set

    fun onEvent(event : LoginScreenEvent) {
        when (event) {
            is LoginScreenEvent.OnEmailChange -> {
                userEmail = event.email
            }
            is LoginScreenEvent.OnPasswordChange -> {
                userPassword = event.password
            }
            is LoginScreenEvent.Login -> {
                //i assume model checks if valid
                viewModelScope.launch {
                    if (repository.serverHandler.loginIsPossible().first()) {
                        repository.serverHandler.login(email = userEmail, password = userPassword)

                    } else {
                        sendUiEvent(UiEvent.ShowToast("Could not log in"))
                    }
                }
            }
            is LoginScreenEvent.LoginGoogle -> {
                sendUiEvent(UiEvent.ShowToast("This feature is not ready yet"))
            }
            is LoginScreenEvent.SignUp -> {
                viewModelScope.launch {
                    if (repository.serverHandler.signUpIsPossible().first()) {
                        repository.serverHandler.signUp(email = event.email, password = event.password)
                    } else {
                        sendUiEvent(UiEvent.ShowToast("Could not sign up"))
                    }
                }
            }
            is LoginScreenEvent.ShowSignUpDialog -> {
                isSignUpDialogOpen = event.isOpen
            }
        }
    }

    private fun sendUiEvent(event : UiEvent) {
        viewModelScope.launch {
            _uiEvent.emit(event)
        }
    }
}