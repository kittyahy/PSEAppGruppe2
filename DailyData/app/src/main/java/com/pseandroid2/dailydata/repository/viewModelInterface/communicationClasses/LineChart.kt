package com.pseandroid2.dailydata.repository.viewModelInterface.communicationClasses

import android.graphics.Color
import android.graphics.drawable.Drawable

data class LineChart(
    override val id: Long,
    override val image: Drawable,
    val dotSize: DotSize,
    val dotColor: Color,
    val lineType: LineType,
    val mappingVertical: List<Column>
): Graph
