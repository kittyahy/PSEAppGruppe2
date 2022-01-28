package com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses

import com.pseandroid2.dailydata.model.project.Project
import com.pseandroid2.dailydata.model.project.ProjectBuilder

interface Convertible<T> {
    fun toDBEquivalent(): T
    fun addYourself(builder: ProjectBuilder<Project>)
}
