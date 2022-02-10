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

package com.pseandroid2.dailydata.model.uielements

/**
 * This emum declares all different types of UIElements. A UIElement has a name and a to-String Representation
 */
enum class UIElementType {

    BUTTON {
        override fun toString(): String {
            return BUTTON_STRING
        }
    },
    NUMBER_FIELD {
        override fun toString(): String {
            return NUMBER_FIELD_STRING
        }
    },
    DATE_TIME_PICKER {
        override fun toString(): String {
            return DATE_TIME_PICKER_STRING
        }
    };

    companion object {
        const val BUTTON_STRING = "BUTTON"
        const val NUMBER_FIELD_STRING = "NUMBER_FIELD"
        const val DATE_TIME_PICKER_STRING = "DATE_TIME_PICKER"
        fun fromString(str: String): UIElementType {
            return when (str) {
                BUTTON_STRING -> BUTTON
                NUMBER_FIELD_STRING -> NUMBER_FIELD
                DATE_TIME_PICKER_STRING -> DATE_TIME_PICKER
                else -> {
                    throw IllegalArgumentException()
                }
            }
        }
    }
}