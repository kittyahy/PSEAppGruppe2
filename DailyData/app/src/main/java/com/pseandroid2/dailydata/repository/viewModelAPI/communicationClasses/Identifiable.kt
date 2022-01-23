package com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses

interface Identifiable {
    val id: Long
    fun deleteIsPossible(): Boolean
    //@throws IllegalOperationException
    fun delete()
}