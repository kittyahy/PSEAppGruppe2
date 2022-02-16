package com.pseandroid2.dailydata.model.transformation

class FloatLineChartTransformation(id: Identity<out Number> = FloatIdentity(), xCol: Int = 0) :
    LineChartTransformation<Float>(id, LINE_CHART_TYPE_FLOAT, xCol) {
    companion object {
        const val LINE_CHART_TYPE_FLOAT = "FLOAT"
    }

    override fun convertX(xValues: List<Any>): List<Float> {
        val ret = mutableListOf<Float>()
        for (value in xValues) {
            if (value is Float) {
                ret.add(value)
            } else {
                throw NumberFormatException("$value could not be converted to Float")
            }
        }
        return ret
    }
}