package com.pseandroid2.dailydata.repository.viewModelInterface.communicationClasses

import android.graphics.Color

data class LineChart(
    val dotSize: DotSize,
    val dotColor: Color,
    val lineType: LineType,
    val mappingVertical: List<Column>
)
