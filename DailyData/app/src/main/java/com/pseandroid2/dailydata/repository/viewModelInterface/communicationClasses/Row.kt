package com.pseandroid2.dailydata.repository.viewModelInterface.communicationClasses

data class Row(
    override val id: Long,
    val elements: List<String>
): Identifiable
