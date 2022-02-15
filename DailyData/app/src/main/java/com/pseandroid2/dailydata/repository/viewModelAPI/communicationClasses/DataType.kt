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

package com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses

import com.pseandroid2.dailydata.util.getSerializableClassName
import java.time.LocalDateTime


enum class DataType(
    val representation: String,
    val regex: String,
    val initialValue: String,
    val serializableClassName: String,
    /**
     * Describes the amount of space in byte an AddRow ProjectCommand gains in its json form
     * (using CommandWrapper) in the worst case, when a column of this type is added.
     */
    val storageSize: Int
) {


    WHOLE_NUMBER(
        "Whole Number",
        "",
        "0",
        Int::class.getSerializableClassName(),
        Int.MAX_VALUE.toString().length * DataType.charSizeInString
    ),
    FLOATING_POINT_NUMBER(
        "Floating Point Number",
        "",
        "0",
        Float::class.getSerializableClassName(),
        Float.MAX_VALUE.toString().length * DataType.charSizeInString
    ),
    TIME(
        "Time",
        "",
        "00:00",
        LocalDateTime::class.getSerializableClassName(),
        LocalDateTime.MAX.toString().length * DataType.charSizeInString
    ),
    STRING(
        "String",
        "",
        "",
        String::class.getSerializableClassName(),
        150 * DataType.charSizeInString //Todo Magic Int
    );

    companion object {
        private const val charSizeInString = 8 //Todo ausrechnen

        /**
         * Describes the amount of space in byte an AddRow ProjectCommand has in its json form
         * (using CommandWrapper) regardless of its contents.
         */
        const val storageSizeBaseline = 200 * charSizeInString //Todo ausrechnen + überlegen, ob

        // diese zahlen nicht aus CommandWrapper kommen sollten
        const val maxStorageSize = 1024
        fun fromString(rep: String): DataType {
            for (enum in values()) {
                if (enum.representation == rep) {
                    return enum
                }
            }
            return WHOLE_NUMBER
        }

        fun fromSerializableClassName(rep: String): DataType {
            for (enum in values()) {
                if (enum.serializableClassName == rep) {
                    return enum
                }
            }
            return WHOLE_NUMBER
        }
    }
    //Todo Fields
}