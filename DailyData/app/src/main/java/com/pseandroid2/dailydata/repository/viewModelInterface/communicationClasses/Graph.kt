package com.pseandroid2.dailydata.repository.viewModelInterface.communicationClasses

import android.graphics.drawable.Drawable

interface Graph: Identifiable{
    override val id: Long
    val image: Drawable
}
