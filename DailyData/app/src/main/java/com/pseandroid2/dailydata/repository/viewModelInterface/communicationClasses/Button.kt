package com.pseandroid2.dailydata.repository.viewModelInterface.communicationClasses

data class Button(
    override val id: Long,
    val name: String,
    val columnId: Long,
    val value: Int
): Identifiable
