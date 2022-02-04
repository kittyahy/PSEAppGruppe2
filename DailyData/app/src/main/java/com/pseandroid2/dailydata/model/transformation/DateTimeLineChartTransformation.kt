package com.pseandroid2.dailydata.model.transformation

import java.time.LocalDateTime

class DateTimeLineChartTransformation(id: Identity<out Number>, xCol: Int = 0) :
    LineChartTransformation<LocalDateTime>(id, LINE_CHART_TYPE_DATE_TIME, xCol) {
    companion object {
        const val LINE_CHART_TYPE_DATE_TIME = "DATETIME"
    }

    override fun convertX(xValues: List<Any>): List<LocalDateTime> {
        val ret = mutableListOf<LocalDateTime>()
        for (value in xValues) {
            if (value is LocalDateTime) {
                ret.add(value)
            } else {
                throw NumberFormatException("$value could not be converted to LocalDateTime")
            }
        }
        return ret
    }
}