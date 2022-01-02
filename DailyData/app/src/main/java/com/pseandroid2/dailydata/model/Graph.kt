package com.pseandroid2.dailydata.model

import android.graphics.drawable.Drawable

interface Graph {

    public fun getDataSets(): List<List<Any>>

    public fun getCustomizing(): Settings

    public fun getImage(): Drawable?

    public fun getPath(): String?

    public fun getType(): GraphType

    public fun getCalculationFunction(): Project.DataTransformation<Any>

}

interface GraphTemplate {

    public fun getName(): String

    public fun getDescription(): String

    public fun getCustomizing(): Settings

    public fun getType(): GraphType

    public fun getCreator(): User

}

enum class GraphType {

    LINE_CHART,
    PIE_CHART

}