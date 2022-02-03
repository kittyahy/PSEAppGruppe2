package com.pseandroid2.dailydata.model.transformation

import java.lang.IllegalArgumentException

class FloatIdentity : Identity<Float>("FLOAT") {
    override fun convertElements(input: List<List<Any>>): List<List<Float>> {
        val outer = mutableListOf<List<Float>>()
        for (list in input) {
            val inner = mutableListOf<Float>()
            for (element in list) {
                if (element is Number) {
                    inner.add(element.toFloat())
                } else {
                    throw IllegalArgumentException("Could not convert $element to Float")
                }
            }
            outer.add(inner)
        }
        return outer
    }
}