package com.pseandroid2.dailydata.repository.viewModelInterface.communicationClasses

data class Column (
    override val id: Long,
    val name: String,
    val unit: String,
    val dataType: DataType
): Identifiable
