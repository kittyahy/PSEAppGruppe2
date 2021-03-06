package com.pseandroid2.dailydata.model.transformation

class PieChartTransformation(private val sum: Sum<*>) :
    TransformationFunction<Float>("$CHART_TYPE_PIE::${sum.toCompleteString()}") {
    companion object {
        const val CHART_TYPE_PIE = "PIECHART"
    }

    override fun execute(input: List<List<Any>>): List<Float> {
        val numberSum = sum.execute(input)
        val floatList = mutableListOf<Float>()
        for (number in numberSum) {
            floatList.add(number.toFloat())
        }
        return floatList
    }
}