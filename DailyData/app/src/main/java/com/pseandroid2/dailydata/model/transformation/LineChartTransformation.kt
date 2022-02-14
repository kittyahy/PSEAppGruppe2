package com.pseandroid2.dailydata.model.transformation

import com.google.gson.Gson
import com.pseandroid2.dailydata.util.getSerializableClassName
import kotlin.reflect.KClass

abstract class LineChartTransformation<T : Any>(
    private val identity: Identity<out Number>,
    typeString: String,
    private val xCol: Int
) :
    TransformationFunction<Map<T, Float>>(
        "$typeString$CHART_TYPE_LINE::${identity.toCompleteString()}%${xCol}"
    ) {
    companion object {
        const val CHART_TYPE_LINE = "LINECHART"
    }

    override fun execute(input: List<List<Any>>): List<Map<T, Float>> {
        val xValues = input[xCol]
        val yValues = mutableListOf<List<Any>>()
        for (i in input.indices) {
            if (i != xCol) {
                yValues.add(input[i])
            }
        }
        val floatList = identity.execute(yValues)
        val floatXValues = convertX(xValues)
        val maps = mutableListOf<Map<T, Float>>()
        for (list in floatList) {
            val map = mutableMapOf<T, Float>()
            for (i in list.indices) {

                map[floatXValues[i]] = list[i].toFloat()
            }
            maps.add(map)
        }
        //TODO Sort by X-Values if User inputs weird stuff
        return maps
    }

    abstract fun convertX(xValues: List<Any>): List<T>
}