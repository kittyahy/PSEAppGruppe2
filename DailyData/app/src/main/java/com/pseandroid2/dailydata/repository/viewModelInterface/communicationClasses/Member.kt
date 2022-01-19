package com.pseandroid2.dailydata.repository.viewModelInterface.communicationClasses

data class Member(
    override val id: Long,
    val name: String
): Identifiable
