package com.pseandroid2.dailydata.util

data class Quadruple<out T, out S, out R, out Q>(
    val first: T,
    val second: S,
    val third: R,
    val fourth: Q
)