package com.pseandroid2.dailydata.repository.viewModelInterface.communicationClasses

import com.pseandroid2.dailydata.model.database.entities.ProjectData

class ProjectPreview(
    override val id: Long,
    val name: String,
    val image: String
): Identifiable {
    constructor(projectData: ProjectData) : this(projectData.id.toLong(), projectData.name, projectData.wallpaper)
    //@throws IllegalOperationException
    override fun delete() {
        TODO("Not yet implemented")
    }

    override fun deleteIsPossible(): Boolean {
        TODO("Not yet implemented")
    }
}
