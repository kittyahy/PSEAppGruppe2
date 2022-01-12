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

import androidx.room.TypeConverter
import com.pseandroid2.dailydata.model.GraphType
import com.pseandroid2.dailydata.model.Project
import com.pseandroid2.dailydata.model.uielements.UIElementType
import com.pseandroid2.dailydata.model.User
import java.time.LocalDateTime

class DateTimeConversion {
    @TypeConverter
    fun dateTimeToLong(dateTime: LocalDateTime): Long {
        return 0
    }

    @TypeConverter
    fun longToDateTime(longDate: Long): LocalDateTime {
        return LocalDateTime.now()
    }
}

class UserConversion {
    @TypeConverter
    fun userToString(user: User): String {
        return ""
    }

    @TypeConverter
    fun stringToUser(userString: String): User {
        TODO()
    }
}

class TransformationConversion {
    @TypeConverter
    fun traFoToString(transformation: Project.DataTransformation<Any>): String {
        return ""
    }

    @TypeConverter
    fun stringToTraFo(trafoString: String): Project.DataTransformation<Any> {
        TODO()
    }
}

class GraphTypeConversion {
    @TypeConverter
    fun graphTypeToString(type: GraphType): String {
        return ""
    }

    @TypeConverter
    fun stringToGraphType(typeStr: String): GraphType {
        return GraphType.PIE_CHART
    }
}

class UIElementTypeConversion {
    @TypeConverter
    fun uiElementToString(uiType: UIElementType): String {
        return ""
    }

    @TypeConverter
    fun stringToUIElement(uiString: String): UIElementType {
        return UIElementType.BUTTON
    }
}
