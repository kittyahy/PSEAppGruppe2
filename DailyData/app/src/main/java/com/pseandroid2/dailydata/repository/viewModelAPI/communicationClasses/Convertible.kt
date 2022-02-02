package com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses

import com.pseandroid2.dailydata.model.project.Project
import com.pseandroid2.dailydata.model.project.ProjectBuilder

interface Convertible<I> {
    fun toDBEquivalent(): I
    fun addYourself(builder: ProjectBuilder<out Project>)
}
