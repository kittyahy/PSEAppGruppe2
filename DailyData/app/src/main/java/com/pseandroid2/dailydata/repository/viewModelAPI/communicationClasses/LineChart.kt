package com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses

import android.graphics.Color
import android.graphics.drawable.Drawable

class LineChart(
    override val id: Long,
    override val image: Drawable,
    val dotSize: DotSize,
    val dotColor: Color,
    val lineType: LineType,
    val mappingVertical: List<Column>
): Graph {
    override fun deleteIsPossible(): Boolean {
        TODO("Not yet implemented")
    }

    //@throws IllegalOperationException
    override fun delete() {
        TODO("Not yet implemented")
    }
}
