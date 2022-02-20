package com.pseandroid2.dailydata.util

fun hashOf(vararg values: Any?) = values.fold(0) { acc, value ->
    (acc * 31) + value.hashCode()
}
