package com.pseandroid2.dailydata.ui.server.login

sealed class LoginScreenEvent {

    data class OnEmailChange(val email : String) : LoginScreenEvent()
    data class OnPasswordChange(val password : String) : LoginScreenEvent()

    object Login : LoginScreenEvent()
    object LoginGoogle : LoginScreenEvent()
    object SignUp : LoginScreenEvent()

}