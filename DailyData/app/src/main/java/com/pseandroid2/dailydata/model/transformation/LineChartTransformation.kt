package com.pseandroid2.dailydata.model.transformation

import com.google.gson.Gson
import com.pseandroid2.dailydata.util.getSerializableClassName
import kotlin.reflect.KClass

class LineChartTransformation<T : Any>(
    private val identity: Identity<out Number>,
    private val xCol: Int = 0
) :
    TransformationFunction<Map<T, Float>>(
        "$CHART_TYPE_LINE::${identity.toCompleteString()}%${xCol}"
    ) {
    override fun execute(input: List<List<Any>>): List<Map<T, Float>> {
        val xValues = input[xCol]
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