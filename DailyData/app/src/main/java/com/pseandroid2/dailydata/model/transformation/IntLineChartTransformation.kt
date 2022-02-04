package com.pseandroid2.dailydata.model.transformation

class IntLineChartTransformation(id: Identity<out Number>, xCol: Int = 0) :
    LineChartTransformation<Int>(id, LINE_CHART_TYPE_INT, xCol) {
    companion object {
        const val LINE_CHART_TYPE_INT = "INT"
    }

    override fun convertX(xValues: List<Any>): List<Int> {
        val ret = mutableListOf<Int>()
        for (value in xValues) {
            if (value is Int) {
                ret.add(value)
            } else {
                throw NumberFormatException("$value could not be converted to Int")
            }
        }
        return ret
    }
}