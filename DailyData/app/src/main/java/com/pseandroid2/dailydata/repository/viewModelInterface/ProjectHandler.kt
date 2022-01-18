package com.pseandroid2.dailydata.repository.viewModelInterface


import com.pseandroid2.dailydata.model.database.daos.ProjectDataDAO
import com.pseandroid2.dailydata.repository.viewModelInterface.communicationClasses.ProjectPreview
import javax.inject.Inject

class ProjectHandler {
    @Inject lateinit var projectDataDAO: ProjectDataDAO
}