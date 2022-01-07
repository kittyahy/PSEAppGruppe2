package com.pseandroid2.dailydata.model

import android.graphics.drawable.Drawable

interface Graph {
    var id: Int

    fun getDataSets(): List<List<Any>>

    fun getCustomizing(): Settings

    fun getImage(): Drawable?

    fun getPath(): String?

    fun getType(): GraphType

    fun getCalculationFunction(): Project.DataTransformation<Any>

}

interface GraphTemplate {

    fun getName(): String

    fun getDescription(): String

    fun getCustomizing(): Settings

    fun getType(): GraphType

    fun getCreator(): User

}

enum class GraphType {

    LINE_CHART,
    PIE_CHART

}