package com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses

import com.pseandroid2.dailydata.model.project.Project
import com.pseandroid2.dailydata.model.project.ProjectBuilder

interface Convertible<I> {
    @Deprecated("Internal function, should not be used outside the RepositoryViewModelAPI")
    fun toDBEquivalent(): I

    @Deprecated("Internal function, should not be used outside the RepositoryViewModelAPI")
    fun addYourself(builder: ProjectBuilder<out Project>)
}
