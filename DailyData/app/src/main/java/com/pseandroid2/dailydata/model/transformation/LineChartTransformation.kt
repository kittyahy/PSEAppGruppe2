package com.pseandroid2.dailydata.model.transformation

import com.google.gson.Gson

class LineChartTransformation<T : Any>(
    private val identity: Identity<out Number>,
    private val xValues: List<T>
) :
    TransformationFunction<Map<T, Float>>(
        "$CHART_TYPE_LINE::${identity.toCompleteString()}%${Gson().toJson(xValues)}"
    ) {
    override fun execute(input: List<List<Any>>): List<Map<T, Float>> {
        val floatList = identity.execute(input)
        val maps = mutableListOf<Map<T, Float>>()
        for (list in floatList) {
            val map = mutableMapOf<T, Float>()
            for (i in list.indices) {
                map[xValues[i]] = list[i].toFloat()
            }
            maps.add(map)
        }
        return maps
    }
}