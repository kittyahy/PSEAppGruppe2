package com.pseandroid2.dailydata.model

interface Notification {

    public fun getMessage(): String

    public fun sendNow(vararg args: Any): Boolean

    public fun toFactoryString(): String

}