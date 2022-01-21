package com.pseandroid2.dailydata.repository.viewModelInterface.communicationClasses.adapters.objects

import com.pseandroid2.dailydata.model.database.AppDataBase
import com.pseandroid2.dailydata.repository.viewModelInterface.communicationClasses.ProjectPreview

class ProjectPreviewAdapter(override val appDataBase: AppDataBase) :
    ObjectAdapter<ProjectPreview>() {
    override fun get(): ProjectPreview {
        TODO("Not yet implemented")
    }

    override fun store(obj: ProjectPreview) {
        TODO("Not yet implemented")
    }
}