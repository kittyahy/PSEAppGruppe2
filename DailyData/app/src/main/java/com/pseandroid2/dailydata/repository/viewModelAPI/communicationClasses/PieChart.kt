package com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses

import android.graphics.Color
import android.graphics.drawable.Drawable

class PieChart(
    override val id: Long,
    override val image: Drawable,
    val color: List<Color>,
    val mapping: List<Column>,
    val showPercentages: Boolean
): Graph {
    override fun deleteIsPossible(): Boolean {
        TODO("Not yet implemented")
    }

    //@throws IllegalOperationException
    override fun delete() {
        TODO("Not yet implemented")
    }
}
