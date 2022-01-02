package com.pseandroid2.dailydata.model

interface Settings {

    public fun getAllSettings(): Map<String, String>

    public fun getSetting(key: String): String

}