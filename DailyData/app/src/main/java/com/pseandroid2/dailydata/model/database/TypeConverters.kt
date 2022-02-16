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

package com.pseandroid2.dailydata.model.database

import android.util.Log
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.pseandroid2.dailydata.model.graph.GraphType
import com.pseandroid2.dailydata.model.project.Project
import com.pseandroid2.dailydata.model.uielements.UIElementType
import com.pseandroid2.dailydata.model.users.SimpleUser
import com.pseandroid2.dailydata.model.users.User
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

/**
 * Type Converter for LocalDateTime
 */
class DateTimeConversion {
    /**
     * Converts the given dateTime to a Long by assuming its time zone to be systemDefault
     */
    @TypeConverter
    fun dateTimeToLong(dateTime: LocalDateTime): Long {
        return  dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
    }

    /**
     * Converts the given Long to a LocalDateTime by assuming it to be EpochMilliseconds of the
     * systemDefault time zone
     */
    @TypeConverter
    fun longToDateTime(longDate: Long): LocalDateTime {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(longDate), ZoneId.systemDefault())
    }
}

/**
 * Type Converter for User. Able to save any subclass of User but will only return SimpleUsers
 */
class UserConversion {
    /**
     * Converts the given User to a String by saving both its id and its name delimited by a |
     */
    @TypeConverter
    fun userToString(user: User): String {
        return user.getId() + "|" + user.getName()
    }

    /**
     * Converts the given String to a simple User by splitting it at | into an id and a name
     *
     * @throws IllegalArgumentException if the String can't be split into exactly to two Strings
     */
    @TypeConverter
    fun stringToUser(userString: String): User {
        val split = userString.split('|')
        if (split.size != 2) {
            throw IllegalArgumentException(
                "User String must be composited of an ID and a Name, delimited by a |"
            )
        }
        return SimpleUser(split[0], split[1])
    }
}

/**
 * Type Converter for GraphType
 */
class GraphTypeConversion {
    /**
     * Converts the given GraphType to a String
     */
    @TypeConverter
    fun graphTypeToString(type: GraphType) = when (type) {
        GraphType.FLOAT_LINE_CHART -> "FLOAT_LINE_CHART"
        GraphType.INT_LINE_CHART -> "INT_LINE_CHART"
        GraphType.TIME_LINE_CHART -> "TIME_LINE_CHART"
        GraphType.PIE_CHART -> "PIE_CHART"
    }

    /**
     * Converts the given String to a GraphType
     *
     * @throws IllegalArgumentException if the String can't be converted to a GraphType
     */
    @TypeConverter
    fun stringToGraphType(typeStr: String) = when (typeStr) {
        "FLOAT_LINE_CHART" -> GraphType.FLOAT_LINE_CHART
        "INT_LINE_CHART" -> GraphType.INT_LINE_CHART
        "TIME_LINE_CHART" -> GraphType.TIME_LINE_CHART
        "PIE_CHART" -> GraphType.PIE_CHART
        else -> throw IllegalArgumentException("Could not convert $typeStr to a GraphType")
    }
}

/**
 * Type Converter for UIElementType
 */
class UIElementTypeConversion {
    /**
     * Converts the given UIElementType to a String
     */
    @TypeConverter
    fun uiElementToString(uiType: UIElementType) = when (uiType) {
        UIElementType.BUTTON -> "BUTTON"
        UIElementType.DATE_TIME_PICKER -> "DATE_TIME_PICKER"
        UIElementType.NUMBER_FIELD -> "NUMBER_FIELD"
    }

    /**
     * Converts the given String to a UIElementType
     *
     * @throws IllegalArgumentException if the String can't be converted to a UIElementType
     */
    @TypeConverter
    fun stringToUIElement(uiString: String) = when (uiString) {
        "BUTTON" -> UIElementType.BUTTON
        "DATE_TIME_PICKER" -> UIElementType.DATE_TIME_PICKER
        "NUMBER_FIELD" -> UIElementType.NUMBER_FIELD
        else -> throw IllegalArgumentException("Could not convert $uiString to a UIElementType")
    }
}
