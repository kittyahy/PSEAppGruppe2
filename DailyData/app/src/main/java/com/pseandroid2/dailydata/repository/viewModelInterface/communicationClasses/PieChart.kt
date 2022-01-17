package com.pseandroid2.dailydata.repository.viewModelInterface.communicationClasses

import android.graphics.Color
import android.graphics.drawable.Drawable

data class PieChart(
    override val id: Long,
    override val image: Drawable,
    val color: List<Color>,
    val mapping: List<Column>,
    val showPercentages: Boolean
): Graph
