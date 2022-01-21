package com.pseandroid2.dailydata.repository.viewModelInterface.communicationClasses.adapters.objects

import com.pseandroid2.dailydata.model.database.AppDataBase

abstract class ObjectAdapter <T> {
    abstract val appDataBase: AppDataBase
    abstract fun get(): T
    abstract fun store(obj: T)
}