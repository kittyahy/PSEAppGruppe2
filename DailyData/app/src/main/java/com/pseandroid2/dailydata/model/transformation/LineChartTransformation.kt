package com.pseandroid2.dailydata.model.transformation

class LineChartTransformation<T : Any>(
    private val identity: Identity<out Number>,
    private val xValues: List<T>
) :
    TransformationFunction<Map<T, Float>>("LINECHART::${identity.toCompleteString()}") {
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