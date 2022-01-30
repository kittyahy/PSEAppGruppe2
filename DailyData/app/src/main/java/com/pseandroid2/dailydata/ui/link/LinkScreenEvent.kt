package com.pseandroid2.dailydata.ui.link

sealed class LinkScreenEvent {

    data class OnButtonClick(val id : Long): LinkScreenEvent()
}
