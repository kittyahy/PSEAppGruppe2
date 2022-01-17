package com.pseandroid2.dailydata.repository.viewModelInterface.communicationClasses

import android.graphics.drawable.Drawable

data class ProjectPreview(
    override val id: Long,
    val name: String,
    val image: Drawable
): Identifiable
