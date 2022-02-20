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