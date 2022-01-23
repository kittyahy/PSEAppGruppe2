package com.pseandroid2.dailydata.repository.viewModelInterface.communicationClasses

interface Identifiable {
    val id: Long
    fun deleteIsPossible(): Boolean
    //@throws IllegalOperationException
    fun delete()
}