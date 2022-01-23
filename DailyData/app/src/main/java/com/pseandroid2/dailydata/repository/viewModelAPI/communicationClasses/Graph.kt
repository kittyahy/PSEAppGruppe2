package com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses

import android.graphics.drawable.Drawable

interface Graph: Identifiable{
    override val id: Long
    val image: Drawable
}
