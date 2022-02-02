package com.pseandroid2.dailydata.ui.server.login

sealed class LoginScreenEvent {

    data class OnEmailChange(val email : String) : LoginScreenEvent()
    data class OnPasswordChange(val password : String) : LoginScreenEvent()

    data class ShowSignUpDialog(val isOpen : Boolean) : LoginScreenEvent()

    object Login : LoginScreenEvent()
    object LoginGoogle : LoginScreenEvent()
    data class SignUp(val email : String, val password : String) : LoginScreenEvent()

}