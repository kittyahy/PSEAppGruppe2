package com.pseandroid2.dailydata.util

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlin.reflect.KClass

const val INTEGER_QUAL_NAME = "java.lang.Integer"
const val FLOAT_QUAL_NAME = "java.lang.Float"

inline fun <reified T> Gson.fromJson(json: String): T =
    fromJson<T>(json, object : TypeToken<T>() {}.type)

inline fun <reified T : Any> KClass<T>.getSerializableClassName(): String =
    if (this == Int::class) {
        INTEGER_QUAL_NAME
    } else if (this == Float::class) {
        FLOAT_QUAL_NAME
    } else {
        this.java.canonicalName!!
    }