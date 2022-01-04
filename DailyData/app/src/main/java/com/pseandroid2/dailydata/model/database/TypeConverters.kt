package com.pseandroid2.dailydata.model.database

import androidx.room.TypeConverter
import com.pseandroid2.dailydata.model.GraphType
import com.pseandroid2.dailydata.model.Project
import com.pseandroid2.dailydata.model.UIElementType
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
