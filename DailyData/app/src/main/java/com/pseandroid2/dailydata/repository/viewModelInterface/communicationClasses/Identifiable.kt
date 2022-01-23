package com.pseandroid2.dailydata.repository.viewModelInterface.communicationClasses

interface Identifiable {
    val id: Long
    //@throws IllegalOperationException
    fun delete()
}